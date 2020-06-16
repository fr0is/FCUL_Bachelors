
% EC guião 12, ex1 (ex5 do guião 11) 




% P - ter pressa (pressa, not pressa)
% V -  velocidade (legal, e120_140, m140)
% D - ser detectado por radar (detect, not detect)
% M - ser multado (multado, not multado)


0.5 :: v(e120_140) ; 0.3 :: v(m140) ; 0.2 :: v(legal) :-
	pressa.
0.6 :: v(legal) ; 0.3 :: v(e120_140) ; 0.1 :: v(m140) :-
	not pressa.

0.2 :: detect.

0 :: multado :- v(legal).
0 :: multado :- not detect.
0.6 :: multado :- v(e120_140), detect.
0.9 :: multado :- v(m140), detect.


0.8 :: pressa.

evidence(detect,true).   % evidence(detect).
evidence(multado,false).

query(pressa).

% Resultado:
% pressa:	0.70204082 
