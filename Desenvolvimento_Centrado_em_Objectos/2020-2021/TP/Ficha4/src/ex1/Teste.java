package ex1;

public class Teste {

	public static void main(String[] args) {
		Color c1 = new Color(2,3,4);
		Color c2 = new Color(2,3,4);
		Color c3 = new Color(2,3,3);
		System.out.println(c1.equals(c2));
		System.out.println(c1.equals(c3));
		
		Rectangle r1 = new Rectangle(10,20);
		Rectangle r2 = new Rectangle(10,20);
		Rectangle r3 = new Rectangle(10,10);
		System.out.println(r1.equals(r2));
		System.out.println(r1.equals(r3));
		
		ColoredRectangle cr1 = new ColoredRectangle(10,20,20,20,20);
		ColoredRectangle cr2 = new ColoredRectangle(10,20,20,20,20);
		ColoredRectangle cr3 = new ColoredRectangle(10,10,10,10,10);
		System.out.println(r1.equals(r2));
		System.out.println(r1.equals(r3));
	
	}
	
}
