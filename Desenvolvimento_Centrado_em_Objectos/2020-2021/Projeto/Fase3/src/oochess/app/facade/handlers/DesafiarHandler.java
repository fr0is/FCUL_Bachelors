package oochess.app.facade.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import oochess.app.discordintegration.DiscordAdapter;
import oochess.app.domain.Desafio;
import oochess.app.domain.Torneio;
import oochess.app.domain.Utilizador;
import oochess.app.domain.catalogos.CatalogoTorneios;
import oochess.app.domain.catalogos.CatalogoUtilizadores;
import oochess.app.domain.factories.DiscordFactory;
import oochess.app.facade.Sessao;
import oochess.app.facade.exceptions.NoEligiblePlayersException;


public class DesafiarHandler {
	private Utilizador uCorrente;
	private Desafio novoDesafio;
	private Torneio torneioCorrente;
	private CatalogoUtilizadores catUtl;
	private CatalogoTorneios catTorn;
	
	/**
	 * 
	 * @param sessao - Recebe Sessao atual que estah a decorrer
	 * @requires sessao !NULL
	 */
	public DesafiarHandler(Sessao sessao) {
		this.uCorrente = sessao.getUtilizador();
		this.catUtl = sessao.getCatUtl();
		this.catTorn = sessao.getCatTorn();
	}
	
	/** UC5 -1 
	 * Cria o novo desafio e ass ao novo desafio
	 * @param nome - nome do Torneio a ser associado o desafio
	 * @requires Exista torneio Criado com torneioNome = nome
	 */
	public void indicaTorneio(String nome) {
		this.torneioCorrente = this.catTorn.getTorneio(nome); //1.1.1
		this.novoDesafio = new Desafio(null,null,this.uCorrente.getUsername(),null,null, this.torneioCorrente,0); //1.2
	}
	/** UC5 -1 
	 * Cria um novo desafio sem torneio Asssociado
	 */
	public void indicaTorneio() {
		this.torneioCorrente = null; //1.1.1
		this.novoDesafio = new Desafio(null,null,this.uCorrente.getUsername(),null,null, this.torneioCorrente,0); //1.2
	}
	/** UC5 -2
	 * Cria uma lista de jogadores pertencente ao intervalo de elo desejado
	 * @param delta - intervalo desejado de ELO dos adversarios
	 * @return Devolve lista com nome dos Utilizadores que cumprem o requisito
	 * @throws NoEligiblePlayersException Caso nao existam jogadores dentro do DeltaElo desejado
	 */
	public List<String> indicaDeltaElo(int delta) throws NoEligiblePlayersException {
		List<Utilizador> usersElegiveis;
        List<String> userEElo = new ArrayList<>();
        this.novoDesafio.setDeltaELO(delta); //2.1
        usersElegiveis = this.catUtl.getMap().entrySet().stream()
                  .filter(e -> ((e.getValue().getElo())>= this.uCorrente.getElo()-delta)&&(e.getValue().getElo()) <= this.uCorrente.getElo()+delta)
                  .map(Map.Entry::getValue)
                  .collect(Collectors.toList());//2.2
        if(usersElegiveis == null) {
            throw new NoEligiblePlayersException();
        }
        for(Utilizador u: usersElegiveis) {
            if(u.getUsername().equals(this.uCorrente.getUsername())) {
            }else {
            userEElo.add(u.getUsername()+" : "+u.getElo());
            }
        }
        return userEElo;
    }
	
	
	/**
	 * UC5 - 3
	 * @param nome nome do user adversario escolhido
	 * @requires nome de adversario seja valido
	 */
	public void indicaJogador(String nome) {
		this.novoDesafio.setAdversario(nome);	//3.1
	}

	/** UC5 - 4
	 * @param datahora data e hora marcada para o desafio
	 * @param msg mensagem enviada em conjunto com o desafio
	 * @return devolve o codigo do desafio
	 */
	public String indicaDetalhes(LocalDateTime datahora, String msg) {
		this.novoDesafio.setDMH(datahora, msg); //4.1
		this.novoDesafio.setCodigo(datahora.toString() + msg); //4.1
		enviaPartida(); //5
		return this.novoDesafio.getCodigo();//5.3
	}
	
	/**UC5 - 5
	 * Metodo que faz o envio do Desafio atraves do discord
	 */
	private void enviaPartida() {
		this.uCorrente.adicionaDesafio(this.novoDesafio.getCodigo(), this.novoDesafio);
		this.catUtl.getUtilizador(this.novoDesafio.getUserAdv()).adicionaDesafio(this.novoDesafio.getCodigo(), this.novoDesafio);//5.1
		String discordIDAdv = this.catUtl.getUtilizador(this.novoDesafio.getUserAdv()).getDiscordID(); //5.4
		String msg = "O jogador " + this.uCorrente.getUsername() +" convidou-o para uma partida de xadrez em " + this.novoDesafio.getData() 
					+ ":"+this.novoDesafio.getMensagem();
		DiscordFactory factory = DiscordFactory.getInstance();
		DiscordAdapter discAdapter = factory.getDiscordAdapter();
		discAdapter.enviaPartida(discordIDAdv, msg);
	}

}
