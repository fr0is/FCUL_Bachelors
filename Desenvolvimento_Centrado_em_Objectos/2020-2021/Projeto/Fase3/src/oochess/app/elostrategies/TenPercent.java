package oochess.app.elostrategies;

import oochess.app.domain.Utilizador;

public class TenPercent implements EloStrategy{
	
	@Override
	public double calcularElo(Utilizador u1, Utilizador u2, String resultado) {
		double diferencaVD = 0.1*Math.abs(u1.getElo()-u2.getElo())+5;
		double diferencaE = 0.05*Math.abs(u1.getElo()-u2.getElo());
		switch(resultado.toLowerCase()) {
		case "vitoria":
			u1.addElo(diferencaVD);
			u2.addElo(-diferencaVD);
			break;
		case "derrota":
			u1.addElo(-diferencaVD);
			u2.addElo(diferencaVD);
			break;
		case "empate":
			//Se d<0 elo de u1 é menor q elo de u2
			//Se d>0 elo de u1 é maior q elo de u2
			int d = Double.compare(u1.getElo(), u2.getElo());
			if(d > 0) {
				u1.addElo(-diferencaE);
				u2.addElo(diferencaE);
			}else {
				u1.addElo(diferencaE);
				u2.addElo(-diferencaE);
			}
			break;
		default:
			break;
		}
		return u1.getElo();
	}

	@Override
	public int criarElo() {
		return 50;
	}

}
