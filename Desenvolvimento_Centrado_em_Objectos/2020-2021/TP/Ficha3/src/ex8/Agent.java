package ex8;

public class Agent extends Person{

	public String getFirstname() {
		return "";
		}
	
	public String getName() {
		return getSurname() +", "+super.getFirstname() +" "+ getSurname();
	}
}
