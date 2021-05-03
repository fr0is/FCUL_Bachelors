package oochess.app.elostrategies;

import oochess.app.domain.Utilizador;

public interface EloStrategy {
	public int criarElo();
	public double calcularElo(Utilizador u1, Utilizador u2, String resultado);
}
