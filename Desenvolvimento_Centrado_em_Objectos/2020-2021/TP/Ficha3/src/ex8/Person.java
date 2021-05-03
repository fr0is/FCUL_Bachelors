package ex8;

public class Person {
	private String name, surname;
	public void setFirstname(String n) {
		name = n;
	}
	public void setSurname(String n) {
		surname = n;
	}
	public String getFirstname() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getName() {
		return getFirstname() + " " + getSurname();
	}
	public String toString() {
		return "My name is " + getName();
	}
	public static void main(String[] args) {
		Agent a = new Agent();
		a.setSurname("Bond");
		a.setFirstname("Jaime");
		System.out.println(a.toString());
	}
}