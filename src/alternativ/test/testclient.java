package alternativ.test;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import alternativ.anlagen.AnlageInterface;

public class testclient extends Thread implements Remote, Serializable {
	private Itestserver server;
	private boolean b;
	int i=0;
	
	public testclient(boolean b){
		this.b = b;
		init();
		
	}
	
	@Override
	public void run() {
		while(true){try {
			i++;
			System.out.println(this.b + "      "+			server.getInt(b)				);
			if(i>5&& b){
				return;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}}
	}
	
	public static void main(String[] args){
//		new testclient(true).start();
//		new testclient(false).start();
//		new testclient(true).test();
//		Object s;
//		String string = new String("maama");
//		s = string;
//		System.out.println(s.toString());
//		System.out.println(string.toString());
		int positive = 0;
		int negative = 0;
		for(int i = 0; i<1000; i++){
			boolean x = (Math.abs(new Random().nextLong()%100))<=50;
			if(x)
				positive++;
			else
				negative++;
			System.out.println(x);
		}
		System.out.println(positive +" : " + negative);
	}

	private void test() {
		HashMap<String, MutableClass> a = new HashMap<String, MutableClass>();
		MutableClass integer = new MutableClass();
		a.put("1", integer);
		MutableClass mutableClass = a.get("1");
		System.out.println(mutableClass);
		mutableClass.i = 666;
		System.out.println(a.get("1"));
	}
	


	public void init(){
	try{
		Registry registry = null;
    	try {
    		registry = LocateRegistry.createRegistry(1099);
    	    // This call will throw an exception if the registry does not already exist
    	}
    	catch (RemoteException e) { 
    		registry = LocateRegistry.getRegistry(1099);
    	}
        server =  (Itestserver) registry.lookup("test");
    } catch (Exception e) {
        e.printStackTrace();
    }
	}

}

class MutableClass {
	public int i = 0; 
	
	@Override
	public String toString() {
		return "" + i;
	}
}
