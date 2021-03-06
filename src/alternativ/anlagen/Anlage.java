package alternativ.anlagen;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.ILebkuchen;

import alternativ.domain.Charge;
import alternativ.domain.Lebkuchen;
import alternativ.domain.Resource;


public abstract class Anlage implements AnlageInterface {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private String name;
	
	public Anlage(String name){
        try {
        	this.name = name;
        	Registry registry = null;
        	try {
        		registry = LocateRegistry.createRegistry(1099);
        	    // This call will throw an exception if the registry does already exist
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
	
	@Override
	public String getName() {
		return name;
	}
	
	
	
	public  boolean checkInstance(Class<?> type, Object param){
		if(param == null){
			return false;
		}
		boolean returnValue = type.isAssignableFrom(param.getClass());
		if(!returnValue){
			logger.error("expected type. " + type +" got type: " + param.getClass());
		}
		return returnValue;
	}
	
	public List<ILebkuchen> getAllLebkuchen() {
		List<ILebkuchen> result = new ArrayList<ILebkuchen>();
		for(Charge c:getCharges()){
			for(Lebkuchen l : c.getAll()){
				result.add(l);
			}
		}
		return result;
	}


	synchronized protected  <T extends RETURN,RETURN> RETURN warteBisZutatVorhanden(RETURN zutat, Queue<T> listeMitZutat)
			throws InterruptedException {
		logger.info("waiting for: " + listeMitZutat.getClass());
		synchronized(listeMitZutat){
			while(zutat ==null){
				listeMitZutat.wait();
				zutat = listeMitZutat.poll();
			}
		}
		return  zutat;
	}

	public abstract Collection<Charge> getCharges();
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
