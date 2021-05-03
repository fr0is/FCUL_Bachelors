package ex9;

public class CarCentralLock extends Car {

	public CarCentralLock(int nDoors) {
		super(nDoors);
	}

	@Override
	public void lockDoor(int n) {
		if(n == 1) {
			for(int i = 1; i <= doors();i++) {
				super.lockDoor(i);
			}
		}else{
			super.lockDoor(n);
		}
	}

	@Override
	public void unlockDoor(int n) {
		if(n == 1) {
			for(int i = 1; i <= doors();i++) {
				super.unlockDoor(i);
			}
		}else{
			super.unlockDoor(n);
		}
	}

}
