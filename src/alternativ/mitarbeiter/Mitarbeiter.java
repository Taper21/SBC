package alternativ.mitarbeiter;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.anlagen.Anlage;
import alternativ.anlagen.AnlageInterface;
import alternativ.domain.Resource;

public abstract class Mitarbeiter {
	
	protected Logger logger = LoggerFactory.getLogger(Mitarbeiter.class);
	
	protected AnlageInterface quelle;
	protected AnlageInterface ziel;
	protected AnlageInterface weiteresZiel;

	private String id;

	protected boolean close;
	
	public Mitarbeiter(String quelle, String ziel, String weiteresZiel ,String id){
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
	        if(!StringUtils.isEmpty(weiteresZiel)){
	        	this.weiteresZiel = (AnlageInterface) registry.lookup(weiteresZiel);
	        }
	    } catch (Exception e) {
	        logger.error("Mitarbeiter exception:");
	        e.printStackTrace();
	    }
	}
	
	public  Resource besorgeZutat(Object optionalParameter){
		try {
			return quelle.objectHolen(optionalParameter);
		} catch (RemoteException | InterruptedException  e) {
			if(e instanceof ConnectException){
				close = true;
			}
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	public abstract void verarbeiteZutat();
	
	public boolean gibZutatAb(Resource resource){
		try {
			return ziel.objectLiefern(resource);
		} catch (RemoteException | InterruptedException e) {
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
	
	public  boolean checkInstance(Class<?> type, Object param){
		if(param==null){
			return false;
		}
		boolean returnValue = type == param.getClass();
		if(!returnValue){
			logger.error("expected type. " + type +" got type: " + param.getClass());
		}
		return returnValue;
	}
}
