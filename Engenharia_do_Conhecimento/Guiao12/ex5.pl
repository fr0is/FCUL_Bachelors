% exercicio 5

0.4 :: turistas.

0.1 :: incendiarios.

0.95 :: incendio :- turistas, incendiarios.
0.4 :: incendio :- turistas, not incendiarios.
0.9 :: incendio :- not turistas, incendiarios.
0.01 :: incendio :- not turistas, not incendiarios.

0.05 :: interferencia.

0.01 :: alarme :- not  incendio, interferencia.
0.9 :: alarme :- incendio, not interferencia.
0.95 :: alarme :- incendio,  interferencia.
0.0 :: alarme :- not incendio, not interferencia.

0.9 :: controlo :- alarme.
0.0 :: controlo :- not alarme.

evidence(alarme).
evidence(interferencia, false).

query(incendiarios).