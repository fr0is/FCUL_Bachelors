Grupo 40 Seguranca e Confibilidade
51023 - Alexandre Monteiro
51050 - Antonio Frois
15775 - Hugo Diogo
 

Ao extrair o zip deve ter encontrado esta pasta com os ficheiros .policy, um makefile, e as pastas src e bin.
Elas devem-se encontrar todas onde estão para o funcionamento correto, o makefile serve apenas para compilar o
 projeto para utilizadores da linha de comandos no linux, se o projeto for importado para o Eclipse o makefile 
 nao sera necessario. Neste readme ira encontrar instrucoes de como correr este trabalho no eclipse e na linha de 
 comandos(em Linux), e tambem se encontra aqui descrito ao que o cliente e o servidor tem acesso.

Como executar:

--------------------------Eclipse--------------------------
   -Importar esta pasta do projeto para o Eclipse 
   	(Import > General > Projects from folder or archive > selecionar a pasta (nao o zip) que contem o projeto)

   -Ligar o MequieServer(servidor) primeiro:
	-Clicar no Run Configurations para configurar os argumentos
	-Mudar para o separador Arguments
	-Nos argumentos do programa (Program arguments) inserir os argumentos necessarios: <port>
	(Exemplo: 23456)
	-Nos VM arguments inserir o seguinte: -Djava.security.manager -Djava.security.policy=server.policy
	-Clicar no Apply e de seguida no Run

   -O servidor esta agora ligado e a aguardar utilizadores

   -Ligar o Mequie(cliente) a seguir:
	-Clicar no Run Configurations para configurar os argumentos
	-Mudar para o separador Arguments
	-Nos argumentos do programa (Program arguments) inserir os argumentos necessarios: <serverAddress> <localUserID> [password]
	(Exemplo: localhost:23456 MrXano ElXano)
	-Nos VM arguments inserir o seguinte: -Djava.security.manager -Djava.security.policy=client.policy
	-Clicar no Apply e de seguida no Run

   -O Cliente esta neste momento ligado ao servidor
   -Caso o servidor esteja desligado o cliente avisa que nao se ligou ao servidor ao iniciar e termina

--------------------------Linha de comandos(Linux)--------------------------
   -Na linha de comandos fazer make para compilar o codigo

   -Ligar o MequieServer primeiro, para tal introduzir na linha de comandos o seguinte:

	java -classpath bin -Djava.security.manager -Djava.security.policy=server.policy MequieServer <port>

	   Em que:
		<port> - porto TCP para aceitar ligacoes (Exemplo: 23456)

   -Ligar o Mequie, na linha de comandos introduzir:

	java -classpath bin -Djava.security.manager -Djava.security.policy=client.policy Mequie <serverAddress> <localUserID> [password]

	   Em que:
		<serverAddress> - identifica o servidor (Exemplo: localhost:23456)
		<localUserID> - o nome do utilizador local (Exemplo: MrXano)
		[password] - password do utilizador (Exemplo: ElXano)(NOTA: Este parametro eh opcional, se nao for dado na linha de comandos o cliente pede a password antes de se autenticar no servidor
	

Limitacoes:

	Ambas as pontas podem usar qualquer endereco, porta ou IP para poderem comunicar.

	O cliente pode ler e escrever na pasta clienteData que contem para cada utilizador uma pasta que guarda as fotos vindas dos grupos em que estao incluidos,
	e pode ler apenas as fotos em qualquer local da maquina para poder enviar para o servidor
	
	O servidor pode ler e escrever na pasta serverData que contem informacoes sobre os utilizadores registados no sistema, os grupos criados, pode gerir as mensagens de cada grupo 
	e fotos tambem, e pode ler e escrever no ficheiro que contem as credenciais de cada utilizador.