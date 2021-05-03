package oochess.main;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import oochess.app.OOChess;
import oochess.app.facade.Sessao;
import oochess.app.facade.dto.DesafioDTO;
import oochess.app.facade.exceptions.NoEligiblePlayersException;
import oochess.app.facade.exceptions.NoSuchMatchWithChallengeException;
import oochess.app.facade.exceptions.NoTornaumentAssociatedException;
import oochess.app.facade.exceptions.UserAlreadyExistsException;
import oochess.app.facade.handlers.DesafiarHandler;
import oochess.app.facade.handlers.ProcessarDesafiosHandler;
import oochess.app.facade.handlers.RegistarResultadoHandler;
import oochess.app.facade.handlers.RegistarUtilizadorHandler;

public class ClienteExemploTestes {
	
	private static String codigoDaPartida;
	private static String codigoDaPartida2;
	private static String codigoDaPartida3;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		OOChess p = new OOChess();
		
		RegistarUtilizadorHandler regHandler = p.getRegistarUtilizadorHandler();
		
		try {
			regHandler.registarUtilizador("Felismina", "hortadafcul", "fel1sgamer");
			System.out.println("Registo Felismina efetuado com sucesso");
		} catch (UserAlreadyExistsException e) {
			System.out.println("Já existe utilizador com esse username.");
		}
		try {
			regHandler.registarUtilizador("Silvino", "bardoc2", "s1lv1n0");
			System.out.println("Registo Silvino efetuado com sucesso");
		} catch (UserAlreadyExistsException e) {
			System.out.println("Já existe utilizador com esse username.");
		}
		try {
			regHandler.registarUtilizador("Maribel", "torredotombo", "SkubaPatr0l");
			System.out.println("Registo Maribel efetuado com sucesso");
		} catch (UserAlreadyExistsException e) {
			System.out.println("Já existe utilizador com esse username.");
		}
			
		System.out.println();
		System.out.println("Desafiar para partida");
		Optional<Sessao> talvezSessao = p.autenticar("Silvino", "bardoc2");
		talvezSessao.ifPresent( (Sessao s) -> {
			System.out.println("Desafio 1:");
			
			DesafiarHandler desh = s.getDesafioParaPartidaHandler();
			
			desh.indicaTorneio("Torneio Xadrez da CADI");
			
			try {
				List<String> jogadoresEElos = desh.indicaDeltaElo(50);
			} catch (NoEligiblePlayersException e) {
				System.out.println("Nao existem jogadores elegiveis!");
			}
			
			desh.indicaJogador("Maribel");
			codigoDaPartida = desh.indicaDetalhes(LocalDateTime.now().plusDays(1), "Amanhã vou finalmente derrotar-te!");
			
			System.out.println("Desafio 2:");
			DesafiarHandler desh2 = s.getDesafioParaPartidaHandler();
			
			desh2.indicaTorneio("Torneio Xadrez da CADI");
			
			try {
				List<String> jogadoresEElos2 = desh2.indicaDeltaElo(50);
			} catch (NoEligiblePlayersException e) {
				System.out.println("Nao existem jogadores elegiveis!");
			}
			
			desh2.indicaJogador("Maribel");
			codigoDaPartida2 = desh2.indicaDetalhes(LocalDateTime.now().plusDays(2), "Que vitória fácil!");
			
			System.out.println("Desafio 3:");
			DesafiarHandler desh3 = s.getDesafioParaPartidaHandler();
			
			desh3.indicaTorneio("Torneio Xadrez da CADI");
			
			try {
				List<String> jogadoresEElos3 = desh3.indicaDeltaElo(50);
			} catch (NoEligiblePlayersException e) {
				System.out.println("Nao existem jogadores elegiveis!");
			}
			
			desh3.indicaJogador("Maribel");
			codigoDaPartida3 = desh3.indicaDetalhes(LocalDateTime.now().plusDays(3), "Prepara-te!");
			
		});
		
		
		System.out.println();
		System.out.println("Registar resultado partidas espontaneas");
		talvezSessao.ifPresent( (Sessao s) -> {
			System.out.println("PE1-Derrota: ");
			RegistarResultadoHandler rh = s.getRegistarResultadoDePartida();
			rh.indicaPartidaEspontanea();
			rh.indicaDetalhes("Felismina", LocalDateTime.now());
			double novoEloDoSilvino = rh.indicarResultado("DERROTA");
			System.out.println("[NovoElo] Silvino: " + novoEloDoSilvino);
			
			System.out.println("PE2-Vitoria: ");
			RegistarResultadoHandler rh2 = s.getRegistarResultadoDePartida();
			rh2.indicaPartidaEspontanea();
			rh2.indicaDetalhes("Felismina", LocalDateTime.now());
			double novoEloDoSilvino2 = rh.indicarResultado("VITORIA");
			System.out.println("[NovoElo] Silvino: " + novoEloDoSilvino2);
			
			System.out.println("PE3-Vitoria: ");
			RegistarResultadoHandler rh3 = s.getRegistarResultadoDePartida();
			rh3.indicaPartidaEspontanea();
			rh3.indicaDetalhes("Felismina", LocalDateTime.now());
			double novoEloDoSilvino3 = rh3.indicarResultado("VITORIA");
			System.out.println("[NovoElo] Silvino: " + novoEloDoSilvino3);
			
		});
		
