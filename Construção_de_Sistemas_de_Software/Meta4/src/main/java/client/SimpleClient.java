package client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import business.MockDateTime;
import facade.exceptions.AppException;
import facade.services.AtivarAulaService;
import facade.services.CriarAulaService;
import facade.services.InscreverAulaService;
import facade.services.VisualizarOcupacaoService;
import facade.startup.AulaGes;

/**
 * Cliente que interage com a aplicacao
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class SimpleClient {

	private SimpleClient() {}

	public static void main(String[] args) {
		AulaGes app = new AulaGes();
		try {
			app.run();

			List<String> dias35 = new ArrayList<>();
			dias35.add("terca");
			dias35.add("quinta");

			List<String> dias246 = new ArrayList<>();
			dias246.add("segunda");
			dias246.add("quarta");
			dias246.add("sexta");

			System.out.println("----------Cenario 1----------");
			casInteraction(app, "Pilates", "PLT001", dias35, LocalTime.of(9, 15), 55);
			System.out.println();

			System.out.println("----------Cenario 2----------"); 
			casInteraction(app, "Pilates", "PLT002", dias35, LocalTime.of(12, 15), 55);
			System.out.println();

			System.out.println("----------Cenario 3----------");
			casInteraction(app, "Pilates", "PLT003", dias246, LocalTime.of(12, 15), 55); 
			System.out.println(); 

			System.out.println("----------Cenario 4----------"); //tem de falhar por causa da duracao indicada
			casInteraction(app, "GAP", "GAP001", dias246, LocalTime.of(9, 0), 45);
			System.out.println();

			System.out.println("----------Cenario 5----------");  
			casInteraction(app, "GAP", "GAP001", dias246, LocalTime.of(9, 0), 50);
			System.out.println();

			System.out.println("----------Cenario 6----------"); 
			casInteraction(app, "STEP", "STP001", dias246, LocalTime.of(9, 15), 45); 
			System.out.println();

			System.out.println("----------Cenario 7----------");
			aasInteraction(app, "PLT001", "Estudio 1", MockDateTime.currentDate(), LocalDate.of(2020, 7, 31), 2);
			System.out.println(); 

			System.out.println("----------Cenario 8----------"); 
			aasInteraction(app, "PLT002", "Estudio 1", MockDateTime.currentDate(), LocalDate.of(2020, 7, 31), 2);
			System.out.println(); 

			System.out.println("----------Cenario 9----------"); 
			aasInteraction(app, "GAP001", "Estudio 1", MockDateTime.currentDate(), LocalDate.of(2020, 7, 31), 2);
			System.out.println(); 

			System.out.println("----------Cenario 10---------");//tem de falhar por causa da instalacao indicada
			aasInteraction(app, "STP001", "Estudio 2", MockDateTime.currentDate(), LocalDate.of(2020, 7, 31), 2);
			System.out.println();

			System.out.println("----------Cenario 11---------");//tem de falhar por causa da instalacao e periodo indicado
			aasInteraction(app, "STP001", "Estudio 1", MockDateTime.currentDate(), LocalDate.of(2020, 7, 31), 2);
			System.out.println();

			System.out.println("----------Cenario 12---------");
			iasInteraction(app, "Pilates", 2, "PLT001", 1);
			System.out.println();

			System.out.println("----------Cenario 13---------");
			iasInteraction(app, "Pilates", 2, "PLT002", 3);
			System.out.println();  

			System.out.println("----------Cenario 14---------"); 
			iasInteraction(app, "Pilates", 2, "PLT001", 2);
			System.out.println(); 

			System.out.println("----------Cenario 15---------");  
			iasInteraction(app, "Pilates", 1, "PLT002", 4);
			System.out.println();

			System.out.println("----------Cenario 16---------"); 
			iasInteraction(app, "Pilates", 1, null, 5); // Eh suposto nao existirem aulas disponiveis
			System.out.println();

			System.out.println("----------Cenario 17---------"); 
			iasInteraction(app, "Pilates", 2, "PLT002", 5);
			System.out.println();

			System.out.println("----------Cenario 18---------"); 
			vosInteraction(app,"Estudio 1", MockDateTime.currentDate().plusDays(1));
			System.out.println();

			app.stopRun();
		} catch (AppException e) {
			System.out.println("Error: " + e.getMessage());
			if (e.getCause() != null)
				System.out.println("Cause: ");
			e.printStackTrace();
		}
	}

	public static void casInteraction(AulaGes app, String modalidade, String nomeAula, List<String> dias, LocalTime horaInicio, int duracao) {
		try {
			CriarAulaService cas = app.getCriarAulaService();
			System.out.println("-----Modalidades existentes------");
			for(String m: cas.criarAula())
				System.out.println(m);
			System.out.println("---------------------------------");
			cas.criarAulaInfo(modalidade, nomeAula, dias, horaInicio, duracao);
			System.out.println("Aula " + nomeAula + " Criada");
		} catch (AppException e) {
			System.out.println("Error: " + e.getMessage());
			if (e.getCause() != null) {
				System.out.println("Cause: " + e.getCause().getMessage());
			}
		}
	}

	public static void aasInteraction(AulaGes app, String desAula, String instalacao, LocalDate data1, LocalDate data2, int nrMaxAlunos) {
		try {
			AtivarAulaService aas = app.getAtivarAulaService();
			System.out.println("-----Instalacoes existentes------");
			for(String i: aas.ativarAula())
				System.out.println(i);
			System.out.println("---------------------------------");
			aas.ativAulaInfo(desAula, instalacao, data1, data2, nrMaxAlunos);
			System.out.println("Aula " + desAula + " Ativada");
			System.out.println();
		} catch (AppException e) {
			System.out.println("Error: " + e.getMessage());
			if (e.getCause() != null) {
				System.out.println("Cause: " + e.getCause().getMessage());
			}
		}
	}

	public static void iasInteraction(AulaGes app, String modalidade, int tipoInscricao, String desAula, int nrUtente) {
		try {
			InscreverAulaService ias = app.getInscreverAulaService();
			System.out.println("-----Modalidades existentes------");
			for(String m: ias.inscreverAula())
				System.out.println(m);
			System.out.println("---------------------------------");

			List<String> aulas = ias.infoInscreverAula(modalidade, tipoInscricao);
			System.out.println("-------------Aulas ativas--------");
			for(String a: aulas)
				System.out.println(a);
			System.out.println("---------------------------------");
			if(desAula != null) {
				double custo = ias.escolheAula(desAula, nrUtente);
				if(tipoInscricao == 1) {
					System.out.println("Custo da inscricao avulsa na aula " + desAula + ": " + custo + " euros.");
				}else {
					System.out.println("Custo da inscricao regular na aula " + desAula + ": " + custo + " euros.");
				}
			}
			System.out.println();
		} catch (AppException e) {
			System.out.println("Error: " + e.getMessage());
			if (e.getCause() != null) {
				System.out.println("Cause: " + e.getCause().getMessage());
			}
		}
	}

	public static void vosInteraction(AulaGes app, String nomeInstalacao, LocalDate data) {
		try {
			VisualizarOcupacaoService vos = app.getVisualizarOcupacaoService();
			List<String> ocupacao = vos.visualizarOcupacao(nomeInstalacao, data);
			System.out.println("-----------Ocupacao desta instalacao-------------");
			for(String info : ocupacao)
				System.out.println(info);
			if(ocupacao.isEmpty())
				System.out.println("Esta instalacao nao esta a ser usada neste dia");
			System.out.println("--------------------------------------------------");
		} catch (AppException e) {
			System.out.println("Error: " + e.getMessage());
			if (e.getCause() != null) {
				System.out.println("Cause: " + e.getCause().getMessage());
			}
		}
	}
}
