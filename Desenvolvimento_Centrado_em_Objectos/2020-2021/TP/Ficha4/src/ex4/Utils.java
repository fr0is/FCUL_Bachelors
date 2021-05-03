package ex4;

import java.util.Collection;

public class Utils {
	
	public int getPernas(AnimalHouse<Animal> animalHouse) {
		return animalHouse.getAnimal().getLegs();
	}
	
	
	public int getTotalPernas(Collection<AnimalHouse<Animal>> animalHouses) {
		int counter = 0;
		for(AnimalHouse<Animal> ah: animalHouses) {
			counter += getPernas(ah);
		}
		return counter;
	}
	
	public void putAnimal(AnimalHouse<Animal> h, Animal a) {
		h.setAnimal(a);
	}
	
	public void addAnimal(Collection<AnimalHouse<Animal>> c, Animal a) {
		AnimalHouse<Animal> h = new AnimalHouse<Animal>();
		h.setAnimal(a);
		c.add(h);
	}

}
