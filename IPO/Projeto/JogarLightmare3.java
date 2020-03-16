import java.util.Random;
import java.util.Scanner;

public class JogarLightmare3{  //inicio JogarLightmare3

/**
 *@author ip023 Antonio Frois 51050 Alexandre Monteiro 51023
 *
 * Joga o jogo Lightmare in Moniusland
 * @param iniMonius - as posicoes iniciais dos Monius
 * @param angulo - angulo de movimento do sabre de luz
 * @param alcanceLuz - alcance da luz dos sabres dos Monius
 * @param linhas - numero de linhas do caminho
 * @param colunas - numero de colunas do caminho
 * @param maxVezes - numero maximo de jogadas do jogo
 * @param gerador O gerador de valores aleatorios
 * @param leitor O objeto Scanner para ler dados do standard input
 * @requires iniMonius != null && forall i, iniMonius[i] >= 1 &&
 *           iniMonius[i] <= linhas * colunas &&
 *           angulo em {-90, 90, -180, 180, 360} && alcanceLuz >= 1 &&
 *           linhas > 0 && colunas > 0 && maxVezes > 0 &&
 *           gerador != null && leitor != null
 */
	public static void experimentaJogar3 (int[] iniMonius, int angulo, int alcanceLuz,
					      int linhas, int colunas, int maxVezes,
					      Random gerador, Scanner leitor) {  //inicio experimentaJogar3
		System.out.println("Como te chamas?");
		String nome = leitor.nextLine();
		System.out.println("############################################################");
		System.out.println("O angulo de ataque dos Monius serah sempre " + angulo + " graus durante o jogo");
		System.out.println("O alcance de luz dos sabres dos Monius serah sempre " + alcanceLuz + " durante o jogo");
		int posOutro = 1;
		boolean morto = false;
		boolean mesmaPos = false;
		boolean game_over = false;
		int MoniusVivos = iniMonius.length;
		

		for(int jog = 0; jog <= maxVezes && game_over != true; jog++){ //inicio do ciclo de jogada
			if(jog == 0) { //inicio da jogada 0
				boolean sk = false;
				for(int i = 0; i<iniMonius.length && sk == false ;i++){
				 if(iniMonius[i]==posOutro){
				 sk = true;
				 }
				}

			if(sk){
			 CaminhoMesmaPos(linhas,colunas,iniMonius,posOutro);
			 System.out.println();
			 System.out.println("Oooops, " + nome +"! Foste atingido ao fim de " + jog + " jogadas!");
			 System.out.println();
			 game_over = true;
			}else{
			 CaminhoDifPos(linhas,colunas,iniMonius,posOutro);
			 
			}

			}else{ // fim da jogada zero e inicio da jogada atual
			 int[] intervalo = interSaltoOutro(posOutro, linhas, colunas);
			 System.out.println(nome + ", quantas posicoes vais saltar?(Tem que ser inteiro entre "+intervalo[0]+" e "+intervalo[1]+")");
			 String msgErro = "Tem que ser inteiro entre "+intervalo[0]+" e "+intervalo[1];
			 int saltoOutro = FuncoesMonius3.lerValorNoIntervalo(intervalo[0], intervalo[1], msgErro, leitor);
			 int posAntOutro = posOutro;
			 posOutro = novaPosOutro(posOutro, saltoOutro);
			 System.out.println(nome + " salta para " + posOutro);
				for(int z = 0; z<iniMonius.length; z++){

    				 if(iniMonius[z] > 0 && iniMonius[z] <= (linhas * colunas)){ //inicio da verificacao se o Monius estah dentro do tabuleiro, ou seja se estah vivo
	
				  if((jog % 2 == 0 && iniMonius[z] % 2 == 0)  || (jog % 2 == 1 && iniMonius[z] % 2 == 1)){
				   int saltoMonius = SaltoMon(iniMonius[z],posAntOutro,colunas,gerador);
				   

				    if(novaPosMonius(iniMonius[z],saltoMonius) <= 0 || novaPosMonius(iniMonius[z],saltoMonius) > (linhas * colunas)){
				     System.out.println("Monius em: " + iniMonius[z] + " salta para fora do jogo!");
				     MoniusVivos--;
				    }else{
				     System.out.println("Monius em: " + iniMonius[z] + " salta para " + novaPosMonius(iniMonius[z],saltoMonius));
				       if(novaPosMonius(iniMonius[z],saltoMonius) == posOutro){
					 mesmaPos = true;
				       }
				    }
				   iniMonius[z] = novaPosMonius(iniMonius[z],saltoMonius);
				  }else{
				   System.out.println("Monius em: " + iniMonius[z] + " ataca!");
				       if(FuncoesMonius3.foiAtingido(iniMonius[z],posOutro,alcanceLuz,angulo,colunas,linhas)){
					 morto = true;
				       }
				   
				  }
			    	 } // fim dessa verificacao
				}	
			  if(posOutro == (linhas*colunas)){
				 if(mesmaPos){
				 CaminhoMesmaPos(linhas,colunas,iniMonius,posOutro);
				 System.out.println();
				 System.out.println("Oooops, " + nome +"! Foste atingido ao fim de " + jog + " jogada(s)!");
			         System.out.println();
			 	 game_over = true;
				 }else{if(morto){
				 CaminhoOutroMorto(linhas,colunas,iniMonius,posOutro);
				 System.out.println();
				 System.out.println("Oooops, " + nome +"! Foste atingido ao fim de " + jog + " jogada(s)!");
			       	 System.out.println();
			  	 game_over = true;
				}else{
				CaminhoDifPos(linhas,colunas,iniMonius,posOutro);
				System.out.println();
				System.out.println("Parabens, " + nome + "! Atravessaste o caminho ao fim de " + jog + " jogada(s)!");
				game_over = true;
				}
				}
			  }else{if(morto){
			  	 CaminhoOutroMorto(linhas,colunas,iniMonius,posOutro);
				 System.out.println();
				 System.out.println("Oooops, " + nome +"! Foste atingido ao fim de " + jog + " jogada(s)!");
			       	 System.out.println();
			  	 game_over = true;
			  }else{if(mesmaPos){
				 CaminhoMesmaPos(linhas,colunas,iniMonius,posOutro);
				 System.out.println();
				 System.out.println("Oooops, " + nome +"! Foste atingido ao fim de " + jog + " jogada(s)!");
			         System.out.println();
			 	 game_over = true;
			  }else{if(MoniusVivos == 0){
				 CaminhoDifPos(linhas,colunas,iniMonius,posOutro);
				 System.out.println();
				 System.out.println("Parabens, " + nome + "! Tens o caminho livre!");
				 System.out.println();
				 game_over = true;
			  }else{if(jog == maxVezes){
				 CaminhoDifPos(linhas,colunas,iniMonius,posOutro);
				 System.out.println();
				 System.out.println("Parabens, " + nome + "! Conseguiste escapar ao fim de " + jog + " jogada(s)!");
				 System.out.println();
				 game_over = true;
			  }else{
				 CaminhoDifPos(linhas,colunas,iniMonius,posOutro);
				 System.out.println();
		          }
			  }
			  }
			  }
			  }

			}//fim da jogada atual

		} //fim do ciclo de jogada


	} //fim experimentaJogar3
	/**Metodo para calcular o intervalo do salto do Outro
	*@param posOutro - posicao do Outro
	*@param linhas - numero de linhas do tabuleiro
	*@param colunas - numero de colunas do tabuleiro
	*@requires 0 <= colunas <= 60 && 0 <= linhas <= 60 && 0 <= posOutro <= (linhas*colunas)
	*@return valor do salto do Outro
	*/

