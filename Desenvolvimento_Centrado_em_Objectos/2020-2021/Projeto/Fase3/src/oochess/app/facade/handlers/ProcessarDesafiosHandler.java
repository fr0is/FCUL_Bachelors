package oochess.app.facade.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import oochess.app.discordintegration.DiscordAdapter;
import oochess.app.domain.Desafio;
import oochess.app.domain.Utilizador;
import oochess.app.domain.catalogos.CatalogoPartidas;
import oochess.app.domain.catalogos.CatalogoUtilizadores;
import oochess.app.domain.factories.DiscordFactory;
import oochess.app.facade.Sessao;
import oochess.app.facade.dto.DesafioDTO;
import oochess.app.facade.exceptions.NoTornaumentAssociatedException;

public class ProcessarDesafiosHandler {
	
	private Desafio desafioCorr;
	private Utilizador uCorrente;
	private CatalogoPartidas catPart;
	private CatalogoUtilizadores catUtl;

	public ProcessarDesafiosHandler(Sessao sessao) {
		this.uCorrente = sessao.getUtilizador();
		this.catPart = sessao.getCatPart();
		this.catUtl = sessao.getCatUtl();
	}

	public List<DesafioDTO> consultarDesafiosPendentes() {
		List<DesafioDTO> listaMockDesafios = new ArrayList<>();
		for(Desafio d: this.uCorrente.getDesafiosPendentes()) {
			listaMockDesafios.add(new DesafioDTO(d.getCodigo()));
		}
		return listaMockDesafios;
	}

	public void respondeADesafio(String codigo, boolean resposta) {
		this.desafioCorr = this.uCorrente.getDesafioExistente(codigo);
		if(resposta) {
			this.uCorrente.aceitaDesafio(codigo);
			this.catPart.criaPartidaDesafio(this.uCorrente.getUsername(),this.desafioCorr);
		}else {
			this.uCorrente.rejeitaDesafio(codigo);
		}
	}

	public void indicaNovaData(LocalDateTime datahora) throws NoTornaumentAssociatedException {
		if(this.desafioCorr.existeTorneioAssociado()) {
			Desafio dNovo = new Desafio(datahora,datahora.toString()+this.desafioCorr.getMensagem(),
					this.uCorrente.getUsername(),this.desafioCorr.getUser1(), this.desafioCorr.getMensagem(), 
					this.desafioCorr.getTorneioAssociado(), this.desafioCorr.getDeltaELO());
			enviaPartida(dNovo);
		}else{
			throw new NoTornaumentAssociatedException();
		}
		
		
	}
	private void enviaPartida(Desafio d) {
		this.uCorrente.adicionaDesafio(d.getCodigo(), d);
		this.catUtl.getUtilizador(d.getUser1()).adicionaDesafio(d.getCodigo(),d);
		String discordIDAdv = this.catUtl.getUtilizador(d.getUser1()).getDiscordID();
		String msg = "O jogador " + this.uCorrente.getUsername() +" convidou-o para uma partida de xadrez em " + d.getData() 
					+ ":"+d.getMensagem();
		DiscordFactory factory = DiscordFactory.getInstance();
		DiscordAdapter discAdapter = factory.getDiscordAdapter();
		discAdapter.enviaPartida(discordIDAdv, msg);
	}

}
