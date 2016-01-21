package br.com.netquiz.servidor.impl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.netquiz.cliente.interfaces.Cliente;
import br.com.netquiz.servidor.dao.PerguntaDAO;
import br.com.netquiz.servidor.interfaces.Servidor;
import br.com.netquiz.servidor.model.Pergunta;

public class ServidorImpl implements Servidor {

	public static Logger logger = LogManager.getLogger(ServidorImpl.class.getName());

	private boolean statusDoJogo = false;
    //Define que o jogo come�ar� na vez do jogador um e a quantidade de perguntas que aparecer�o no menu
	public int vezJogador = 1;
	public int numeroDeRodadas = 0;
	public final int QUANTIDADE_DE_PERGUNTAS = 16;

	ArrayList<Pergunta> arrayPerguntas;
	public List<Boolean> arrayPerguntasValidas;
	public List<Cliente> clientes = new ArrayList<Cliente>();

	public PerguntaDAO pergundaDAO;

	public static void main(String[] args) {
		try {
			ServidorImpl servidorImpl = new ServidorImpl();
			Servidor stub = (Servidor) UnicastRemoteObject.exportObject(servidorImpl, 0);
			Registry registry = LocateRegistry.createRegistry(6789);
			logger.info("Criando registro com o nome 'JogoNetQuiz'...");
			registry.bind("JogoNetQuiz", stub);
			logger.info("Servidor inicializado com sucesso...");
			logger.info("Esperando conexoes na porta '6789'...\n\n\r\r");
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public String getRanking() throws RemoteException {
		String ranking;
        //Ranking que aparecer� para os jogadores
		ranking = " *=======================================*\r\n" 
				+ " |                RANKING                |\r\n"
				+ " |---------------------------------------|\r\n" 
				+ " |   NOME DO JOGADOR |  PONTUACAO        |\r\n"
				+ " |---------------------------------------|\r\n";
		for (Cliente cliente : clientes) {
			ranking += " |   " + cliente.getNome() 
					 + " \t\t" + cliente.getPontuacao() 
					 + "" 
					 + "                |\r\n";
		}
		ranking += " |---------------------------------------|\r\n" 
				 + " *=======================================*";

		return ranking;
	}
    // M�todo que ir� efetuar o registo dos jogadores conectados
	public void registrar(Cliente cliente) throws RemoteException {
		clientes.add(cliente);
		
		/* Quando o primeiro jogador se conectar, ser� gerada uma mensagem para que este aguarde o segundo jogador
		 * Tamb�m ser� gerada uma mensagem para mostrar que a conex�o do jogador um foi efetuada com sucesso
		 */
		if (clientes.size() == 1) {
			cliente.notificar(cliente.getNome() + " , por favor aguarde o seu oponente se conectar..");
			cliente.setID(1);
			logger.info("Jogador 1 - ["+cliente.getNome()+"] conectado com sucesso");
			
			/*Ser� gerada uma mensagem para mostrar que a conex�o do jogador dois foi efetuada com sucesso
			 */
		} else if (clientes.size() == 2) {
			cliente.setID(2);
			logger.info("Jogador 2 - ["+cliente.getNome()+"] conectado com sucesso");
			statusDoJogo = true;
			pergundaDAO = new PerguntaDAO();
			
			// Buscando as perguntas de forma aleat�ria no vetor de perguntas 
			arrayPerguntas = pergundaDAO.buscarPerguntasAleatorias(QUANTIDADE_DE_PERGUNTAS);
			arrayPerguntasValidas = new ArrayList<Boolean>(Arrays.asList(new Boolean[QUANTIDADE_DE_PERGUNTAS]));
			Collections.fill(arrayPerguntasValidas, Boolean.FALSE);
			clientes.forEach(c -> {
				try {
					logger.info("O Jogo esta sendo iniciado...");
					c.notificar(c.getNome() + " o jogo esta sendo iniciado...");
					c.play();
				} catch (Exception e) {
					logger.error(e);
				}
			});

		} else {
			cliente.notificar(
					cliente.getNome() + " o jogo NetQuiz so permite dois jogadores, espere a partida terminar!");
		}

	}
   // M�todo utilizado para mostrar o meu de perguntas para os jogadores
	@Override
	public String getMenu() throws RemoteException {

		String menu = "\t*===========================*\r\n" 
		        + "\t|     MENU DE PERGUNTAS     |\r\n"
				+ "\t|---------------------------|\r\n\t  ";
		int quebraDeLinha = 1;

		for (int contador = 0; contador < arrayPerguntasValidas.size(); contador++) {
			if (!arrayPerguntasValidas.get(contador)) {
				if (quebraDeLinha <= 4) {
					if (quebraDeLinha != 4) {
						menu += " ";
						menu += contador + 1 + "\t";
						quebraDeLinha++;
					} else {
						menu += contador + 1 + "  \n\r\t  ";
						quebraDeLinha = 1;
					}

				}
			}
		}

		menu += "  \n\r\t|---------------------------|\r\n" 
		        + "\t|*Perguntas ja selecionadas |\r\n"
				+ "\t| por qualquer um dos dois  |\r\n" 
				+ "\t| jogadores nao podem mais  |\r\n" 
				+ "\t| ser escolhidas.           |\r\n" 
				+ "\t*===========================*\n\n\r\r"
				+ "Digite o numero da pergunta escolhida: ";

		return menu;
	}
   // M�todo respons�vel por mostrar as respostas poss�veis a pergunta selecionada pelos jogadores
	@Override
	public String getPergunta(int indexPergunta) throws RemoteException {
		String pergunta;
		indexPergunta--;
		pergunta = "\r\r\n\n " + arrayPerguntas.get(indexPergunta).getTitulo_pergunta() + "\r\n\r\n 1. "
				+ arrayPerguntas.get(indexPergunta).getRespostas().get(0).getTitulo_resposta() + "\r\n 2. "
				+ arrayPerguntas.get(indexPergunta).getRespostas().get(1).getTitulo_resposta() + "\r\n 3. "
				+ arrayPerguntas.get(indexPergunta).getRespostas().get(2).getTitulo_resposta() + "\r\n 4. "
				+ arrayPerguntas.get(indexPergunta).getRespostas().get(3).getTitulo_resposta()
				+ "\r\r\n\n Escolha a alternativa correta: ";

		return pergunta;
	}

	@Override
	public boolean getStatusDoJogo() {
		return statusDoJogo;
	}

	@Override
	public int getVezJogador() throws RemoteException {
		return vezJogador;
	}

	@Override
	public void setVezJogador() throws RemoteException {
		if (vezJogador > 1) {
			vezJogador = 1;
		} else {
			vezJogador = 2;
		}
	}
   
	//Verifica se o numero da quest�o escolhida esta de acordo com o n�mero de quest�es presentes no menu
	@Override
	public boolean isQuestaoValida(int numeroQuestao) throws RemoteException {
		if (numeroQuestao > QUANTIDADE_DE_PERGUNTAS || numeroQuestao < 1
				|| arrayPerguntasValidas.get(numeroQuestao - 1)) {
			return false;
		} else {
			return true;
		}
	}
	
	//Verifica se o numero da resposta escolhida esta de acordo com o n�mero de respostas que podem ser escolhidas ( de 1 a 4)
	@Override
	public boolean isRespostaValida(int numeroResposta) throws RemoteException {
		if (numeroResposta > 4 || numeroResposta < 1) {
			return false;
		} else {
			return true;
		}
	}
	@Override
	public boolean enviarRespostaDaPergunta(int numeroAlternativa, int numeroPergunta, int idJogador)
			throws RemoteException {
		numeroAlternativa--;
		numeroPergunta--;
		numeroDeRodadas++;
		arrayPerguntasValidas.set(numeroPergunta, true);

		if (numeroDeRodadas == QUANTIDADE_DE_PERGUNTAS) {
			statusDoJogo = false;
		}

		if (arrayPerguntas.get(numeroPergunta).getRespostas().get(numeroAlternativa).getValido()) {
			clientes.get(idJogador -1).setPontuacao();
			clientes.forEach(c -> {
				try {
					c.notificar(getRanking());
				} catch (Exception e) {
					logger.error(e);
				}
			});
			return true;
		} else {
			return false;
		}
	}
   
	// M�todo respons�vel por notificar quem foi o vencedor do jogo
	@Override
	public void notificarVencedor(int idJogador) throws RemoteException {
		idJogador--;
		if (clientes.get(0).getPontuacao() > clientes.get(1).getPontuacao()) {
			clientes.get(idJogador).notificar(" O vencedor do jogo foi " + clientes.get(0).getNome() +" com  " +clientes.get(0).getPontuacao() +"  ponto(s)");
			clientes.get(idJogador).notificar(" O jogador " + clientes.get(1).getNome() + " ficou com " +clientes.get(1).getPontuacao() +" ponto(s)");
		} else if (clientes.get(0).getPontuacao() < clientes.get(1).getPontuacao()) {
			clientes.get(idJogador).notificar(" O vencedor do jogo foi " + clientes.get(1).getNome() +" com  " +clientes.get(1).getPontuacao() +"  ponto(s)");
			clientes.get(idJogador).notificar(" O jogador  " + clientes.get(0).getNome() + " ficou com " +clientes.get(0).getPontuacao() +"  ponto(s)");
		} else {
			clientes.get(idJogador).notificar(" O jogo terminou empatado com  " +clientes.get(0).getPontuacao()+" ponto(s) para cada jogador!");
		}

	}

}
