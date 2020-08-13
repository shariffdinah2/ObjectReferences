package uk.co.dinahbyte;

public class SyncClass implements Runnable {

	public  void  test() {
		System.out.println(Thread.currentThread().getName() + " :: test called");
	}
	
	public   void test2() {
		System.out.println(Thread.currentThread().getName() + " :: test2 called");
	}

	@Override
	public void run() {
		test();
		test2();
		
	}
}
