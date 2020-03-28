#Grupo 40 Seguranca e Confibilidade
#51023 - Alexandre Monteiro
#51050 - Antonio Frois
#15775 - Hugo Diogo

JFLAGS = -g -d bin
JC = javac
SRC_dir = src/
BIN_dir = bin/

all:
	find -name "*.java" > sources.txt
	$(JC) $(JFLAGS) @sources.txt

clean: 
	$(RM) $(BIN_dir)*.class