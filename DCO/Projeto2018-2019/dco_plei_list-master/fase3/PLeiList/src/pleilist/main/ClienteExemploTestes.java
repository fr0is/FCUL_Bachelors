package pleilist.main;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import pleilist.app.PleiList;
import pleilist.app.domain.Clip;
import pleilist.app.domain.Curador;
import pleilist.app.domain.Hashtag;
import pleilist.app.domain.PlaylistDomain;
import pleilist.app.domain.Video;
import pleilist.app.domain.catalogos.BibliotecaGeral;
import pleilist.app.domain.catalogos.CatalogoPlaylists;
import pleilist.app.facade.Sessao;
import pleilist.app.facade.dto.Entrada;
import pleilist.app.facade.dto.Pair;
import pleilist.app.facade.dto.Playlist;
import pleilist.app.facade.exceptions.NoSuchAddressException;
import pleilist.app.facade.exceptions.NoSuchVideoCodeException;
import pleilist.app.facade.exceptions.NoSuchVideoWithHashtagException;
import pleilist.app.facade.handlers.AdicionarVideoHandler;
import pleilist.app.facade.handlers.CriarPlayListHandler;
import pleilist.app.facade.handlers.CriarPlaylistAutoHandler;
import pleilist.app.facade.handlers.RegistarUtilizadorHandler;
import pleilist.app.facade.handlers.VerPlaylistHandler;

