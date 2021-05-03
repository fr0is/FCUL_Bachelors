package ex1;

import java.util.ArrayList;
import java.util.List;

public abstract class CubeGame {
	private int maxCubes;
	protected List<Cube> cubosJogados = new ArrayList<>();
	
	public CubeGame (int maxCubes) {
		this.maxCubes = maxCubes;
	}
	
	public Cube lastPlay () {
		if (this.cubosJogados.isEmpty())
			return null;
		return this.cubosJogados.get(this.cubosJogados.size()-1);
	}
	
	public boolean canPlay(Cube c) {
		return (cubosJogados.size()<maxCubes) && encaixa(c); 
	}
	
	public void play(Cube c) {
		cubosJogados.add(c);
	}
	
	public Iterable<Cube> playedCubes(){
		return cubosJogados;
	}
	
	public abstract boolean encaixa(Cube c);
	
}


