OBJ_dir = obj
OBJECTOS = cliente.o control.o empresa.o file.o intermediario.o main.o memory.o prodcons.o scheduler.o time.o
TIMER = 100
OUT = ./bin/out
SOPERPRO = ./bin/soperpro


all:
	make out
	make runNtest

runNtest:
	make runSO
	make runAll
	make teste

valgrind:
	valgrind --leak-check=full --show-leak-kinds=all $(OUT) tests/in/scenario1 tests/out/result1.txt -l tests/log/log1.txt -t $(TIMER)

out: $(OBJECTOS)
	gcc $(addprefix $(OBJ_dir)/,$(OBJECTOS)) obj/so.o -lrt -lpthread -o bin/out

%.o: src/%.c $($@)
	gcc -I include -Wall -g -o $(OBJ_dir)/$@ -c $<

clean:
	rm -f $(addprefix $(OBJ_dir)/,$(OBJECTOS))
	rm -f bin/out

runAll:
	make run1
	make run2
	make run3
	make run4

run1:
	$(OUT) tests/in/scenario1 tests/out/result1.txt -l tests/log/log1.txt -t $(TIMER)

run2:
	$(OUT) tests/in/scenario2 tests/out/result2.txt -l tests/log/log2.txt -t $(TIMER)

run3:
	$(OUT) tests/in/scenario3 tests/out/result3.txt -l tests/log/log3.txt -t $(TIMER)

run4:
	$(OUT) tests/in/scenario4 tests/out/result4.txt -l tests/log/log4.txt -t $(TIMER)

teste:
	chmod u+x script.sh
	./script.sh tests/out/result1.txt tests/out/resultsSO1.txt
	./script.sh tests/out/result2.txt tests/out/resultsSO2.txt
	./script.sh tests/out/result3.txt tests/out/resultsSO3.txt
	./script.sh tests/out/result4.txt tests/out/resultsSO4.txt

runSO:
	$(SOPERPRO) tests/in/scenario1 tests/out/resultsSO1.txt -l tests/log/logSO1.txt -t $(TIMER)
	$(SOPERPRO) tests/in/scenario2 tests/out/resultsSO2.txt -l tests/log/logSO2.txt -t $(TIMER)
	$(SOPERPRO) tests/in/scenario3 tests/out/resultsSO3.txt -l tests/log/logSO3.txt -t $(TIMER)
	$(SOPERPRO) tests/in/scenario4 tests/out/resultsSO4.txt -l tests/log/logSO4.txt -t $(TIMER)
