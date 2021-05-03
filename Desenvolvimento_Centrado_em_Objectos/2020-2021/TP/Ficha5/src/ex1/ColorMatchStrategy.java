package ex1;

public class ColorMatchStrategy implements CubeMatchStrategy{

	@Override
	public boolean encaixa(CubeGame cg, Cube c) {
		return cg.lastPlay().getColor().equals(c.getColor()) || cg.cubosJogados.isEmpty();
	}

}
