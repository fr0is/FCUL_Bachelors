#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <semaphore.h>
#include <time.h>
#include <pthread.h>
#include <errno.h>
#include <sys/mman.h> //mmap
#include <sys/stat.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <ctype.h>
#include <signal.h>
#include <limits.h>

#include "main.h"
#include "so.h"
#include "memory.h"
#include "prodcons.h"
#include "file.h"
#include "sotime.h"
#include "scheduler.h"

//==============================================
// DECLARAR ACESSO A DADOS EXTERNOS
//
extern struct configuration Config;
//==============================================

struct file Ficheiros; // informação sobre nomes e handles de ficheiros

void file_begin(char *fic_in, char *fic_out, char *fic_log) {
	//==============================================
	// GUARDAR NOMES DOS FICHEIROS NA ESTRUTURA Ficheiros
	//
	so_file_begin(fic_in, fic_out, fic_log);
	//==============================================

	//==============================================
	// ABRIR FICHEIRO DE ENTRADA
	// em modo de leitura
	so_file_open_file_in();
	//==============================================

	// parse do ficheiro de teste
	// esta funcao prepara os campos da estrutura Config (char *)
	// com os dados do ficheiro de entrada
	if (ini_parse_file(Ficheiros.h_in, handler, &Config) < 0) {
		printf("Erro a carregar o ficheiro de teste'\n");
		exit(1);
	}

	// agora e' preciso inicializar os restantes campos da estrutura Config

	//==============================================
	// CONTAR SERVICOS
	// usar strtok para percorrer Config.list_servicos
	// guardar resultado em Config.SERVICOS
	so_file_count_servicos();
	//==============================================

	// iniciar memoria para o vetor com o capacidade de servicos por servico e semaforo
	memory_create_capacidade_servicos();
	prodcons_create_stock();

	//==============================================
	// LER CAPACIDADE DE SERVICO DE CADA SERVICO
	// percorrer Config.list_servicos e
	// guardar cada valor no vetor Config.capacidade_servicos
	so_file_read_capacidade_servicos();
	//==============================================

	//==============================================
	// CONTAR CLIENTES
	// usar strtok para percorrer Config.list_clientes
	// guardar resultado em Config.CLIENTES
	so_file_count_clientes();
	//==============================================

	//==============================================
	// CONTAR INTERMEDIARIOS
	// usar strtok para percorrer Config.list_intermediarios
	// guardar resultado em Config.INTERMEDIARIOS
	so_file_count_intermediarios();
	//==============================================

	//==============================================
	// CONTAR EMPRESAS
	// usar strtok para percorrer Config.list_empresas
	// guardar resultado em Config.EMPRESAS
	so_file_count_empresas();
	//==============================================

	so_file_read_servicos();

	//==============================================
	// LER CAPACIDADES DOS BUFFERS
	// usar strtok para percorrer Config.list_buffers
	// guardar primeiro tamanho em Config.BUFFER_DESCRICAO
	// guardar segundo tamanho em Config.BUFFER_ORCAMENTO
	// guardar terceiro tamanho em Config.BUFFER_PROPOSTA
	so_file_read_capacidade_buffer();
	//==============================================

	//==============================================
	// ABRIR FICHEIRO DE SAIDA (se foi especificado)
	// em modo de escrita
	so_file_open_file_out();
	//==============================================

	//==============================================
	// ABRIR FICHEIRO DE LOG (se foi especificado)
	// em modo de escrita
	so_file_open_file_log();
	//==============================================
}

void file_destroy() {
	//==============================================
	// LIBERTAR ZONAS DE MEMÓRIA RESERVADAS DINAMICAMENTE
	// que podem ser: Ficheiros.entrada, Ficheiros.saida, Ficheiros.log
	so_file_destroy();
	//==============================================
}

void file_write_log_file(int etapa, int id) {
	double t_diff;

	if (Ficheiros.h_log != NULL) {

		prodcons_buffers_begin();

		// guardar timestamp
		t_diff = time_untilnow();

		//==============================================
		// ESCREVER DADOS NO FICHEIRO DE LOG
		//
		// o log deve seguir escrupulosamente o formato definido
		so_file_write_log_file(etapa, id, t_diff);
		//==============================================

		prodcons_buffers_end();
	}
}

void file_write_line(char * linha) {
	//==============================================
	// ESCREVER UMA LINHA NO FICHEIRO DE SAIDA
	//
	so_file_write_line(linha);
	//==============================================
}

int stricmp(const char *s1, const char *s2) {
	if (s1 == NULL)
		return s2 == NULL ? 0 : -(*s2);
	if (s2 == NULL)
		return *s1;

	char c1, c2;
	while ((c1 = tolower(*s1)) == (c2 = tolower(*s2))) {
		if (*s1 == '\0')
			break;
		++s1;
		++s2;
	}

	return c1 - c2;
}

int handler(void* user, const char* section, const char* name,
		const char* value) {
	struct configuration* pconfig = (struct configuration*) user;

#define MATCH(s, n) stricmp(section, s) == 0 && stricmp(name, n) == 0
	if (MATCH("servicos", "capacidade_servicos")) {
		pconfig->list_servicos = strdup(value);
	} else if (MATCH("clientes", "servico")) {
		pconfig->list_clientes = strdup(value);
	} else if (MATCH("intermediarios", "list")) {
		pconfig->list_intermediarios = strdup(value);
	} else if (MATCH("empresas", "servicos")) {
		pconfig->list_empresas = strdup(value);
	} else if (MATCH("buffers", "capacidade_buffer")) {
		pconfig->list_buffers = strdup(value);
	} else {
		return 0; /* unknown section/name, error */
	}
	return 1;
}

