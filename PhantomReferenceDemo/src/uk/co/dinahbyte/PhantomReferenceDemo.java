package uk.co.dinahbyte;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;

public class PhantomReferenceDemo {

	public static void main(String[] args) {

		ReferenceQueue<Customer> rq = new ReferenceQueue<Customer>();
		ArrayList<FinaliseCustomerReference> fcArr = new ArrayList<FinaliseCustomerReference>();
		ArrayList<Customer> custArr = new ArrayList<Customer>();
		
		for(int i=0; i<5; i++) {
			Customer c = new Customer();
			custArr.add(c);
			fcArr.add(new FinaliseCustomerReference(c,rq));
		}
		// after this for loop, we have
		// 		5 strong references
		//		5 Phantom references
		// to Customer objects in memory
		System.out.println("First call to GC");
		System.gc();
		System.out.println("Is any object in PhantomRef queue?");
		for(FinaliseCustomerReference pr: fcArr) {
			System.out.println(pr.isEnqueued()); 
		}

		custArr = null;
		// after the above line of code, no strong references to the 5 Customer objects exist
		// after the above line of code, 5 Customer Objects are put in the ReferenceQueue rq
		// because there are now only Phantom References
		
		System.out.println("Set custArr to null");
		System.out.println("Second call to GC");
		System.gc();
		System.out.println("Is any object in PhantomRef queue?");
		for(FinaliseCustomerReference pr: fcArr) {
			System.out.println(pr.isEnqueued()); 
		}
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println("Third call to GC");
		System.gc();
		System.out.println("Is any object in PhantomRef queue?");
		for(FinaliseCustomerReference pr: fcArr) {
			System.out.println(pr.isEnqueued()); 
		}
		// expect 5 lines each with 'true'
		
		
		Reference<? extends Customer> ref ;
		// declare a reference to a Customer object as this will be returned from the rq
		while ((ref = rq.poll()) != null) {
			((FinaliseCustomerReference)ref).cleanup();
			ref.clear();
		}
		// after this while loop, no objects left in the queue
		// there are no more references( Phantom or strong) to the 5 Customer objects 
	}

}

class FinaliseCustomerReference extends PhantomReference<Customer>{

	public FinaliseCustomerReference(Customer referent, ReferenceQueue<? super Customer> q) {
		super(referent, q);
	}
	
	public void cleanup() {
		System.out.println("cleanup called");
	}

}

class Customer {
	public void finalize() {
		System.out.println("finalise called");
		try {
			super.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
