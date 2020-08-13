package uk.co.dinahbyte;

public class Tester {

	public static void main(String[] args) {
		Thread t = new Thread(new SyncClass(), "ONE");
		Thread t2 = new Thread(new SyncClass(), "TWO");
		
		t.start();
		t2.start();
		

	}

}
