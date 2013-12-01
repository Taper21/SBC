package alternativ.mitarbeiter;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.anlagen.Anlage;
import alternativ.domain.Resource;

public abstract class Mitarbeiter {
	
	private Logger logger = LoggerFactory.getLogger(Mitarbeiter.class);
	
	protected Anlage quelle;
	protected Anlage ziel;

	private String id;
	
	public Mitarbeiter(String quelle, String ziel, String id){
		this.id = id;
		try{
	        Registry registry = LocateRegistry.getRegistry(null);
	        this.quelle = (Anlage) registry.lookup(quelle);
	        this.ziel = (Anlage) registry.lookup(ziel);
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
	public abstract void gibZutatAb();
	
	public Logger getLogger(){
		return logger;
	}
	
	public String getId(){
		return this.id;
	}
}
