<h1 class="code-line" data-line-start=0 data-line-end=1 ><a id="OOChess_0"></a>OOChess</h1>
<p class="has-line-data" data-line-start="1" data-line-end="2">O seu grupo foi contratado para desenvolver uma aplicação para gestão de torneios de Xadrez. Iremos usar uma metodologia ágil, onde iremos desenvolver as funcionalidades mais importantes e/ou complexas primeiro, e onde iremos iterar e refinar o design ao longo do projecto.</p>
<p class="has-line-data" data-line-start="3" data-line-end="5">Definição de Casos de Uso<br>
A aplicação deverá permitir as seguintes funcionalidades gerais:</p>
<p class="has-line-data" data-line-start="6" data-line-end="14">Registo de utilizador<br>
Login de utilizador<br>
Criação de torneio (com data de início e fim)<br>
Inscrição em torneio<br>
Desafiar para partida<br>
Registar resultado de partida<br>
Aceitação de desafios para partida<br>
Nesta primeira iteração vamo-nos focar nos casos de uso #5 e #6 No caso de grupos de 3 elementos, devem também desenvolver o caso de uso #7.</p>
<p class="has-line-data" data-line-start="15" data-line-end="17">Caso de Uso #5: Desafiar para Partida<br>
Este caso de uso permite ao jogador autenticado desafiar um ou mais jogadores para uma ou mais partidas respectivamente.</p>
<p class="has-line-data" data-line-start="18" data-line-end="27">O jogador tem a opção de indicar o torneio a que a partida vai pertencer. Este passo não é obrigatório porque podem existir partidas amigáveis fora de torneios.<br>
O jogador indica ao sistema o delta de ELO que é aceitável. O ELO é uma métrica de ranking usada no nosso sistema, que vai sendo actualizada conforme os jogadores ganham ou perdem.<br>
O sistema responde ao jogador com a lista de jogadores e respectivos ELOs que tenham uma diferença absoluta entre o seu ELO e do jogador autenticado inferior ao delta.<br>
O jogador indica ao sistema o username do jogador com quer estabelecer uma partida.<br>
De seguida, o jogador indica a hora e data da partida, bem como uma mensagem opcional (smack talk!).<br>
O sistema devolve ao jogador um código correspondente ao desafio da partida. O jogador desafiado deverá receber uma mensagem pelo Discord.<br>
Os passos 4-6 podem ser repetidos tantas vezes quanto o utilizador pretender.<br>
Caso de Uso #6: Registar Resultado de Partida<br>
Este caso de uso permite ao jogador autenticado registar o resultado de um jogo em que esteve envolvido.</p>
<p class="has-line-data" data-line-start="28" data-line-end="35">O jogador tem a opção de indicar o desafio correspondente a esta partida, indicando o seu código.<br>
Em vez do código, o utilizador pode indicar que quer registar uma partida espontânea em que não existiu convite associado.<br>
No caso de ser uma partida espontânea, o sistema deve indicar a lista dos usernames dos 5 últimos jogadores com quem jogou (rematches são comuns).<br>
Ainda no caso de ser uma partida espontânea, o utilizador indica o username do seu oponente, bem como a data em que ocorreu a partida.<br>
Em qualquer dos casos, o utilizador indica o resultado da partida (ganhou, perdeu ou empatou).<br>
O sistema calcula, regista e devolve o seu novo ELO. Podem usar este algoritmo para actualizar o ELO, devendo usar K=50.<br>
Extensões:</p>
<p class="has-line-data" data-line-start="36" data-line-end="39">No caso do código do convite ser inválido, o sistema deve informar o utilizador desse facto.<br>
Caso de Uso #7:<br>
Este caso de uso permite ao jogador autenticado consultar e aceitar ou rejeitar desafios para partidas.</p>
<p class="has-line-data" data-line-start="40" data-line-end="45">O jogador indica que pretende consultar os seus desafios pendentes.<br>
O sistema devolve a lista de desafios ainda sem resposta.<br>
O utilizador indica que pretende aceitar ou não um determinado desafio, indicando o código respectivo.<br>
O passo 3 é repetidos enquanto existirem desafios por responder.<br>
Extensões:</p>
<p class="has-line-data" data-line-start="46" data-line-end="49">No caso do desafio estar associado a um torneio e após a sua rejeição, o utilizador indica uma nova data que criará um novo desafio de si para o jogador que inicialmente o desafiou.<br>
Iteração #1<br>
Pretende-se que os alunos entreguem:</p>
<p class="has-line-data" data-line-start="50" data-line-end="57">Diagrama de Casos de Uso<br>
SSD Caso de Uso #5 - Aluno com o menor número de aluno.<br>
SSD Caso de Uso #6 - Aluno com o segundo menor número de aluno.<br>
SSD Caso de Uso #7 - Aluno com o terceiro menor número de aluno, no caso de existir.<br>
Modelo de Domínio<br>
Contratos das várias operações presentes no SSD (aluno respectivo)<br>
A entrega deverá ser um ficheiro PDF. Não estamos interessados na tecnologia (papel+lápis ou software) usados. Importa que o PDF seja legível. Cada caso de uso deve ter indicado o seu autor, segundo as regras acima indicadas.</p>
<p class="has-line-data" data-line-start="58" data-line-end="59">As secções com aluno atribuído deverão ser feitas por esse aluno, embora possa contar com a ajuda dos colegas de grupo. Nas defesas do trabalho, as perguntas serão sobretudo (mas não exclusivamente!!!) sobre os casos de uso atribuídos. Será importante ter um conhecimento do trabalho completo. Todos os alunos devem colaborar na definição do Modelo de Domínio.</p>
<p class="has-line-data" data-line-start="60" data-line-end="61">Deverão contactar com o cliente através do Fórum do Moodle, visível para todos os alunos. Devem fazer as perguntas de forma a não revelarem a vossa solução. Não serão respondidas questões de interpretação por email.</p>
