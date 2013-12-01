package alternativ.anlagen;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.domain.Resource;


public abstract class Anlage implements AnlageInterface {
	
	protected Logger logger = LoggerFactory.getLogger(Anlage.class);
	
	public Anlage(String name){
        try {
        	Registry registry = null;
        	try {
        		registry = LocateRegistry.createRegistry(1099);
        	    // This call will throw an exception if the registry does not already exist
        	}
        	catch (RemoteException e) { 
        		registry = LocateRegistry.getRegistry(1099);
        	}
        	AnlageInterface export = this;
            UnicastRemoteObject.exportObject(export,0);
            registry.rebind(name,  export);
            logger.info(name + " bound");
        } catch (Exception e) {
            logger.error(name + " exception:");
            e.printStackTrace();
        }
	}
	
	
	
	public  boolean checkInstance(Class<?> type, Object param){
		boolean returnValue = type == param.getClass();
		if(!returnValue){
			logger.error("expected type. " + type +" got type: " + param.getClass());
		}
		return returnValue;
	}
}
