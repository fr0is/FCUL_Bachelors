package ex1;

public class BabyCubeFace extends CubeGame{
	private CubeMatchStrategy estrategia;
	
	public BabyCubeFace(CubeMatchStrategy estrategia, int maxCubes) {
		super(maxCubes);
		this.estrategia = estrategia;
	}

	@Override
	public boolean encaixa(Cube c) {
		return this.estrategia.encaixa(this, c);
	}

}
