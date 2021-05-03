package ex4;

public class Teste {
	
	public static void main(String[] args) {
		//1 - Como AnimalHouse depende dum objecto tem de ser o mesmo dos dois lados
		AnimalHouse<Animal> h1 = new AnimalHouse<Cat>();
		//2
		AnimalHouse<Dog> h2 = new AnimalHouse<Animal>();
		//3 - como h3 e do tipo ? n sabe o q pode receber
		AnimalHouse<?> h3 = new AnimalHouse<Cat>();
		h3.setAnimal(new Cat(4));
		//4
		AnimalHouse h4 = new AnimalHouse();
		h4.setAnimal(new Dog(4));
		
	}
	
}