		System.out.println();
		System.out.println("Processar desafios pendentes");
		Optional<Sessao> talvezOutraSessao = p.autenticar("Maribel", "torredotombo");
		talvezOutraSessao.ifPresent( (Sessao s) -> {
			ProcessarDesafiosHandler pdh = s.getProcessarDesafios();
			boolean disponivel = true; 
			System.out.println("Codigo desafio / Estado");
			for (DesafioDTO d : pdh.consultarDesafiosPendentes()) {
				pdh.respondeADesafio(d.getCodigo(), disponivel);
				if (!disponivel) {
					try {
						pdh.indicaNovaData(LocalDateTime.now().plusDays(2));
					} catch (NoTornaumentAssociatedException e) {
						System.out.println("Este desafio não está associado a nenhum torneio.");
					}
				}				
				disponivel = !disponivel;
				System.out.println(d.getCodigo() + " / " + s.getUtilizador().getDesafioExistente(d.getCodigo()).getEstado());
			}
		});
		
		System.out.println();
		System.out.println("Registar resultado partidas amigaveis");
		talvezOutraSessao.ifPresent( (Sessao s) -> {
			System.out.println("P1-Vitoria(Desafio aceite):");
			RegistarResultadoHandler rh = s.getRegistarResultadoDePartida();
			try {
				rh.indicaDesafio(codigoDaPartida);
				double novoEloDaMaribel = rh.indicarResultado("VITORIA"); // Poderia ser também EMPATE
				System.out.println("[NovoElo] Maribel: " + novoEloDaMaribel);
			} catch (NoSuchMatchWithChallengeException e) {
				System.out.println("Não existe partida com desafio com esse código associado.");
			}
			
			System.out.println("P2-Vitoria(Desafio recusado):");
			RegistarResultadoHandler rh2 = s.getRegistarResultadoDePartida();
			try {
				rh2.indicaDesafio(codigoDaPartida2);
				double novoEloDaMaribel2 = rh2.indicarResultado("VITORIA"); // Poderia ser também EMPATE
				System.out.println("[NovoElo] Maribel: " + novoEloDaMaribel2);
			} catch (NoSuchMatchWithChallengeException e) {
				System.out.println("Não existe partida com desafio com esse código associado.");
			}
			
			System.out.println("P3-Vitoria(Desafio aceite):");
			RegistarResultadoHandler rh3 = s.getRegistarResultadoDePartida();
			try {
				rh3.indicaDesafio(codigoDaPartida3);
				double novoEloDaMaribel3 = rh3.indicarResultado("VITORIA"); // Poderia ser também EMPATE
				System.out.println("[NovoElo] Maribel: " + novoEloDaMaribel3);
			} catch (NoSuchMatchWithChallengeException e) {
				System.out.println("Não existe partida com desafio com esse código associado.");
			}
		});
		
		System.out.println();
		System.out.println("Desafiar após algumas partidas");
		talvezSessao.ifPresent((Sessao s)->{
			DesafiarHandler desh4 = s.getDesafioParaPartidaHandler();
			
			desh4.indicaTorneio("Torneio Xadrez da CADI");
			
			try {
				List<String> jogadoresEElos4 = desh4.indicaDeltaElo(1);
				System.out.println("Jogadores com elo dentro do deltaElo:");
				for(String h: jogadoresEElos4) {
					System.out.println(h);
				}
			} catch (NoEligiblePlayersException e) {
				System.out.println("Nao existem jogadores elegiveis!");
			}
			
			desh4.indicaJogador("Felismina");
			codigoDaPartida3 = desh4.indicaDetalhes(LocalDateTime.now().plusDays(3), "Prepara-te!");
		});
	}
}
		
