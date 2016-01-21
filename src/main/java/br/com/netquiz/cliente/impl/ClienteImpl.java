package br.com.netquiz.cliente.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import br.com.netquiz.cliente.interfaces.Cliente;
import br.com.netquiz.cliente.util.Impressao;
import br.com.netquiz.servidor.interfaces.Servidor;

public class ClienteImpl implements Cliente {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static Registry registry;
	static Servidor servidor;
	String nome;
	Integer id;
	Integer pontuacao = 0;
	Impressao impressao = new Impressao();

	static {
		try {
			registry = LocateRegistry.getRegistry("localhost", 6789);
			servidor = (Servidor) registry.lookup("JogoNetQuiz");

		} catch (RemoteException ex) {
			System.err.println("Nao foi possivel conectar ao servidor. Servidor esta ativo?");
			System.err.println(ex);
			System.exit(0);
		} catch (NotBoundException ex) {
			System.err.println("Nao foi possivel encontrar o registro. Variavel esta correta?");
			System.err.println(ex);
			System.exit(0);
		}
	}

	public static void main(String[] args) throws Exception {

		System.out.print("Por favor insira seu nome: ");
		ClienteImpl cliente = new ClienteImpl();
		cliente.nome = reader.readLine().toUpperCase();
		Cliente stub = (Cliente) UnicastRemoteObject.exportObject(cliente, 0);
		Random gerador = new Random();
		Registry registry = LocateRegistry.createRegistry(gerador.nextInt(1000) + 6000);
		registry.bind("Cliente", stub);
		servidor.registrar(cliente);
	}

	@Override
	public void notificar(String mensagem) throws RemoteException {
		System.out.println(mensagem);
	}

	@Override
	public String getNome() throws RemoteException {
		return nome;
	}
    // M�todo de in�cio do jogo
	@Override
	public void play() throws RemoteException {
		new Thread() {
			@Override
			public void run() {
                
				try {
					// Busca o status do jogo e verifica de qual jogador � a vez
					while (servidor.getStatusDoJogo()) {
						if (servidor.getVezJogador() == id) {

							int opcaoMenu;
							int opcaoPergunta;
							boolean flag = false;
                            
							do {
								System.out.print(servidor.getMenu());
								opcaoMenu = impressao.getInputApenasInteiro(reader);
								// Mensagem de erro caso seja escolhida uma quest�o que n�o esteja no menu
								if (!servidor.isQuestaoValida(opcaoMenu)) {
									System.out.println("Por favor insira uma questao valida de acordo com o menu!");
								} else {
									flag = true;
								}

							} while (!flag);

							flag = false;

							do {
								System.out.print(servidor.getPergunta(opcaoMenu));
								opcaoPergunta = impressao.getInputApenasInteiro(reader);
								// Mensagem de erro caso escolha um numero de resposta que n�o seja os presentes nas op��es
								
								if (!servidor.isRespostaValida(opcaoPergunta)) {
									System.out.println("Por favor insira uma alternativa valida!");
								} else {
									flag = true;
								}

							} while (!flag);
							// Retorna a mensagem "resposta correta" caso o jogador tenha acertado
							if (servidor.enviarRespostaDaPergunta(opcaoPergunta, opcaoMenu, id)) {
								System.out.println("\r\n\t*--------------------*\r\n" 
												 + "\t|  RESPOSTA CORRETA! |\r\n"
												 + "\t*--------------------*\r\n");

								
							// Retorna a mensagem "resposta incorreta" caso o jogador tenha errado
							} else {
								System.out.println("\r\n\t*---------------------*\r\n" 
										   		  +"\t| RESPOSTA INCORRETA! |\r\n" 
										   		  +"\t*---------------------*\r\n");
							}
							
							servidor.setVezJogador();
						} else {
							System.out.println("AGUARDE A JOGADA DO SEU OPONENTE...");
							while (servidor.getVezJogador() != id) {
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					servidor.notificarVencedor(id);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				System.out.println("\n\n\r\r******************** FIM DE JOGO ********************");
			}
		}.start();
	}

	@Override
	public int getPontuacao() throws RemoteException {
		return pontuacao;
	}

	@Override
	public void setID(Integer id) throws RemoteException {
		this.id = id;
	}


	public void setPontuacao() throws RemoteException {
		 
		pontuacao++;
		
		}
	
	}
	

