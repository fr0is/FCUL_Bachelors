package ex6;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class Piece extends Observable{
	
	private Square location;
	
	public Square getLocation() {
		return this.location;
	}
	
	public void setLocation(Square newLocation) {
		Square oldLocation = this.location;
		location = newLocation;
		this.setChanged();
		this.notifyObservers(oldLocation);
	}


}
