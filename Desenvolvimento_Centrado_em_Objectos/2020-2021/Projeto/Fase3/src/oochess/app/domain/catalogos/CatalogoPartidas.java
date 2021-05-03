package oochess.app.domain.catalogos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oochess.app.domain.Amigavel;
import oochess.app.domain.Desafio;
import oochess.app.domain.Espontanea;
import oochess.app.domain.Partida;
import oochess.app.domain.Utilizador;

public class CatalogoPartidas {
	private Map<String,List<Partida>> listaPartidasPorUser;

	private static CatalogoPartidas INSTANCE;

	public static CatalogoPartidas getInstance() {
		if(INSTANCE == null) { //Lazy Loading
			INSTANCE = new CatalogoPartidas();
		}
		return INSTANCE;
	}

	protected CatalogoPartidas() {
		this.listaPartidasPorUser = new HashMap<>();
	}

	
	public void adicionarUtilizadorListaPartidas(Utilizador userNovo) {
		List<Partida> lp = new ArrayList<>();
		this.listaPartidasPorUser.put(userNovo.getUsername(), lp);
	}
	
	

	/**
	 * UC6-1.2.1 e 1.2.2
	 * Obter partida associada a d:Desafio tal que d.codDesfio = codigoDesafio  
	 * @param codigoDesafio - codigo desafio associado a partida
	 * @param username - username do utilizador
	 * @requires codigoDesafio!=null && username!=null
	 * @return partida tal que p.codigoDesafio=codigoDesafio
	 */
	public Partida getPartida(String codigoDesafio, String username) {
		//1.2.1
		List<Partida> lp = this.listaPartidasPorUser.get(username);
		//1.2.2
		for(Partida p: lp) {
			if(p.getCodDesafio().contentEquals(codigoDesafio)) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * UC6-1.3.1 e 1.3.2
	 * Obter lista com os ultimos 5 utilizadores jogados
	 * @param username
	 * @param catalogoUtilizadores
	 * @requires username!=null && catalogoUtilizadores!=null
	 * @return lista do ultimos 5 utilizadores jogados
	 */
	public List<Utilizador> getUltimos5Jogos(String username, CatalogoUtilizadores catalogoUtilizadores) {
		//1.3.1
		List<Partida> li= this.listaPartidasPorUser.get(username);
		//1.3.2
		//Se ainda n houveram partidas devolve null caso contrario vai buscar as ultimas 5
		return li.isEmpty() ? null : getUltimas5Partidas(li, catalogoUtilizadores,username);
	}

	/**
	 * UC6-1.3.2
	 * Obter os ultimos 5 utilizadores jogados
	 * @param li - lista das partidas do user
	 * @param catUtl 
	 * @param username - username do user
	 * @requires li!=null && catUtl!=null && username!=null
	 * @return lista do ultimos 5 utilizadores jogados
	 */
	private List<Utilizador> getUltimas5Partidas(List<Partida> li, CatalogoUtilizadores catUtl, String username) {
		//Ordenar os jogos todos por data
		List<Partida> liByDate = ordenarPorData(li);
		List<Utilizador> liUsers;
		if(liByDate.size() < 5) {
			liUsers = adicionarUltimos5Utilizadores(liByDate,username,liByDate.size(),catUtl);
		}else {
			liUsers = adicionarUltimos5Utilizadores(liByDate,username,5,catUtl);
		}
		return liUsers;
	}

	/**
	 * UC6-2.2.1
	 * Criar nova partida espontanea e adiciona-la a ambos os users
	 * @param username - username do user atual
	 * @param userAdv - username do user adversario
	 * @param datahora - data e hora da partida
	 * @return nova partida espontanea tal que p.user1 = username && p.user2 = userAdv && p.datahora = datahora
	 */
	public Partida criaPartida(String username, String userAdv, LocalDateTime datahora) {
		Partida p = new Espontanea(username, userAdv, datahora);
		//Update lista partidas adv e uCorr
		List<Partida> liPart = this.listaPartidasPorUser.get(username);
		List<Partida> liPartAdv = this.listaPartidasPorUser.get(userAdv);
		liPart.add(p);
		liPartAdv.add(p);
		this.listaPartidasPorUser.replace(username, liPart);
		this.listaPartidasPorUser.replace(userAdv, liPartAdv);
		return p;

	}

	/**
	 * UC6
	 * Atualiza lista de partida de cada user da partida
	 * @param pCorrente - partida a ser atualizada
	 * @param uCorrente - utilizador 
	 * @param uAdv - utilizador adversario
	 */
	public void atualizaPartida(Partida pCorrente, Utilizador uCorrente, Utilizador uAdv) {
		//3.8
		//update lista partidas de cada user
		this.listaPartidasPorUser.replace(uCorrente.getUsername(), atualizarPartida(pCorrente,uCorrente));
		this.listaPartidasPorUser.replace(uAdv.getUsername(), atualizarPartida(pCorrente,uAdv));
	}

	/**
	 * UC6
	 * Atualiza partida na lista
	 * @param pCorrente - partida a ser atualizada
	 * @param user - user cuja lista deve ser atualizada
	 * @return nova lista de partidas
	 */
	private List<Partida> atualizarPartida(Partida pCorrente, Utilizador user) {
		//3.4
		List<Partida> lPart = this.listaPartidasPorUser.get(user.getUsername());
		//3.5
		int h = getIndexPartida(lPart,pCorrente);
		//3.6
		lPart.remove(h);
		//3.7
		lPart.add(pCorrente);
		return lPart;
	}

	/**
	 * Adicionar nova partida amigavel ah lista de partidas do user e a lista de partidas por cod desafio
	 * @param username - username do user 
	 * @param novaP - partida a ser adicionada a lista de partidas do user
	 */
	public void adicionarPartida(String username, Partida novaP) {
		List<Partida> liPart = this.listaPartidasPorUser.get(username);
		liPart.add(novaP);
		this.listaPartidasPorUser.replace(username, liPart);
	}

	private List<Utilizador> adicionarUltimos5Utilizadores(List<Partida> liByDate, String username, int size, CatalogoUtilizadores catUtl) {
		List<Utilizador> liUsers = new ArrayList<>();
		for(int j = size; j < liByDate.size(); j++) {
			//Se o nome do user1 for igual ao corrente adiciona o user2 caso contrario adiciona o user1
			if(liByDate.get(j).getUser1().equals(username)) {
				liUsers.add(catUtl.getUtilizador(liByDate.get(j).getUser2()));
			}else {
				liUsers.add(catUtl.getUtilizador(liByDate.get(j).getUser1()));
			}
		}
		return liUsers;
	}

	/**
	 * Ajuda ao UC6-1.3.2
	 * Ordenar as partidas por data(da mais recente para a mais antiga)
	 * @param li - lista de partidas a ser ordenada
	 * @return lista das partidas ordenadas da mais recente para a mais antiga
	 */
	private List<Partida> ordenarPorData(List<Partida> li) {
		List<Partida> l = li;
		Comparator<Partida> byOrderTime = (p1, p2) -> {
			if (p1.getDataHora().isAfter(p2.getDataHora())) return -1; 
			else return 1;
		};
		Collections.sort(l, byOrderTime);
		return l;
	}
	
	private int getIndexPartida(List<Partida> lPart, Partida partida) {
		int counter = 0;
		for(Partida p: lPart) {
			if(p.equals(partida)) {
				return counter;
			}
			counter ++;
		}
		return 0;
	}

	/**
	 * Cria partida quando desafio e aceite
	 * @param usernameAtual - username do user
	 * @param desafioCorr - desafio que vai ser associado a partida
	 * @ensures existe uma nova p:Amigavel tal que p.codigoDesafio=desafioCorr.codigo && p.user1=usernameAtual
	 * 			&& p.user2=desafioCorr.user1 && p.datahora=desafioCorr.datahora
	 */
	public void criaPartidaDesafio(String usernameAtual, Desafio desafioCorr) {
		List<Partida> listaUserAtual = this.listaPartidasPorUser.get(usernameAtual);
		String userAdv = desafioCorr.getUser1();
		List<Partida> listaUserAdv = this.listaPartidasPorUser.get(userAdv);
		Partida p = new Amigavel(usernameAtual,userAdv,desafioCorr.getData(),desafioCorr.getCodigo());
		listaUserAtual.add(p);
		listaUserAdv.add(p);
		this.listaPartidasPorUser.replace(usernameAtual, listaUserAtual);
		this.listaPartidasPorUser.replace(userAdv, listaUserAdv);
	}
}
