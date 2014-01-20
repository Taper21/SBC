package alternativ.test;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class testserver implements Itestserver{

	private int i;

	@Override
	synchronized public int getInt(boolean caller) {
		
		System.out.println(caller + "   " + i);
		if(caller){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(caller);
		}
		return i++;
	}
	
	public static void main(String[] args){
		new testserver().init();
	}
	
	
	
	public void init(){
		   try {
	        	Registry registry = null;
	        	try {
	        		registry = LocateRegistry.createRegistry(1099);
	        	    // This call will throw an exception if the registry does already exist
	        	}
	        	catch (RemoteException e) { 
	        		registry = LocateRegistry.getRegistry(1099);
	        	}
	            UnicastRemoteObject.exportObject(this,0);
	            registry.rebind("test",  this);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}
