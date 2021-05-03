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

public class ClienteExemplo {
	
	private static String codigoDaPartida;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		OOChess p = new OOChess();
		
		RegistarUtilizadorHandler regHandler = p.getRegistarUtilizadorHandler();
		
		try {
			regHandler.registarUtilizador("Felismina", "hortadafcul", "fel1sgamer");
		} catch (UserAlreadyExistsException e) {
			System.out.println("Já existe utilizador com esse username.");
		}
		try {
			regHandler.registarUtilizador("Silvino", "bardoc2", "s1lv1n0");
		} catch (UserAlreadyExistsException e) {
			System.out.println("Já existe utilizador com esse username.");
		}
		try {
			regHandler.registarUtilizador("Maribel", "torredotombo", "SkubaPatr0l");
		} catch (UserAlreadyExistsException e) {
			System.out.println("Já existe utilizador com esse username.");
		}
		
		
		
		// SSD - UC5
		Optional<Sessao> talvezSessao = p.autenticar("Silvino", "bardoc2");
		talvezSessao.ifPresent( (Sessao s) -> {
			
			DesafiarHandler desh = s.getDesafioParaPartidaHandler();
			
			desh.indicaTorneio("Torneio Xadrez da CADI");
			
			try {
				List<String> jogadoresEElos = desh.indicaDeltaElo(50);
				for(String jogadorElo: jogadoresEElos) {
					System.out.println(jogadorElo);
				}
			} catch (NoEligiblePlayersException e) {
				System.out.println("Nao existem jogadores elegiveis!");
			}
			
			desh.indicaJogador("Maribel");
			codigoDaPartida = desh.indicaDetalhes(LocalDateTime.now().plusDays(1), "Amanhã vou finalmente derrotar-te!");
			
		});
		
		// SSD - UC6

		talvezSessao.ifPresent( (Sessao s) -> {
			RegistarResultadoHandler rh = s.getRegistarResultadoDePartida();
			rh.indicaPartidaEspontanea();
			rh.indicaDetalhes("Felismina", LocalDateTime.now());
			double novoEloDoSilvino = rh.indicarResultado("DERROTA");
			System.out.println("[NovoElo] Silvino: " + novoEloDoSilvino);
			
		});
		
		// SSD - UC7
		Optional<Sessao> talvezOutraSessao = p.autenticar("Maribel", "torredotombo");
		talvezOutraSessao.ifPresent( (Sessao s) -> {
			ProcessarDesafiosHandler pdh = s.getProcessarDesafios();
			boolean disponivel = true; 
			for (DesafioDTO o : pdh.consultarDesafiosPendentes()) {
				pdh.respondeADesafio(o.getCodigo(), disponivel);
				if (!disponivel) {
					try {
						pdh.indicaNovaData(LocalDateTime.now().plusDays(2));
					} catch (NoTornaumentAssociatedException e) {
						System.out.println("Este desafio não está associado a nenhum torneio.");
					}
				}				
				disponivel = !disponivel;
			}
		});
		
		// SSD - UC6 (alt)
		
		talvezOutraSessao.ifPresent( (Sessao s) -> {
			RegistarResultadoHandler rh = s.getRegistarResultadoDePartida();
			try {
				rh.indicaDesafio(codigoDaPartida);
				double novoEloDaMaribel = rh.indicarResultado("VITORIA"); // Poderia ser também EMPATE
				System.out.println("[NovoElo] Maribel: " + novoEloDaMaribel);
			} catch (NoSuchMatchWithChallengeException e) {
				System.out.println("Não existe desafio com esse código associado.");
			}
	
		});
	}
}
