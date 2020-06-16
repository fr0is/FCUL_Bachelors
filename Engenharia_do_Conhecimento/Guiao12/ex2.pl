


% exercício 2


0.8 :: f2 :- dx2, dy.
0.5 :: f2 :- dx2, not dy.
0.5 :: f2 :- not dx2, dy.
0.0 :: f2 :- not dx2, not dy.

0.7 :: f1 :- dx1, dy.
0.0 :: f1 :- dx1, not dy. 
0.0 :: f1 :- not dx1, dy.
0.0 :: f1 :- not dx1, not dy.


0.05 :: dx1 .
0.05 :: dx2 .
0.03 :: dy .


evidence(f2,true).
evidence(f1,false).

query(f1).
query(f2).
query(dx1).
query(dx2).
query(dy).


/*

dx1:	0.036881452
dx2:	0.64883776
 dy:	0.3807338 
 f1:	0         
 f2:	1   
*/

