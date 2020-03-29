package pleilist.main;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import pleilist.app.PleiList;
import pleilist.app.facade.Sessao;
import pleilist.app.facade.dto.Entrada;
import pleilist.app.facade.dto.Pair;
import pleilist.app.facade.dto.Playlist;
import pleilist.app.facade.exceptions.NoSuchAddressException;
import pleilist.app.facade.exceptions.NoSuchVideoCodeException;
import pleilist.app.facade.exceptions.NoSuchVideoWithHashtagException;
import pleilist.app.facade.handlers.AdicionarVideoHandler;
import pleilist.app.facade.handlers.CriarPlayListHandler;
import pleilist.app.facade.handlers.RegistarUtilizadorHandler;
import pleilist.app.facade.handlers.VerPlaylistHandler;

public class ClienteExemplo {
	public static void main(String[] args) {
		PleiList p = new PleiList();
		
		RegistarUtilizadorHandler regHandler = p.getRegistarUtilizadorHandler();
		
		regHandler.registarUtilizador("Felismina", "hortadafcul");
		regHandler.registarCurador("Silvino", "bardoc2");
		regHandler.registarCurador("Maribel", "torredotombo");
		
		Optional<Sessao> talvezSessao = p.autenticar("Silvino", "bardoc2");
		
		talvezSessao.ifPresent( (Sessao s) -> {
			
			AdicionarVideoHandler avh = p.getAdicionarVideoHandler(s);
			avh.iniciarAdicionar();
			avh.definirComoClip(true); // Vamos dizer que é um clip.
			try {
				avh.indicaVideo("OOP in 7 Minutes", "endereco errado");
			} catch (NoSuchAddressException e) {
				try {
					avh.indicaVideo("OOP in 7 Minutes", "https://www.youtube.com/watch?v=pTB0EiLXUC8");//indica endereco certo
				} catch (NoSuchAddressException e1) {
					System.out.println("O endereco indicado nao foi encontrado.");
				}
			}
			avh.indicaDuracao(Duration.ofSeconds(7 * 60 + 33));
			
			for (String tag : "oop objects polymorphism".split(" ")) {
				avh.indicaTag(tag);
			}
			
			String codigo = avh.defineComoPublico(false);
			System.out.println("O Silvino criou o vídeo " + codigo);
		});
		
		Optional<Sessao> talvezOutraSessao = p.autenticar("Maribel", "torredotombo");
		talvezOutraSessao.ifPresent( (Sessao s) -> {
			
			AdicionarVideoHandler avh = p.getAdicionarVideoHandler(s);
			avh.iniciarAdicionar();
			avh.definirComoClip(false); // Vamos dizer que é um stream.
			try {
				avh.indicaVideo("RTP1", "https://www.rtp.pt/play/direto/rtp1");
			} catch (NoSuchAddressException e) {
				System.out.println("O endereco indicado nao foi encontrado.");
			}
			// Não indica duração! É um Stream!
			
			for (String tag : "portugues actualidade noticias praca_da_alegria".split(" ")) {
				avh.indicaTag(tag);
			}
			
			String codigo = avh.defineComoPublico(true);
			System.out.println("A Maribel criou o vídeo " + codigo);
		});
		
		talvezSessao.ifPresent( (Sessao s) -> {
			
			CriarPlayListHandler cph = p.getCriarPlayListHandler(s);
			cph.iniciaCriacao("Playlist de Domingo");
			
			for (String tag : "oop portugues".split(" ")) {
				List<Entrada> entradasActuaisDaPlaylist = cph.obterEntradasActuais();
				System.out.println("..........");
				entradasActuaisDaPlaylist.stream().forEach(System.out::println);
				System.out.println("..........");
				
				List<Pair<String, String>> videos;
				try {
					videos = cph.obterVideosComHashtag(tag);
				} catch (NoSuchVideoWithHashtagException e2) {
					videos = cph.pretendeVerTop10("S");
				}
				
				boolean eStream = false;
				try {
					eStream = cph.indicarCodigo("codigo errado");
				} catch (NoSuchVideoCodeException e) {
					try {
						eStream = cph.indicarCodigo(videos.get(0).getSecond());//codigo correto
					} catch (NoSuchVideoCodeException e1) {
						System.out.println("Nao foi encontrado nenhum video na minha biblioteca"
								+ " pessoal ou na biblioteca geral.");
					}
				}
				
				if (eStream) {
					cph.indicaDuracao(Duration.ofMinutes(30));
				}
			}
			cph.terminar();
			
		});
		
		Optional<Sessao> talvezSessaoUtilizador = p.autenticar("Felismina", "hortadafcul");
		
		talvezSessaoUtilizador.ifPresent( (Sessao s) -> {
			
			VerPlaylistHandler vph = p.getVerPlaylistHandler(s);
			
			List<Playlist> resultados = vph.procurarPorTag("oop");
			
			Duration duracaoTotal = vph.escolhePlaylist(resultados.get(0).getCodigo());
			
			Pair<String, Duration> video = vph.verProximo();
			
			vph.classificar(3);
			
	
		});
		
	}
}
