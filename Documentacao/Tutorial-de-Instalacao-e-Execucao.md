NetQuiz - Projeto 2
========================
Tutorial de instalação e execução
-----------------------------

NetQuiz é um jogo de perguntas e respostas desenvolvido utilizando arquitetura RMI, que permite a execução de chamadas de métodos entre aplicações remotamente. Para sua correta execução são necessárias as seguintes ferramentas:

  - Eclipse/Netbeans IDE
  - Servidor MySQL
  - 2 clientes executando no terminal

### Instalação

- Primeiro é necessário a instalação do servidor [MySQL](https://dev.mysql.com/downloads/mysql/5.5.html).
- Após a instalação, é necessário criar um banco chamado "netquiz" em minúsculo.
- O usuário do banco deve ser "root" e a senha "root".
- Em seguida execute o script ```netquiz.sql``` para criar as tabelas e popular o banco com as perguntas e respostas.
- Na IDE Eclipse, ir em ```File > Import```, selecionar a opção ```Git - Projects from Git```, ```Clone Url``` e inserir a url do projeto no gitlab.
- Na IDE Netbeans, ir em ```Team > Git> Clone``` e inserir a url do projeto no gitlab.
- Feito isso será clonado o projeto no repositório local.

### Execução

Execute a classe ServidorImpl.java (br.com.netquiz.servidor.impl) pelo Eclipse, clicando com o botão direito >Run As > Java Application.

Acesse a raiz do pacote com o comando:
```
cd NetQuizRMI/src/main/java
```
Compile a classe ClienteImpl no terminal com o comando:
```
javac -cp . br/com/netquiz/cliente/impl/ClienteImpl.java
```
Execute o cliente no terminal:
```
java br.com.netquiz.cliente.impl.ClienteImpl
```
Após isso o jogo irá inicializar. Como o jogo é por turnos, em uma das janelas o jogo irá apresentar a mensagem pedindo para aguardar, e na outra irá ser apresentado um menu com opções de escolha do número da pergunta desejada. 

Após a escolha da resposta, o servidor valida se a resposta está correta e insere a pontuação de acordo com sua resposta.

Após verificar se o primeiro jogador pontuou ou não, será a vez do segundo jogador realizar sua jogada, uma mensagem será apresentada para o primeiro jogador aguardar enquanto o segundo jogador efetua sua jogada.

O vencedor será quem ao final das rodadas possuir a maior pontuação.


	 
