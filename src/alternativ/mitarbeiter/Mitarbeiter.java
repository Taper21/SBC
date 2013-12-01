package alternativ.mitarbeiter;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.anlagen.Anlage;
import alternativ.anlagen.AnlageInterface;
import alternativ.domain.Resource;

public abstract class Mitarbeiter {
	
	protected Logger logger = LoggerFactory.getLogger(Mitarbeiter.class);
	
	protected AnlageInterface quelle;
	protected AnlageInterface ziel;

	private String id;
	
	public Mitarbeiter(String quelle, String ziel, String id){
		this.id = id;
		try{
			Registry registry = null;
        	try {
        		registry = LocateRegistry.createRegistry(1099);
        	    // This call will throw an exception if the registry does not already exist
        	}
        	catch (RemoteException e) { 
        		registry = LocateRegistry.getRegistry(1099);
        	}
	        this.quelle = (AnlageInterface) registry.lookup(quelle);
	        this.ziel = (AnlageInterface) registry.lookup(ziel);
	    } catch (Exception e) {
	        logger.error("Mitarbeiter exception:");
	        e.printStackTrace();
	    }
	}
	
	public  Resource besorgeZutat(Object optionalParameter){
		try {
			return quelle.objectHolen(optionalParameter);
		} catch (RemoteException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	public abstract void verarbeiteZutat();
	
	public boolean gibZutatAb(Resource resource){
		try {
			return ziel.objectLiefern(resource);
		} catch (RemoteException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public Logger getLogger(){
		return logger;
	}
	
	public String getId(){
		return this.id;
	}
}