	public static int[] interSaltoOutro(int posOutro,int linhas,int colunas){ //inicio interSaltoOutro
		int[] intervalo = new int[2];
		if(posOutro <= colunas){
			intervalo[0]=(1-posOutro);
			intervalo[1]=colunas;
		}else{if(posOutro > (colunas*(linhas-1)) ){
			intervalo[0]=-colunas;
			intervalo[1]=(colunas*linhas)-posOutro;
		}else{
			intervalo[0]=-colunas;
			intervalo[1]=colunas;
		}
		}
		return intervalo;	
	} // fim interSaltoOutro
	
	/**Metodo para calcular a nova posicao do Outro
	*@param posOutro - posicao do Outro
	*@param saltoOut - valor do salto do Outro
	*@requires 0 <= posOutro <= (linhas*colunas) && -colunas <= saltoOut <= colunas                
	*@return nova posiçao do Outro
	*/

	static int novaPosOutro(int posOut, int saltoOut){ //inicio novaPosOutro
		int npo = posOut + saltoOut;
		return npo;
	} //fim novaPosOutro


	/**Metodo para calcular o salto do Monius
	*@param posOutro - posicao do Outro
	*@param posMon - posicao do Monius
	*@param colunas - numero de colunas do tabuleiro
	*@param gerador - numero random
	*@requires 0 <= colunas <= 60 && 0 <= linhas <= 60 && 0 <= posOutro <= (linhas*colunas)
	*@return valor do salto do Monius
	*/

	static int SaltoMon(int posMon,int posOut,int colunas,Random gerador){ //inicio SaltoMon

	int salto = gerador.nextInt(colunas)+1;
	if (posOut < posMon){
	salto = salto * -1;
	}
	return salto;

	}// fim SaltoMon

	/**Metodo para calcular a nova posicao do Monius
	*@param posMon - posicao do Monius
	*@param saltoMon - valor do salto do Monius
	*@requires 0 < posMon <= (linhas*colunas) && 1 <= saltoMon <= colunas                   
	*@return nova posicao do Monius
	*/



	static int novaPosMonius(int posMon,int saltoMon){ //inicio novaPosMonius
	int npm = posMon + saltoMon;
	return npm;
	} //fim novaPosMonius
	
