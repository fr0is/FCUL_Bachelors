import java.util.Random;
import java.util.Scanner;

/**
 * Esta classe contem a funcao foiAtingido, e outras, pedida aos 
 * alunos na Fase 1 do trabalho, e ainda metodos para gerar um
 * array de aleatorios diferentes e metodos para leitura robusta 
 * de valores a partir do standard input
 * @author Isabel Nunes
 * @date Novembro 2017
 */
public class FuncoesMonius3 {

    /**
     * Um Outro eh atingido pelo sabre de luz de um Monius?
     * @param posMonius - Posicao do Monius 
     * @param posOutro - Posicao do Outro 
     * @param sabre - Tamanho do sabre de luz
     * @param angulo - Angulo de movimento do sabre de luz
     * @param comprim - Numero de colunas do caminho
     * @param altura - Numero de linhas do caminho
     * 
     * @requires posMonius >= 1 && posMonius <= comprim * altura && 
     *           posOutro >= 1 && posOutro < = comprim * altura && 
     *           angulo in {-90, 90, -180, 180, 360} &&
     *           comprim > 0 && altura > 0 && sabre >= 0
     * @return true se o Outro que se encontra na posicao posOutro eh 
     *         atingido pelo movimento que o Monius, que estah na 
     *         posicao posMonius faz com o seu sabre de luz, de    
     *         angulo graus, num caminho com comprim * altura posicoes
     *         false c.c.
     */
     public static boolean foiAtingido(int posMonius, int posOutro, 
                                       int sabre, double angulo, 
                                       int comprim, int altura) {
		
          int linhaMonius = linha (posMonius, comprim);
          int colunaMonius = coluna (posMonius, comprim, linhaMonius);
          int linhaOutro = linha (posOutro, comprim);
          int colunaOutro = coluna (posOutro, comprim, linhaOutro);
		
          double distancia = distancia (linhaMonius, colunaMonius, 
        		                        linhaOutro, colunaOutro);
		
          boolean possivelAtingir = distancia < sabre || 
                                    igual (distancia, sabre, 0.0001);
		
          // se estah ao alcance do sabre, falta ver se a amplitude
          //  do golpe o afeta, no caso de ser menor que 360
          if (possivelAtingir && angulo < 360){
               possivelAtingir = aoAlcanceSabre (linhaMonius, colunaMonius, 
            		                         linhaOutro, colunaOutro,angulo);
          }
		
          return possivelAtingir;		
     }

     /**
      * Um dado ponto eh atingido por um dado movimento circular?
      * @param xCentro - Abcissa do centro da circunferencia
      * @param yCentro - Ordenada do centro da circunferencia
      * @param xAlvo - Abcissa da posicao alvo
      * @param yAlvo - Ordenada da posicao alvo
      * @param angulo - Angulo de varrimento do movimento circular
      * @requires xCentro >= 1 && yCentro >= 1 && xAlvo >= 1 && yAlvo >= 1 &&
      *           angulo in {-90, 90, -180, 180, 360}
      * @return true se a posicao (xAlvo,yAlvo) eh atingida por um 
      *         movimento de angulo graus com centro em (xCentro,yCentro)
      */
     private static boolean aoAlcanceSabre(int xCentro, int yCentro,
                                           int xAlvo, int yAlvo, 
                                           double angulo) {
		boolean result = igual(angulo, 360, 0.0001);
		
		if (igual(Math.abs(angulo), 180, 0.0001)){
			result = xCentro % 2 == 1 ? yAlvo >= yCentro : yAlvo <= yCentro;
		}
		
		if (igual(Math.abs(angulo), 90, 0.0001)){
			if (xCentro % 2 == 1 && yAlvo >= yCentro){
				result = angulo > 0 ? xAlvo >= xCentro : xAlvo <= xCentro;
			}
			if (xCentro % 2 == 0 && yAlvo <= yCentro){
				result = angulo > 0 ? xAlvo <= xCentro : xAlvo >= xCentro;
			}
				
		}
		return result;
	}
		
     /**
      * Um numero real eh igual a outro a menos de um erro?
      * @param a - Um numero
      * @param b - Outro numero
      * @param erro - Tolerancia
      * @return True se a eh igual a b a menos de erro
      */
      public static boolean igual(double a, double b, double erro) {
		   return Math.abs(a - b) < erro;
      }

