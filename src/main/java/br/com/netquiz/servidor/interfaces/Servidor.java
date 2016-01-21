package br.com.netquiz.servidor.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.netquiz.cliente.interfaces.Cliente;

public interface Servidor extends Remote {
	
    public String getRanking() throws RemoteException;

	public String getMenu() throws RemoteException;

	public String getPergunta(int indexPergunta) throws RemoteException;

    public void registrar(Cliente cliente) throws RemoteException;

    public boolean getStatusDoJogo() throws RemoteException;
    
    public int getVezJogador() throws RemoteException;
    
    public void setVezJogador() throws RemoteException;
    
    public boolean isQuestaoValida(int numeroQuestao) throws RemoteException;
    
    public boolean isRespostaValida(int numeroResposta) throws RemoteException;
    
    public boolean enviarRespostaDaPergunta(int numeroAlternativa, int numeroPergunta, int idJogador) throws RemoteException;
    
    public void notificarVencedor(int idJogador) throws RemoteException;
}
