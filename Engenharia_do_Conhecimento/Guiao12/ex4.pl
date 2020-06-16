


% exercicio 4


0.3 :: credito :- id(mtBoa), not rendimento, fiador.
0.0 :: credito :- id(mtBoa), not rendimento, not fiador.
1.0 :: credito :- id(mtBoa), rendimento, fiador.
1.0 :: credito :- id(mtBoa), rendimento, not fiador.
0.0 :: credito :- id(boa), not rendimento, fiador.
0.0 :: credito :- id(boa), not rendimento, not fiador.
0.8 :: credito :- id(boa), rendimento, fiador.
0.4 :: credito :- id(boa), rendimento, not fiador.

0.0 :: credito :- id(ma), not rendimento, fiador.
0.0 :: credito :- id(ma), not rendimento, not fiador.
0.0 :: credito :- id(ma),  rendimento,  fiador.
0.0 :: credito :- id(ma),  rendimento,  not fiador.


1.0 :: id(ma); 0 :: id(boa); 0:: id(mtBoa) :- cheques, incumprimento.
0.9 :: id(mtBoa); 0.1:: id(boa); 0 :: id(ma):- not cheques, not incumprimento.
0.6 :: id(boa); 0.2:: id(ma); 0.2 :: id(mtBoa):- not cheques, incumprimento.
0.6 :: id(boa); 0.2:: id(ma); 0.2 :: id(mtBoa):- cheques, not incumprimento.

0.5 :: incumprimento.
0.5 :: cheques.
0.5 :: fiador.
0.5 :: rendimento.


evidence(credito, false).
evidence(incumprimento, false).



query(id(boa))
query(id(mtBoa)).

/*
 id(boa):  0.42332613
 id(mtBoa): 0.40388769
 */