      /**
       * Distancia euclidiana entre dois pontos
       * @param x1 - Abcissa de uma posicao
       * @param y1 - Ordenada de uma posicao
       * @param x2 - Abcissa da outra posicao
       * @param y2 - Ordenada da outra posicao
       * @requires x1 >= 1 && y1 >= 1 && x2 >= 1 && y2 >= 1
       * @return Distancia euclidiana entre os pontos (x1,y1) e (x2,y2)
       */
       private static double distancia(int x1, int y1, int x2, int y2) {

     	    double somaQuads = Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
            return Math.sqrt(somaQuads);
       }

     /**
      * Linha correspondente a uma dada posicao num dado tabuleiro
      * @param pos - Posicao a calcular
      * @param comprim - Numero de colunas do tabuleiro
      * @requires pos >= 1 && comprim >= 1
      * @return A linha correspondente ah posicao pos num 
      *         tabuleiro com comprim colunas
      */
      public static int linha(int pos, int comprim) {
            int linhas = pos / comprim;
            if (pos % comprim > 0){
                  linhas++;
            }
            return linhas;
      }

     /**
      * Coluna correspondente a uma dada posicao num dado tabuleiro
      * @param pos - Posicao a calcular
      * @param comprim - Numero de colunas do tabuleiro
      * @param linha - Linha no tabuleiro correspondente a pos
      * @requires pos >= 1 && comprim >= 1 && linha >= 1
      * @return A coluna, na linha linha, correspondente ah posicao  
      *         pos num tabuleiro com comprim colunas
      */
      public static int coluna(int pos, int comprim, int linha) {
            int resto = pos % comprim;
            int result;
            if (linha % 2 == 1){
            	result = resto == 0 ? comprim : resto;
            } else {
            	result = resto == 0 ? 1 : comprim + 1 - resto;
            }
            return result;
      }


  	/**
       * Um vetor de valores aleatorios, todos diferentes, num dado intervalo
       * @param n - O numero de elementos que o vetor vai ter
       * @param sup - O limite superior do intervalo
       * @param g - O gerador de aleatorios
       * @requires n <= sup (para poderem ser todos diferentes)
       */
      public static int[] aleatoriosDiferentes (int n, int sup, Random g){
          int[] result = new int [n];
          int i = 0;
          while (i < result.length) {
              int aleatorio = g.nextInt(sup) + 1;
              if (!contidoEmParte(aleatorio,result,i)){
                  result[i] = aleatorio;
                  i++;
              }
          }
          return result;
      }

      /**
       * Um valor estah contido em parte de um array?
       * @param val - O valor a procurar
       * @param v - O array onde procurar
       * @param parte - O indice ateh onde procurar (exclusive)
       * @return true se val estah nas primeiras parte posicoes de v
       * @requires v != null && parte < v.length
       */
      static boolean contidoEmParte (int val, int[] v, int parte){

          for (int i = 0 ; i < parte ; i++){
              if (v[i] == val)
                  return true;
          }
          return false;
      }


      /**
       * Primeiro inteiro no canal de leitura que esta num dado intervalo
       * @param infLim   Limite inferior do intervalo
       * @param supLim  Limite superior do intervalo
       * @param errMess  Mensagem de  erro a apresentar no System.out
       * @param sc  Canal de leitura
       * @return um valor entre infLim e supLim
       * @requires sc != null && infLim <= supLim && errMess != null
       */
      public static int lerValorNoIntervalo(int infLim, int supLim, 
                                     String errMess, Scanner sc) {
          int valor = 0;
          boolean erro;
          do {
              valor = lerInteiro ( errMess, sc );
              erro = valor < infLim || valor > supLim;
              if ( erro ){
                  System.out.println ( errMess );
              }
          } while ( erro );

          return valor;
      }

      /**
       * Primeiro inteiro no canal de leitura
       * @param errMess - mensagem a escrever no System.out caso o valor 
       *                  acessivel no canal de leitura nao seja um inteiro
       * @param sc - canal de leitura
       * @return valor inteiro
       * @requires errMess != null && sc != null
       */
      public static int lerInteiro ( String errMess, Scanner sc ) {
          int valor = 0;
          boolean erro = true;
          do {
              if ( sc.hasNextInt () ) {
                  valor = sc.nextInt ();  // consome o inteiro
                  erro = false;
              } else {
                  sc.next ();      // consome o que lah esteja
                  System.out.println ( errMess );
              }				
          } while ( erro );

          return valor;
      }

}
