# **Plano de Teste**  

# **Objetivo**  

O documento do plano de teste de software tem o objetivo de executar o software de modo sistemático afim de encontrar `defeitos`, `erros` e `falhas`. E além disso os testes objetivam aumentar a confiabilidade do software desenvolvido, aumentado a probabildiade de que o jogo funcionará sem falhas durante um período de tempo.  

# **Sequência de teste**  

- **Teste 1**  

Abrir duas janelas do console, independente do sistema operacional utilizado.  

Digitar a string de conexão nas duas janelas do console.  

Resultado esperado: Conexão dos dois jogadores através da string de conexão: java br.com.netquiz.cliente.impl.ClienteImpl

Possível falha: Jogadores não se conectare.   

- **Teste 2**  

Após a conexão o jogador deverá informar o seu nome e teclar enter.  

Será apresentado uma mensagem para o jogador aguardar o seu componente conectar ao jogo.  

Possível falha: Não exibir a mensagem para aguardar o oponente conectar ao jogo.  

Resultado esperado: Exibir a mensagem para o jogador para aguardar o oponente conectar ao jogo.  


- **Teste 3**  

Após a conexão dos jogadores o jogo irá inicializar. 

Uma janela será apresentado ao jogador. Nesta janela irá conter o menu de questões, com os números das perguntas disponíveis. Abaixo do menu haverá uma mensagem explicativa.

Possível falha: Janela não exibir o menu de questões para o jogador.  

Possível falha: Abaixo do menu de questões não ser exibida a mensagem explicativa.  

Resultado esperado: Exibir janela com menu de questões e abaxio do menu mensagem explicativa.  

- **Teste 4**  

O jogador deverá escolher uma questão de 1 a 16.  

Possível falha: Não exibir a questão escolhida.  

Resultado esperado: Exibir a questão escolhida contendo as opções de 1 a 4 para a escolha da resposta.  

- **Teste 5**  

Após o jogador escolher o número da questão será apresentado a questão com opções de escolha, de 1 a 4.  

Possível falha: Não apresentar as opções das questões.

Resultado esperado: Apresentar as opções da questões.  

- **Teste 6**  

Após a escolha da resposta, será apresentado uma mensagem ao jogador, informando se a resposta esta correta ou incorreta. Será apresentando o ranking da pontuação informando o nome e a pontução individual dos jogadores.     

Caso a resposta seja correta a pontuação será 1 (um), caso esteja incorreta será zero (0).  

Possível falha: Não apresentar mensagem sobre a resposta se esta correta ou incorreta.  

Possível falha: Não apresentar o ranking informando nome e pontuação dos jogadores.  

Resultado esperado: Informar a mensagem sobre a reposta.  

Resultado esperado: Informar o ranking com os nomes e a pontuação do jogadores.    

- **Teste 7**  

Após o jogador 1 terminar a sua jogada, será a vez do jogador 2 realizar a sua jogada.  

Será exibido para o primeiro jogador, uma mensagem para aguardar o oponente, jogador 2, realizar a sua jogada.

Possível falha: Não ser exibida mensagem ao jogador 1, para aguardar o oponente, jogador 2, realizar a sua jogada.  

Resultado esperado: Exibir mensagem ao jogador 1 para aguardar o oponente, jogador 2, realizar a sua jogada.   

- **Teste 8**  

O jogador deverá digitar o número de uma questão que não esta disponível no menu de questões.  

Deverá ser exibida uma mensagem de restrição ao jogador, para que escolha uma questão válida de acordo com o menu.  

Possível falha: Não ser exibida a mensagem de restrição após o jogador escolher o número de uma questão que não esta no menu.  

Possível falha: Ser exibida a questão escolhida que não esta no menu de questões.

Resultado esperado: Exibir a mensagem de restrição ao jogador que escolher uma questão que não esta disponível no menu.  

Resultado esperado: Nao ser exibida a questão escolhida que não esta no menu de questões.   

-  **Teste 9**  

Após o final das 8 rodadas será informada a pontuação individual dos jogadores dos jogadores e uma mensagem informando que o jogo esta encerrado. 

o vencedor será o jogador que possuir a maior pontuação.

Possívei falha: Não infomar a pontuação individual dos jogadores.  

Possível falha: Não ser exibida a mensagem sobre o encerramento do jogo.  

Resultado esperado: Mostrar a pontuação individual dos jogadores.  

Resultado esperado: Exibir a mensagem que o jogo esta finalizado.
















