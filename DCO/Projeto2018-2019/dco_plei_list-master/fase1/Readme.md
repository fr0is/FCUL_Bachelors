pLEI list - Meta1
=================

**Deadline:** 31 de Março de 2019, 23:55, Hora de Lisboa

**Equipa:** O trabalho deve ser feito em grupos de 3. Excepcionalmente serão aceites grupos de 2.

**Dúvidas:** Dúvidas sobre o trabalho, bem como outras discussões como procura de elementos de grupo deverão ser feitas [neste endereço](https://git.alunos.di.fc.ul.pt/dco0001/dco_plei_list/issues).

**Instruções:** Fazer fork deste repositório. Escolher visibilidade privado (outros grupos não poderão ver o conteúdo). Em settings -> members, dar acesso de _Developer_ ao utilizador **dco000**. Poderá agora trabalhar neste novo repositório. 

**Divisão de trabalho:** Existem 3 casos de uso a trabalhar. Cada um dos membros do grupo deverá fazer um, sendo que devem colaborar de forma a estarem todos em harmonia. O modelo de domínio deverá portanto ser feito em conjunto.

**Para entregar:** Deverá preencher o ficheiro autores.txt. Deverá entregar tudo num PDF chamado ```pleilist.pdf```. Não esquecer fazer um commit no git:

```
git add autores.txt
git add pleilist.pdf
git commit -m "Adicionados autores e documentação"
```

Depois, basta criar uma nova tag git e colocá-la no servidor:

```
git tag meta1
git push origin meta1
```

Não cumprir estas instruções levará a que o projecto nem seja corrigido, resultando numa nota de 0.

Fraude
------

Como futuro profissional, espera-se de si uma atitude irrepreensível,
em termos éticos e deontológicos. Tenha pois o maior cuidado em
respeitar e fazer respeitar a lei da criminalidade informática.

A nível académico, alunos detetados em situação de fraude ou plágio
(plagiadores e plagiados) em alguma prova ficam reprovados à
disciplina e serão alvo de processo disciplinar, o que levará a um
registo dessa incidência no processo de aluno, podendo conduzir à
suspensão letiva ou abandono da Universidade.

Objectivo
---------

Nesta primeira meta pretendemos fazer a fase de análise, em que vamos
organizar o que o cliente nos transmite. Felizmente, o levantamento de casos
de uso já foi feito, e iremos trabalhar sobre eles.

Nesta primeira meta pretende-se que os alunos entreguem:

* Diagrama de Casos de Uso
* Modelo de Domínio
* SSD e contractos para os seguintes casos de uso: 1, 2 e 4.

De seguida apresenta-se uma vista geral da aplicação e a descrição dos casos de uso:


Descrição geral do pLEI list:
-----------------------------

Pretende-se que os alunos desenvolvam uma aplicação que permita a curadores criar playlists de vídeos para outros utilizadores consumirem. Cada playlist pode ter dois tipos de conteúdo: clips de duração limitada, como [vídeos do youtube](https://www.youtube.com/watch?v=GTFXkXp_pig), ou streams, que estão sempre em emissão como [o stream da RTP1](https://www.rtp.pt/play/direto/rtp1) ou da [praia do guincho](https://beachcam.meo.pt/livecams/praia-do-guincho/).

Outros utilizadores podem ver as playlists, vídeo a vídeo, podendo classificar cada vídeo de 1 a 5 estrelas. 



UC1 - Adicionar vídeo à biblioteca
------------------------------------------

**Actor Principal:** Curador

**Pré-condição:** Existe um curador autenticado

**Pós-condição:** Se foi tudo introduzido correctamente, o sistema tem mais um vídeo.

**Cenário Principal (ou _Happy Path_):**

1. O curador indica ao sistema que pretende adicionar um vídeo.
2. O sistema pergunta ao curador se pretende indicar um clip (vídeo de duração limitada) ou um stream (transmissão contínua)
3. O curador indica a sua escolha.
4. O sistema pede ao curador o título do vídeo e o endereço onde se encontra.
5. O curador indica ao sistema esses dados.
6. No caso do clip, o sistema pede ao curador a duração do mesmo.
7. O curador indica a duração.

    Os passos 6-7 só existem no caso do clip e não do stream.

8. O sistema pede ao curador que indique um hashtag para associar ao vídeo.
9. O utilizador indica ao sistema um hashtag que pretenda associar.

    Os passos 8-9 podem ser repetidos enquanto o curador pretender.

10. O sistema pergunta ao curador se o vídeo poderá ser usado por outros utilizadores
11. O curador indica se a o vídeo será público ou privado e o sistema regista esse vídeo na sua biblioteca pessoal ou na biblioteca geral, devolvendo um novo código associado a esse vídeo.

**Extensões:**

5a. Se o endereço do vídeo não existir, o sistema indica que esse endereço é inválido, e pede outra vez os dados do clip.



UC2 - Criar playlist manualmente
------------------------------------------

**Actor Principal:** Curador

**Pré-condição:** Existe um curador autenticado

**Pós-condição:** Se foi tudo introduzido correctamente, o sistema tem mais uma playlist, associada a todos os tags que estejam relacionados com pelo menos um dos vídeos contidos.

**Cenário Principal (ou _Happy Path_):**

1. O curador indica ao sistema que pretende criar uma nova playlist, com um determinado nome.
2. O sistema cria uma playlist com esse nome, um novo código, e sem qualquer video.
3. O curador pede ao sistema a constituição actual da playlist
4. O sistema devolve uma listagem dos vários items da playlist, com a hora de início de cada um.
5. O sistema pede ao curador um hashtag.
6. O curador indica um hashtag relacionado com o vídeo que pretende adicionar à playlist.
7. O sistema devolve uma lista de vídeos relacionados com esse hashtag, indicando o código e o título de cada um. Este vídeos podem vir ou da sua biblioteca pessoal ou da biblioteca geral comum a todos os utilizadores.
8. O curador indica ao sistema o código do vídeo que quer introduzir na playlist.
9. O sistema indica ao curador se o vídeo é um stream ou não.
10. Se o vídeo for um stream, o curador indica ao sistema a duração de transmissão pretendida.
11. O sistema regista essa informação

    Os passos 8-11 podem ser repetidos enquanto o curador pretender.
    Os passos 3-11 podem ser repetidos enquanto o curador pretender.

12. O curador indica que pretende terminar.

**Extensões:**

7a. No caso de não existirem vídeos com aquele hashtag, o sistema pergunta se o utilizador pretende ver a lista dos top 10 vídeos mais vistos. Se o utilizador responder afirmamente, o sistema devolve a lista dos top 10 vídeos, com a hora de início de cada um. No caso de existirem menos de 10 vídeos, mostra todos.

8a. Se o código do vídeo não for conhecido, o sistema deverá dar um erro e pedir um novo código.


UC3 - Criar playlist automaticamente
------------------------------------------

Este caso de uso ficou para uma iteração seguinte.

Um utilizador pode pedir para ser gerada uma playlist automática segundo determinados critérios, sem necessitar de um curador.



UC4 - Visualizar uma playlist
------------------------------------------

**Actor Principal:** Utilizador

**Pré-condição:** Existe um utilizador autenticado

**Pós-condição:** Existe mais uma classificação (rating) para cada vídeo visto.

**Cenário Principal (ou _Happy Path_):**

1. O utilizador indica que pretende visualizar uma playlist sobre um determinado hashtag.
2. O sistema devolve uma lista de playlists relacionadas com esse hashtag, referindo o nome, código e classificação média dos seus vídeos.
3. O utilizador indica o código de uma das playlists.
4. O sistema devolve a duração total da playlist.
5. O utilizador pede qual o próximo vídeo a ver.
6. O sistema indica o endereço desse vídeo, bem como a sua duração.
7. O utilizador indica ao sistema a classificação (de 1 a 5 estrelas) que atribui àquele vídeo.
8. O sistema regista esta informação.
    Os passos 5-8 são repetidos enquanto existirem vídeos na playlist por ver ou o utilizador quiser sair.


UC5 - Denunciar vídeo como copyright-protected
----------------------------------------------

Este caso de uso ficou para uma iteração seguinte.

Um utilizador autenticado reporta um determinado vídeo como sendo uma violação de copyright indicando o código do vídeo.




