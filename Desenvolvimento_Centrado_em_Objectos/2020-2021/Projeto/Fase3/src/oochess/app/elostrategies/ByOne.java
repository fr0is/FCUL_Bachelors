package oochess.app.elostrategies;

import oochess.app.domain.Utilizador;

public class ByOne implements EloStrategy{

	@Override
	public double calcularElo(Utilizador u1, Utilizador u2, String resultado) {
		switch(resultado.toLowerCase()) {
			case "vitoria":
				u1.addElo(1);
				u2.addElo(-1);
				break;
			case "derrota":
				u1.addElo(-1);
				u2.addElo(1);
				break;
			case "empate":
				break;
			default:
				break;
		}
		return u1.getElo();
	}

	@Override
	public int criarElo() {
		return 5;
	}

}
