


% exercício 3


0.01 :: fmotor .

0.1 :: fh.

0.05 :: fmec .

0.8 :: aaaa :- fh, fmec .
0.4 :: aaaa :- not fh, fmec .
0.4 :: aaaa :- fh, not fmec .
0.2 :: aaaa :- not fh, not fmec .


1.0 :: perda :- fmotor, aaaa .
0.6 :: perda :- fmotor, not aaaa.
0.6 :: perda :- not fmotor, aaaa.
0.0 :: perda :- not fmotor, not aaaa.



evidence(perda,true).
/*
  aaaa:	0.96798901
    fh:	0.17841235
  fmec:	0.093354979
fmotor:	0.048037298
 perda:	1   
*/

query(fmotor).
query(fh).
query(fmec).
query(aaaa).
query(perda).


/* sem evidências
  aaaa:	0.231     
    fh:	0.1       
  fmec:	0.05      
fmotor:	0.01      
 perda:	0.144138  
*/



