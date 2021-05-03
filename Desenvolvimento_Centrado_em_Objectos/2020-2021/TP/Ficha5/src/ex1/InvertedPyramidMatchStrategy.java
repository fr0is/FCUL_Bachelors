package ex1;

public class InvertedPyramidMatchStrategy implements CubeMatchStrategy{

	@Override
	public boolean encaixa(CubeGame cg, Cube c) {
		return c.getFace() > cg.lastPlay().getFace() || cg.cubosJogados.isEmpty();
	}

}
