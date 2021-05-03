package ex1;

public class MyCubeGame extends CubeGame{

	public MyCubeGame(int maxCubes) {
		super(maxCubes);
	}

	@Override
	public boolean encaixa(Cube c) {
		for(Cube cubo: cubosJogados) {
			if(cubo.fit(c)) {
				return true;
			}
		}
		return this.cubosJogados.isEmpty();
	}

}
