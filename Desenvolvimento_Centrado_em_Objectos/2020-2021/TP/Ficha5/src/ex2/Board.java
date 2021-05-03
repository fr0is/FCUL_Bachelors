package ex2;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private List<Square> squares;
	private final int tamanhoBoard = 40;
	
	public Board() {
		this.squares = new ArrayList<>();
		for(int i = 0; i < tamanhoBoard; i++) {
			squares.add(new Square(Integer.toString(i)));
		}
	}
	
	public Square getSquare(Square oldLoc, int somaFaces) {
		int oldLoca = this.squares.indexOf(oldLoc);
		int newLoc = (oldLoca+somaFaces) % tamanhoBoard;
		return this.squares.get(newLoc);
	}
}
