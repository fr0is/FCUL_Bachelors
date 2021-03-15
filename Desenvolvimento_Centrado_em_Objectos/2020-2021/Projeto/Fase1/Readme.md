OOChess
O seu grupo foi contratado para desenvolver uma aplicação para gestão de torneios de Xadrez. Iremos usar uma metodologia ágil, onde iremos desenvolver as funcionalidades mais importantes e/ou complexas primeiro, e onde iremos iterar e refinar o design ao longo do projecto.

Definição de Casos de Uso
A aplicação deverá permitir as seguintes funcionalidades gerais:

Registo de utilizador
Login de utilizador
Criação de torneio (com data de início e fim)
Inscrição em torneio
Desafiar para partida
Registar resultado de partida
Aceitação de desafios para partida
Nesta primeira iteração vamo-nos focar nos casos de uso #5 e #6 No caso de grupos de 3 elementos, devem também desenvolver o caso de uso #7.

Caso de Uso #5: Desafiar para Partida
Este caso de uso permite ao jogador autenticado desafiar um ou mais jogadores para uma ou mais partidas respectivamente.

O jogador tem a opção de indicar o torneio a que a partida vai pertencer. Este passo não é obrigatório porque podem existir partidas amigáveis fora de torneios.
O jogador indica ao sistema o delta de ELO que é aceitável. O ELO é uma métrica de ranking usada no nosso sistema, que vai sendo actualizada conforme os jogadores ganham ou perdem.
O sistema responde ao jogador com a lista de jogadores e respectivos ELOs que tenham uma diferença absoluta entre o seu ELO e do jogador autenticado inferior ao delta.
O jogador indica ao sistema o username do jogador com quer estabelecer uma partida.
De seguida, o jogador indica a hora e data da partida, bem como uma mensagem opcional (smack talk!).
O sistema devolve ao jogador um código correspondente ao desafio da partida. O jogador desafiado deverá receber uma mensagem pelo Discord.
Os passos 4-6 podem ser repetidos tantas vezes quanto o utilizador pretender.
Caso de Uso #6: Registar Resultado de Partida
Este caso de uso permite ao jogador autenticado registar o resultado de um jogo em que esteve envolvido.

O jogador tem a opção de indicar o desafio correspondente a esta partida, indicando o seu código.
Em vez do código, o utilizador pode indicar que quer registar uma partida espontânea em que não existiu convite associado.
No caso de ser uma partida espontânea, o sistema deve indicar a lista dos usernames dos 5 últimos jogadores com quem jogou (rematches são comuns).
Ainda no caso de ser uma partida espontânea, o utilizador indica o username do seu oponente, bem como a data em que ocorreu a partida.
Em qualquer dos casos, o utilizador indica o resultado da partida (ganhou, perdeu ou empatou).
O sistema calcula, regista e devolve o seu novo ELO. Podem usar este algoritmo para actualizar o ELO, devendo usar K=50.
Extensões:

No caso do código do convite ser inválido, o sistema deve informar o utilizador desse facto.
Caso de Uso #7: 
Este caso de uso permite ao jogador autenticado consultar e aceitar ou rejeitar desafios para partidas.

O jogador indica que pretende consultar os seus desafios pendentes.
O sistema devolve a lista de desafios ainda sem resposta.
O utilizador indica que pretende aceitar ou não um determinado desafio, indicando o código respectivo.
O passo 3 é repetidos enquanto existirem desafios por responder.
Extensões:

No caso do desafio estar associado a um torneio e após a sua rejeição, o utilizador indica uma nova data que criará um novo desafio de si para o jogador que inicialmente o desafiou.
Iteração #1
Pretende-se que os alunos entreguem:

Diagrama de Casos de Uso
SSD Caso de Uso #5 - Aluno com o menor número de aluno.
SSD Caso de Uso #6 - Aluno com o segundo menor número de aluno.
SSD Caso de Uso #7 - Aluno com o terceiro menor número de aluno, no caso de existir.
Modelo de Domínio
Contratos das várias operações presentes no SSD (aluno respectivo)
A entrega deverá ser um ficheiro PDF. Não estamos interessados na tecnologia (papel+lápis ou software) usados. Importa que o PDF seja legível. Cada caso de uso deve ter indicado o seu autor, segundo as regras acima indicadas.

As secções com aluno atribuído deverão ser feitas por esse aluno, embora possa contar com a ajuda dos colegas de grupo. Nas defesas do trabalho, as perguntas serão sobretudo (mas não exclusivamente!!!) sobre os casos de uso atribuídos. Será importante ter um conhecimento do trabalho completo. Todos os alunos devem colaborar na definição do Modelo de Domínio.

Deverão contactar com o cliente através do Fórum do Moodle, visível para todos os alunos. Devem fazer as perguntas de forma a não revelarem a vossa solução. Não serão respondidas questões de interpretação por email.
