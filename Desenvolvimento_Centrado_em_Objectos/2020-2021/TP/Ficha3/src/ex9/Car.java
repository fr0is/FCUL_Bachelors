package ex9;

public class Car {
	private boolean[] lockedDoors;
	/**
	 * Construtor.
	 * @param nDoors numero de portas
	 * @requires nDoors > 2;
	 * @ensures (\forall int i; i in 1..doors(); !doorLocked(i));
	 */
	public Car(int nDoors) {
		lockedDoors = new boolean[nDoors];
	}

	/**
	 * Tranca porta.
	 * @param n numero da porta
	 * @requires n > 0 && n <= doors();
	 * @ensures doorLocked(n);
	 */
	public void lockDoor(int n) {
		lockedDoors[n - 1] = true;
	}

	/**
	 * Destranca porta.
	 * @param n numero da porta
	 * @requires n > 0 && n <= doors();
	 * @ensures !doorLocked(n);
	 */
	public void unlockDoor(int n) {
		lockedDoors[n - 1] = false;
	}

	/**
	 * Verifica se uma porta está trancada.
	 * @param n numero da porta
	 * @requires n > 0 && n <= doors();
	 */
	public boolean doorLocked(int n) {
		return lockedDoors[n - 1];
	}

	/**
	 * Indica o número de portas do carro.
	 * @ensures \result > 2;
	 */
	public int doors() {
		return lockedDoors.length;
	}
	
	public static void main(String[] args) {
		CarCentralLock ccl = new CarCentralLock(4);
		ccl.lockDoor(3);
		System.out.println(ccl.doorLocked(3));
		ccl.unlockDoor(3);
		System.out.println(ccl.doorLocked(3));
		ccl.lockDoor(1);
		System.out.println(ccl.doorLocked(3));
		ccl.unlockDoor(1);
		System.out.println(ccl.doorLocked(3));
	}
}
