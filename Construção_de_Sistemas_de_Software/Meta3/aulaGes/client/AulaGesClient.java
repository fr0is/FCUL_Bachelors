package client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import facade.exceptions.AppException;
import facade.services.AtivarAulaService;
import facade.services.CriarAulaService;
import facade.services.InscreverAulaService;
import facade.services.VisualizarOcupacaoService;
import facade.startup.AulaGes;

/**
 * Cliente que interage com os handlers da aplicacao
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class AulaGesClient {

	private CriarAulaService cas;
	private AtivarAulaService aas;
	private InscreverAulaService ias;
	private VisualizarOcupacaoService vos;

	public AulaGesClient(CriarAulaService cas, AtivarAulaService aas, InscreverAulaService ias,
			VisualizarOcupacaoService vos) {
		this.cas=cas;
		this.aas=aas;
		this.ias=ias;
		this.vos=vos;
	}


	public void createInteraction() {
		try {
			System.out.println(">>>>>>>CRIAR UMAS AULAS<<<<<<<");
			casInteraction();

		} catch (AppException e) {
			System.out.println("Erro: " + e.getMessage());
		}	

		try {
			System.out.println(">>>>>>>ATIVAR AS AULAS<<<<<<<");
			aasInteraction();

		} catch (AppException e) {
			System.out.println("Erro: " + e.getMessage());
		}	

		try {
			System.out.println(">>>>>>>INSCREVER UTENTES<<<<<<<");
			iasInteraction1();

		} catch (AppException e) {
			System.out.println("Erro: " + e.getMessage());
		}	
		try {
			System.out.println("-------------------------------------");
			iasInteraction2();

		} catch (AppException e) {
			System.out.println("Erro: " + e.getMessage());
		}	
		try {
			System.out.println("-------------------------------------");
			iasInteraction3();

		} catch (AppException e) {
			System.out.println("Erro: " + e.getMessage());
		}	
		try {
			System.out.println("-------------------------------------");
			System.out.println(">>>>>>>OCUPACAO DE UMA INSTALACAO<<<<<<<");
			vosInteraction();
		} catch (AppException e) {
			System.out.println("Erro: " + e.getMessage());
		}	
	}

	/**
	 * Interacao com o CriarAulaHandler
	 * @throws AppException
	 */
	public void casInteraction() throws AppException{
		System.out.println("-----Modalidades existentes------");
		for(String m: cas.criarAula())
			System.out.println(m);
		System.out.println("---------------------------------");
		List<String> dias = new ArrayList<>();
		dias.add("segunda");
		dias.add("terca");
		dias.add("quarta");
		dias.add("quinta");
		dias.add("sexta");
		dias.add("sabado");
		dias.add("domingo");
		cas.criarAulaInfo("Natacao", "sw1m00", dias, "9:00", 45);
		System.out.println("Aula sw1m00 Criada");		

		List<String> dias2 = new ArrayList<>();
		dias2.add("segunda");
		dias2.add("quarta");
		dias2.add("sexta");
		cas.criarAulaInfo("Tenis", "t3n1s0", dias2, "11:00", 45);
		System.out.println("Aula t3n1s0 Criada");

		cas.criarAulaInfo("Natacao", "sw1m02", dias, "7:00", 45);
		System.out.println("Aula sw1m02 Criada");		
		System.out.println();
	}

	/**
	 * Interacao com o AtivarAulaHandler
	 * @throws AppException
	 */
	public void aasInteraction() throws AppException{
		System.out.println("-----Instalacoes existentes------");
		for(String i: aas.ativarAula())
			System.out.println(i);
		System.out.println("---------------------------------");
		aas.ativAulaInfo("sw1m00", "piscina", this.convertToString(LocalDate.now()), this.convertToString(LocalDate.now().plusDays(10)), 40);
		System.out.println("Aula sw1m00 Ativada");
		aas.ativAulaInfo("sw1m02", "piscina", this.convertToString(LocalDate.now()), this.convertToString(LocalDate.now().plusDays(10)), 40);
		System.out.println("Aula sw1m02 Ativada");
		aas.ativAulaInfo("t3n1s0", "CampoDeTenis", "2020-06-07", "2020-08-07", 20);
		System.out.println("Aula t3n1s0 Ativada");		
		System.out.println();		
	}

	/**
	 * Interacao com o InscreverAulaHandler
	 * @throws AppException
	 */
	public void iasInteraction1() throws AppException{
		System.out.println("-----Modalidades existentes------");
		for(String m: ias.inscreverAula())
			System.out.println(m);
		System.out.println("---------------------------------");

		System.out.println("-------------Aulas ativas--------");
		List<String> aulas = ias.infoInscreverAula("Natacao", 2);
		if(!aulas.isEmpty()) {
			for(String a: aulas)
				System.out.println(a);
		}else {
			System.out.println("Nao existem aulas ativas para esta modalidade");
		}
		System.out.println("---------------------------------");
		double custo = ias.escolheAula("sw1m00", 1);
		System.out.println("Custo da inscricao regular na aula sw1m00: " + custo + " euros.");
		System.out.println();
	}

	/**
	 * Interacao com o InscreverAulaHandler
	 * @throws AppException
	 */
	private void iasInteraction2() throws AppException {
		System.out.println("-----Modalidades existentes------");
		for(String m: ias.inscreverAula())
			System.out.println(m);
		System.out.println("---------------------------------");

		System.out.println("-------------Aulas ativas--------");
		List<String> aulas = ias.infoInscreverAula("Natacao", 1);
		if(!aulas.isEmpty()) {
			for(String a: aulas)
				System.out.println(a);
		}else {
			System.out.println("Nao existem aulas ativas para esta modalidade");
		}
		System.out.println("---------------------------------");
		double custo = ias.escolheAula("sw1m00", 2);
		System.out.println("Custo da inscricao avulsa na aula sw1m00: " + custo + " euros.");
		System.out.println();
	}

	/**
	 * Interacao com o InscreverAulaHandler
	 * @throws AppException
	 */
	private void iasInteraction3() throws AppException {
		System.out.println("-----Modalidades existentes------");
		for(String m: ias.inscreverAula())
			System.out.println(m);
		System.out.println("---------------------------------");

		System.out.println("-------------Aulas ativas--------");
		List<String> aulas = ias.infoInscreverAula("Natacao", 2);
		if(!aulas.isEmpty()) {
			for(String a: aulas)
				System.out.println(a);
		}else {
			System.out.println("Nao existem aulas ativas para esta modalidade");
		}
		System.out.println("---------------------------------");
		double custo = ias.escolheAula("sw1m00", 3);
		System.out.println("Custo da inscricao regular na aula sw1m00: " + custo + " euros.");
		System.out.println();
	}

	/**
	 * Interacao com o VisualizarOcupacaoHandler
	 * @throws AppException
	 */
	public void vosInteraction() throws AppException{
		List<String> ocupacao = vos.visualizarOcupacao("CampoDeTenis", "2020-06-10");
		System.out.println("-----------Ocupacao desta instalacao-------------");
		for(String info : ocupacao)
			System.out.println(info);
		if(ocupacao.isEmpty())
			System.out.println("Esta instalacao nao esta a ser usada neste dia");
		System.out.println("--------------------------------------------------");
	}

	/**
	 * Converte o data de LocalDate para o formato String yyyy-MM-dd
	 * @param data - data a ser convertida para String
	 * @return data em String no formato yyyy-MM-dd
	 */
	private String convertToString(LocalDate data) {
		int ano = data.getYear();
		int mes = data.getMonthValue();
		int dia = data.getDayOfMonth();
		return ano + "-" + mes + "-" + dia;
	}

	public static void main(String[] args) {
		AulaGes app = new AulaGes();

		CriarAulaService cas = new CriarAulaService(app.getCriarAulaHandler());
		AtivarAulaService aas = new AtivarAulaService(app.getAtivarAulaHandler());
		InscreverAulaService ias = new InscreverAulaService(app.getInscreverAulaHandler());
		VisualizarOcupacaoService vos = new VisualizarOcupacaoService(app.getVisualizarOcupacaoHandler());

		AulaGesClient client = new AulaGesClient(cas,aas,ias,vos);
		client.createInteraction();
	}
}