public class ClienteExemploTestes {
	public static void main(String[] args) {
		PleiList p = new PleiList();

		/////////////////////////////Catalogos/////////////////////////////////
		BibliotecaGeral bibGeral = BibliotecaGeral.getInstance();
		CatalogoPlaylists catPlaylists = CatalogoPlaylists.getInstance();
		//////////////////////////////////////////////////////////////////////////////

		RegistarUtilizadorHandler regHandler = p.getRegistarUtilizadorHandler();

		regHandler.registarUtilizador("Felismina", "hortadafcul");
		regHandler.registarUtilizador("Paulo", "cajax234");
		regHandler.registarCurador("Silvino", "bardoc2");
		regHandler.registarCurador("Maribel", "torredotombo");



		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////SILVINO///////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Optional<Sessao> talvezSessao = p.autenticar("Silvino", "bardoc2");

		talvezSessao.ifPresent( (Sessao s) -> {

			System.out.println("---------------\n----SILVINO----\n---------------");
			/////////////////////////VID_0////////////////////////////
			AdicionarVideoHandler avh = p.getAdicionarVideoHandler(s);
			avh.iniciarAdicionar();
			avh.definirComoClip(true); // Vamos dizer que é um clip.
			
			try {
				avh.indicaVideo("Video_0_clip_privado_7M33S", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_0_https://www.youtube.com/watch?v=pTB0EiLXUC8");
			
			avh.indicaDuracao(Duration.ofSeconds(7 * 60 + 33));

			for (String tag : "oop objects polymorphism".split(" ")) {
				avh.indicaTag(tag);
			}

			String codigo = avh.defineComoPublico(false);


			System.out.println("O Silvino criou o vídeo " + codigo);
			Curador cur = (Curador) s.getUtilizador();//cast nao pode falhar, Silvino eh curador
			Video vid = cur.getVideo(codigo).get();
			imprimeVideoInfo(vid);
			//////////////////////////////////////////////////////////

			/////////////////////////VID_1////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(false);
			
			try {
				avh.indicaVideo("Video_1_stream_publico", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_1_https://www.youtube.com/watch?v=pTB0EiLXUC8");
			
			//STREAM NAO TEM DURACAO

			for (String tag : "oop objects polymorphism hashtagChained".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("O Silvino criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			//////////////////////////////////////////////////////////

			/////////////////////////VID_2////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true); // Vamos dizer que é um clip.
			
			try {
				avh.indicaVideo("Video_2_clip_publico_10M", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_2_https://www.youtube.com/watch?v=pTB0EiLXUC8");
			
			avh.indicaDuracao(Duration.ofSeconds(10 * 60));

			for (String tag : "parafusos porcas hashtagChained".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("O Silvino criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////

			/////////////////////////VID_3////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true); // Vamos dizer que é um clip.
			
			try {
				avh.indicaVideo("Video_3_clip_privado_10M", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_3_https://www.youtube.com/watch?v=pTB0EiLXUC8");
			
			avh.indicaDuracao(Duration.ofSeconds(10 * 60));

			for (String tag : "sandalias".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(false);


			System.out.println("O Silvino criou o vídeo " + codigo);
			cur = (Curador) s.getUtilizador();//cast nao pode falhar, Silvino eh curador
			vid = cur.getVideo(codigo).get();
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////


		});
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////MARIBEL/////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Optional<Sessao> talvezOutraSessao = p.autenticar("Maribel", "torredotombo");
		talvezOutraSessao.ifPresent( (Sessao s) -> {

			System.out.println("---------------\n----MARIBEL----\n---------------");
			/////////////////////////VID_4////////////////////////////
			AdicionarVideoHandler avh = p.getAdicionarVideoHandler(s);
			avh.iniciarAdicionar();
			avh.definirComoClip(false); // Vamos dizer que é um stream.
			
			try {
				avh.indicaVideo("Video_4_stream_publico", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_4_https://www.rtp.pt/play/direto/rtp1");
			
			// Não indica duração! É um Stream!

			for (String tag : "portugues actualidade noticias praca_da_alegria hashtagChained".split(" ")) {
				avh.indicaTag(tag);
			}

			String codigo = avh.defineComoPublico(true);


			System.out.println("A Maribel criou o vídeo " + codigo);
			Video vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////

			/////////////////////////VID_5////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true);
			
			try {
				avh.indicaVideo("Video_5_clip_publico_20M", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_5_https://www.rtp.pt/play/direto/rtp1");
			
			avh.indicaDuracao(Duration.ofSeconds(20 * 60));

			for (String tag : "alergias doencas".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("A Maribel criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////

			/////////////////////////VID_6////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true);
			
			try {
				avh.indicaVideo("Video_6_clip_privado_20M", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_6_https://www.rtp.pt/play/direto/rtp1");
			
			avh.indicaDuracao(Duration.ofSeconds(20 * 60));

			for (String tag : "borboletas passaros girafas animais".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(false);


			System.out.println("A Maribel criou o vídeo " + codigo);
			Curador cur = (Curador) s.getUtilizador();//cast nao pode falhar, Maribel eh curador
			vid = cur.getVideo(codigo).get();
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////


			// -> -> -> os videos criados daqui para baixo serao os que vao estar no top 10 porque eu no fim vou lhes incrementar muito as visualizacoes

			/////////////////////////VID_7////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true);
			
			try {
				avh.indicaVideo("Video_7_clip_publico_20M___TOP10", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_7_https://www.rtp.pt/play/direto/rtp1");
			
			avh.indicaDuracao(Duration.ofSeconds(20 * 60));

			for (String tag : "malas".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("A Maribel criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////

			/////////////////////////VID_8////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true);
			
			try {
				avh.indicaVideo("Video_8_clip_publico_20M___TOP10", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_8_https://www.rtp.pt/play/direto/rtp1");
			
			avh.indicaDuracao(Duration.ofSeconds(20 * 60));

			for (String tag : "malas".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("A Maribel criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////

			/////////////////////////VID_9////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true);
			
			try {
				avh.indicaVideo("Video_9_clip_publico_20M___TOP10", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_9_https://www.rtp.pt/play/direto/rtp1");
			
			avh.indicaDuracao(Duration.ofSeconds(20 * 60));

			for (String tag : "malas".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("A Maribel criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////

			/////////////////////////VID_10////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true);
			
			try {
				avh.indicaVideo("Video_10_clip_publico_20M___TOP10", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_10_https://www.rtp.pt/play/direto/rtp1");
			
			avh.indicaDuracao(Duration.ofSeconds(20 * 60));

			for (String tag : "malas".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("A Maribel criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////

			/////////////////////////VID_11////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true);
			
			try {
				avh.indicaVideo("Video_11_clip_publico_20M___TOP10", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_11_https://www.rtp.pt/play/direto/rtp1");
			
			avh.indicaDuracao(Duration.ofSeconds(20 * 60));

			for (String tag : "malas".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("A Maribel criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////

			/////////////////////////VID_12////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true);
			
			try {
				avh.indicaVideo("Video_12_clip_publico_20M___TOP10", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_12_https://www.rtp.pt/play/direto/rtp1");
			
			avh.indicaDuracao(Duration.ofSeconds(20 * 60));

			for (String tag : "malas".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("A Maribel criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////

			/////////////////////////VID_13////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true);
			
			try {
				avh.indicaVideo("Video_13_clip_publico_20M___TOP10", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_13_https://www.rtp.pt/play/direto/rtp1");
			
			avh.indicaDuracao(Duration.ofSeconds(20 * 60));

			for (String tag : "malas".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("A Maribel criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////

			/////////////////////////VID_14////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true);
			
			try {
				avh.indicaVideo("Video_14_clip_publico_20M___TOP10", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_14_https://www.rtp.pt/play/direto/rtp1");
			
			avh.indicaDuracao(Duration.ofSeconds(20 * 60));

			for (String tag : "malas".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("A Maribel criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////

			/////////////////////////VID_15////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true);
			
			try {
				avh.indicaVideo("Video_15_clip_publico_20M___TOP10", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_15_https://www.rtp.pt/play/direto/rtp1");
			
			avh.indicaDuracao(Duration.ofSeconds(20 * 60));

			for (String tag : "malas".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("A Maribel criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////

			/////////////////////////VID_16////////////////////////////
			avh.iniciarAdicionar();
			avh.definirComoClip(true);
			
			try {
				avh.indicaVideo("Video_16_clip_publico_20M___TOP10", "https://text-compare.com/");
			} catch (NoSuchAddressException e) {
				e.printStackTrace();
			}//vid_16_https://www.rtp.pt/play/direto/rtp1");
			
			avh.indicaDuracao(Duration.ofSeconds(20 * 60));

			for (String tag : "malas".split(" ")) {
				avh.indicaTag(tag);
			}

			codigo = avh.defineComoPublico(true);


			System.out.println("A Maribel criou o vídeo " + codigo);
			vid = bibGeral.getVideo(codigo).get();//get() nao pode falhar, video eh publico por isso tem de estar na bibGeral
			imprimeVideoInfo(vid);
			/////////////////////////////////////////////////////////


		});
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




		////////////////////////CRIAR PLAYLIST/////////////////////////
		talvezSessao.ifPresent( (Sessao s) -> {

			System.out.println("\n\n\n-----------------------------\n-----CRIACAO DE PLAYLIST-----\n-----------------------------");
			CriarPlayListHandler cph = p.getCriarPlayListHandler(s);
			cph.iniciaCriacao("Playlist de Domingo");

			for (String tag : "oop portugues HASHTAG_INEXISTENTE".split(" ")) {//com a HASHTAG_INEXISTENTE o sistema deve perguntar ao utilizador se ele pretende ver o top10
				System.out.println("Estamos a pesquisar videos com a hashtag \"" + tag + "\" na biblioteca geral e na biblioteca pessoal de " + s.getUtilizador().getUsername());

				List<Entrada> entradasActuaisDaPlaylist = cph.obterEntradasActuais();

				System.out.println("..........Constituicao da Playlist (inicio da iteracao)..........");
				entradasActuaisDaPlaylist.stream().forEach(System.out::println);
				System.out.println(".................................................................");

				if(tag.equals("HASHTAG_INEXISTENTE")) {
					System.out.println("O sistema vai perguntar se pretende ver top10!");
				}
				List<Pair<String, String>> videos;
				try {
					videos = cph.obterVideosComHashtag(tag);
				} catch (NoSuchVideoWithHashtagException e1) {
					videos = cph.pretendeVerTop10("S");
				}
				if(tag.equals("HASHTAG_INEXISTENTE")) {
					System.out.println("----------Lista TOP10:----------");
					for(Pair<String, String> par: videos) {
						System.out.println("    " + par.getSecond());
					}
					System.out.println("--------------------------------");
					System.out.println("views de todos os videos da bibGeral");
					Object[] allVids= bibGeral.getAllVideos().stream().toArray();
					for(Object obj: allVids) {
						Video v = (Video) obj;
						System.out.println(v.getCodigo() + " - >" + v.getVisualizacoes());
					}
				}


				List<String> codigos = new ArrayList<>();
				for(Pair<String, String> par: videos) {
					codigos.add(par.getSecond());//preencher codigos os codigos de todos os videos encontrados
				}
				for(String cod: codigos) {//neste for os videos sao adicionados ah playlist
					boolean eStream = false;;
					try {
						eStream = cph.indicarCodigo(cod);
					} catch (NoSuchVideoCodeException e) {
						e.printStackTrace();
					}

					if (eStream) {
						cph.indicaDuracao(Duration.ofMinutes(30));
					}
				}

				entradasActuaisDaPlaylist = cph.obterEntradasActuais();
				System.out.println("...........Constituicao da Playlist (fim da iteracao)...........");
				entradasActuaisDaPlaylist.stream().forEach(System.out::println);
				System.out.println("................................................................\n\n");
			}

			cph.terminar();

		});
		///////////////////////////////////////////////////////////////





		Optional<Sessao> talvezSessaoUtilizador = p.autenticar("Felismina", "hortadafcul");

		System.out.println("\n\n\n----------------------------------\n-----Visualizacao DE PLAYLIST-----\n----------------------------------");

		talvezSessaoUtilizador.ifPresent( (Sessao s) -> {
			System.out.println(s.getUtilizador().getUsername() + " vai visualizar a playlist");
			VerPlaylistHandler vph = p.getVerPlaylistHandler(s);

			System.out.println("\n\nPlaylist ANTES de ser visualizada:");
			List<Playlist> resultados = vph.procurarPorTag("oop");
			for(Playlist pl: resultados) {
				System.out.println(pl.getCodigo() + " pontuacao: " + pl.getPontuacao() + "\n--------------------------------------------");
			}

			Duration duracaoTotal = vph.escolhePlaylist(resultados.get(0).getCodigo());//escolhe a primeira playlist da lista de playlists com o hashtag pesquisado

			Pair<String, Duration> video = vph.verProximo();//soh ve o primeiro video da playlist

			vph.classificar(3);//soh classifica o primeiro video da playlist

			System.out.println("\n\nPlaylist DEPOIS de ser visualizada:");
			resultados = vph.procurarPorTag("oop");//para que sejam impressas as playlists outra vez
			for(Playlist pl: resultados) {
				System.out.println(pl.getCodigo() + " pontuacao: " + pl.getPontuacao() + "\n--------------------------------------------");
			}
		});


		////////////////////////////Paulo vai visualizar a mesma playlist que Felismina///////////////////////////////////////////////////////////////////
		Optional<Sessao> talvezSessaoUtilizadorPaulo = p.autenticar("Paulo", "cajax234");

		System.out.println("\n\n\n--------------------------------------\n---------Visualizacao DE PLAYLIST (Paulo)---------\n--------------------------------------");

		talvezSessaoUtilizadorPaulo.ifPresent( (Sessao s) -> {
			System.out.println(s.getUtilizador().getUsername() + " vai visualizar a playlist");
			VerPlaylistHandler vph = p.getVerPlaylistHandler(s);

			System.out.println("\n\nPlaylist ANTES de ser visualizada:");
			List<Playlist> resultados = vph.procurarPorTag("oop");

			for(Playlist play: resultados) {
				System.out.println(play.getCodigo() + " pontuacao: " + play.getPontuacao() + "\n--------------------------------------------");
			}

			String codigo = resultados.get(0).getCodigo();

			Duration duracaoTotal = vph.escolhePlaylist(codigo);//escolhe a primeira playlist da lista de playlists com o hashtag pesquisado

			PlaylistDomain pl = catPlaylists.getPlaylist(codigo).get();//get() nao pode falhar porque estah a ser passado um codigo que estah presente no catalogo de playlists

			int tamanhoPl = pl.getItens().size();

			for(int i = 0; i <= tamanhoPl; i++) { //<= para ver se o metodo verProximo diz que nao existem mais videos
				Pair<String, Duration> video = vph.verProximo();
				if(!video.getSecond().equals(Duration.ZERO)) {//se a duracao devolvida for 0 nao classificar porque senao vamos classificar o ultimo video outra vez visto que o itemCorrente nao foi atualizado
					vph.classificar(5);//classificar todos com 5 estrelas
				}

			}
			System.out.println("\n\nPlaylist DEPOIS de ser visualizada:");
			resultados = vph.procurarPorTag("oop");//para que sejam impressas as playlists outra vez
			for(Playlist play: resultados) {
				System.out.println(play.getCodigo() + " pontuacao: " + play.getPontuacao() + "\n--------------------------------------------");
			}
		});
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




		/////////////////////////////////Aumentar visualizacoes para que os videos com top10 no titulo estejam no top_10, por ordem de codigo/////////////////////////////////////////////////////////////////////////
		//		int i = 100;
		//		while(i > 0) {
		//			bibGeral.getVideo("vid_7").get().atualizaVisualizacoes();
		//			i--;
		//		}
		//		i = 90;
		//		while(i > 0) {
		//			bibGeral.getVideo("vid_8").get().atualizaVisualizacoes();
		//			i--;
		//		}
		//		i = 80;
		//		while(i > 0) {
		//			bibGeral.getVideo("vid_9").get().atualizaVisualizacoes();
		//			i--;
		//		}
		//		i = 70;
		//		while(i > 0) {
		//			bibGeral.getVideo("vid_10").get().atualizaVisualizacoes();
		//			i--;
		//		}
		//		i = 60;
		//		while(i > 0) {
		//			bibGeral.getVideo("vid_11").get().atualizaVisualizacoes();
		//			i--;
		//		}
		//		i = 50;
		//		while(i > 0) {
		//			bibGeral.getVideo("vid_12").get().atualizaVisualizacoes();
		//			i--;
		//		}
		//		i = 40;
		//		while(i > 0) {
		//			bibGeral.getVideo("vid_13").get().atualizaVisualizacoes();
		//			i--;
		//		}
		//		i = 30;
		//		while(i > 0) {
		//			bibGeral.getVideo("vid_14").get().atualizaVisualizacoes();
		//			i--;
		//		}
		//		i = 20;
		//		while(i > 0) {
		//			bibGeral.getVideo("vid_15").get().atualizaVisualizacoes();
		//			i--;
		//		}
		//		i = 10;
		//		while(i > 0) {
		//			bibGeral.getVideo("vid_16").get().atualizaVisualizacoes();
		//			i--;
		//		}


		///imprimir top10
		talvezSessao.ifPresent( (Sessao s) -> {
			CriarPlayListHandler cph = p.getCriarPlayListHandler(s);
			List<Pair<String, String>> videos;
			try {
				videos = cph.obterVideosComHashtag("HASHTAG_INEXISTENTE");
			} catch (NoSuchVideoWithHashtagException e) {
				videos = cph.pretendeVerTop10("S");
			}
			System.out.println("----------Lista TOP10:----------");
			for(Pair<String, String> par: videos) {
				System.out.println("    " + par.getSecond());
			}
			System.out.println("--------------------------------");
			System.out.println("views de todos os videos da bibGeral");
			Object[] allVids= bibGeral.getAllVideos().stream().toArray();
			for(Object obj: allVids) {
				Video v = (Video) obj;
				System.out.println(v.getCodigo() + " - >" + v.getVisualizacoes());
			}
		});
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////FASE4///////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////	
		talvezSessaoUtilizadorPaulo.ifPresent( (Sessao s) -> {

			CriarPlaylistAutoHandler cpah = p.getCriarPlaylistAutoHandler();
			List<String> strategies = cpah.iniciaCriacaoAutoPlaylist("PlaylistFase4");
			String strat = strategies.get(2);
			String cod = cpah.indicaEstrategia(strat, 5);

			VerPlaylistHandler vplh = p.getVerPlaylistHandler(s);

			System.out.println("\n\nPlaylist ANTES de ser visualizada:");
			PlaylistDomain pl = catPlaylists.getPlaylist(cod).get();//get() nao pode falhar porque estah a ser passado um codigo que estah presente no catalogo de playlists
			vplh.escolhePlaylist(cod);
			System.out.println(pl.toString());

			System.out.println(pl.getCodigo() + " pontuacao: " + pl.getPontuacao() + "\n--------------------------------------------");


			int tamanhoPl = pl.getItens().size();
			int j;
			for(j = 0; j <= tamanhoPl; j++) { //<= para ver se o metodo verProximo diz que nao existem mais videos
				Pair<String, Duration> video = vplh.verProximo();
				if(!video.getSecond().equals(Duration.ZERO)) {//se a duracao devolvida for 0 nao classificar porque senao vamos classificar o ultimo video outra vez visto que o itemCorrente nao foi atualizado
					vplh.classificar(1);//classificar todos com 1 estrela
				}

			}
			System.out.println("\n\nPlaylist DEPOIS de ser visualizada:");
			System.out.println(pl.toString());
			System.out.println(pl.getCodigo() + " pontuacao: " + pl.getPontuacao() + "\n--------------------------------------------");

		});

		///imprimir top10 e view de todos os videos na bib Geral
		talvezSessao.ifPresent( (Sessao s) -> {
			CriarPlayListHandler cph = p.getCriarPlayListHandler(s);
			List<Pair<String, String>> videos;
			try {
				videos = cph.obterVideosComHashtag("HASHTAG_INEXISTENTE");
			} catch (NoSuchVideoWithHashtagException e) {
				videos = cph.pretendeVerTop10("S");
			}
			System.out.println("----------Lista TOP10:----------");
			for(Pair<String, String> par: videos) {
				String cod = par.getSecond();
				System.out.println("    " + cod + " -> " + bibGeral.getVideo(cod).get().getVisualizacoes());
			}
			System.out.println("--------------------------------");
			System.out.println("views de todos os videos da bibGeral");
			Object[] allVids= bibGeral.getAllVideos().stream().toArray();
			for(Object obj: allVids) {
				Video v = (Video) obj;
				System.out.println(v.getCodigo() + " - >" + v.getVisualizacoes());
			}
		});

		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////


	}

	private static void imprimeVideoInfo(Video vid) {
		System.out.println("  Titulo: " + vid.getTitulo());
		System.out.println("  Endereco: " + vid.getEndereco());
		System.out.println("  Visualizacoes: " + vid.getVisualizacoes());
		Set<Hashtag>hashtags = vid.getHashtags();
		System.out.println("  Hashtags: ");
		for(Hashtag h: hashtags) {
			System.out.println("      " + h.getNome());
		}
		if(vid instanceof Clip) {
			Clip clip = (Clip) vid;
			System.out.println("*clip* Duracao: " + clip.getDuracao());
		}
		System.out.println("");//mudar de linha
	}

}
