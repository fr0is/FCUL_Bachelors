import java.util.Random;
import java.util.Scanner;

/**
 * Esta classe joga o jogo "Lightmare in Moniusland" interagindo com o 
 * utilizador
 * @author IP
 * @date Novembro de 2017
 */
public class LightmareMoniusland3 {

    /**
     * O metodo experimentaJogar3 da classe JogarLightmare3 eh invocado
     * para dados valores dos seus parametros
     * @param args - Nao utilizados
     */
    public static void main(String[] args) {
	    
        // Objetos para leitura do standard input e para geracao de aleatorios
    	Scanner leitor = new Scanner(System.in);
        Random gerador = new Random(1);

        // Numero de linhas e colunas do caminho
        System.out.println("Quantas linhas tem o caminho? (entre 1 e 60)");
        int linhas = FuncoesMonius3.lerValorNoIntervalo(1, 60, "Inteiro entre 1 e 60!", leitor);
        System.out.println("Quantas colunas tem o caminho? (entre 1 e 60)");
        int colunas = FuncoesMonius3.lerValorNoIntervalo(1, 60, "Inteiro entre 1 e 60!", leitor);
        // Para consumir a mudanca de linha:
        leitor.nextLine();

        // Definicao dos valores do alcance da luz e angulo 
        int alcanceLuz = Math.max(1, colunas / 2); 
        int angulo = 180; 

        // Numero maximo de jogadas
        int maxVezes = 10;
        // Quantos Monius
        int nMonius = Math.max(1, linhas * colunas / 8);

        // Posicoes iniciais dos Monius - valores aleatorios todos diferentes
        int [] posMonius = FuncoesMonius3.aleatoriosDiferentes(nMonius, linhas * colunas, gerador);       		                                            

         // Jogar o jogo!
        JogarLightmare3.experimentaJogar3(posMonius, angulo, alcanceLuz, linhas, colunas,maxVezes, gerador, leitor);
    }
}