	/**Metodo para imprimir caminho com Monius e Outro 
	*  em posicoes diferentes
	*@param posMon - posicao(oes) do(s) Monius
	*@param posOut - posicao do Outro
	*@param c - numero de colunas do caminho
	*@param l - numero de linhas do caminho
	*@requires 0 < posMon <= (linhas*colunas) && 0 < posOut <= (linhas*colunas) 
	           && posMon!=null && l>0 && c>0
	*/

	static void CaminhoDifPos(int l,int c,int[] posMon,int posOut){//inicio do caminho de posições diferentes

	int lO = linha(posOut, c);
	int cO = coluna(posOut, c ,lO);

	int[] lM = new int[posMon.length];
	int[] cM = new int[posMon.length];
		for(int t = 0; t<posMon.length ; t++){
		lM[t] = linha(posMon[t],c);
		cM[t] = coluna(posMon[t],c,lM[t]);
		}




	for(int i=1;i<=l;i++){
	StringBuilder caminho = new StringBuilder();
	
		for(int k=1;k<=c;k++){
  
   		caminho.append("_ ");
   
   		if(cO==k && lO==i){ 
   		caminho.setCharAt((2*k-2),'&');
    		}
          
  		for(int a = 0;a<posMon.length;a++){
			if(cM[a]==k && lM[a]==i){
			caminho.setCharAt((2*k-2),'M');
			}
   		}


	}
	System.out.println(caminho.toString());
 
}

}//fim do caminho com posições diferentes

	/**Metodo para imprimir caminho com Outro morto
	*@param posMon - posicao(oes) do(s) Monius
	*@param posOut - posicao do Outro
	*@param c - numero de colunas do caminho
	*@param l - numero de linhas do caminho
	*@requires 0 < posMon <= (linhas*colunas) && 0 < posOut <= (linhas*colunas) 
	           && posMon!=null && l>0 && c>0
	*/

static void CaminhoOutroMorto(int l,int c,int[] posMon,int posOut){//inicio do caminho com o Outro morto

int lO = linha(posOut, c);
int cO = coluna(posOut, c ,lO);

int[] lM = new int[posMon.length];
int[] cM = new int[posMon.length];
	for(int t = 0; t<posMon.length ; t++){
	lM[t] = linha(posMon[t],c);
	cM[t] = coluna(posMon[t],c,lM[t]);
	}


	for(int i=1;i<=l;i++){
	StringBuilder caminho = new StringBuilder();
	
		for(int k=1;k<=c;k++){
  
   		caminho.append("_ ");
   
   		if(cO==k && lO==i){ 
   		caminho.setCharAt((2*k-2),'*');
    		}
          
  		for(int a = 0;a<posMon.length;a++){
			if(cM[a]==k && lM[a]==i){
			caminho.setCharAt((2*k-2),'M');
			}
   		}


	}
	System.out.println(caminho.toString());
 
}

}//fim do caminho com o Outro morto

	/**Metodo para imprimir caminho com Outro e Monius
	   na mesma posicao
	*@param posMon - posicao(oes) do(s) Monius
	*@param posOut - posicao do Outro
	*@param c - numero de colunas do caminho                          			
	*@param l - numero de linhas do caminho                  		
	*@requires 0 < posMon <= (linhas*colunas) && 0 < posOut <= (linhas*colunas) 
	           && posMon!=null && l>0 && c>0
	*/

static void CaminhoMesmaPos(int l,int c,int[] posMon,int posOut){//inicio do caminho de posições iguais

int lO = linha(posOut, c);
int cO = coluna(posOut, c ,lO);

int[] lM = new int[posMon.length];
int[] cM = new int[posMon.length];
	for(int t = 0; t<posMon.length ; t++){
	lM[t] = linha(posMon[t],c);
	cM[t] = coluna(posMon[t],c,lM[t]);
	}


	for(int i=1;i<=l;i++){
	StringBuilder caminho = new StringBuilder();
	
		for(int k=1;k<=c;k++){
  
   		caminho.append("_ ");
   
          
  		for(int a = 0;a<posMon.length;a++){
			if(cM[a]==k && lM[a]==i){
			caminho.setCharAt((2*k-2),'M');
			if(posMon[a] == posOut){
			caminho.setCharAt((2*k-2),'@');
			}

			}
			}

	}
	System.out.println(caminho.toString());
 
	}

}//fim do caminho com posições iguais



/**Metodo para calcular a linha
	*@param pos - posicao atual 
	*@param comprim - numero de colunas
	*@requires 0 < pos <= (linhas*colunas) && comprim > 0
	*@return numero da linha
	*/

public static int linha(int pos, int comprim) {
            int linhas = pos / comprim;
            if (pos % comprim > 0){
                  linhas++;
            }
            return linhas;
	}

/**Metodo para calcular a coluna
	*@param pos - posicao atual 
	*@param comprim - numero de colunas
	*@param linha - linha atual				      
	*@requires 0 < pos <= (linhas*colunas) && comprim > 0 && linha > 0       
	*@return numero da linha
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

} //fim JogarLightmare3