package alternativ.anlagen;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import alternativ.domain.AlternativZutat;
import alternativ.domain.Charge;
import alternativ.domain.Resource;

public class Ofen extends Anlage {

	private static int MAX_LEBKUCHEN = 10;
	
	private AtomicInteger lebkuchenAnzahl = new AtomicInteger(0);
	private Map<UID, Charge> charges = new ConcurrentHashMap<UID,Charge>(MAX_LEBKUCHEN);
	
	public Ofen() {
		super("Ofen");
	}
	
	public static void main(String[] args) {
    	new Ofen();
    }

	@Override
	public boolean objectLiefern(Resource t) throws RemoteException {
		logger.info("ofen bekommt eine Charge");
		if(checkInstance(Charge.class, t)){
			Charge charge = (Charge) t;
			synchronized (charges) {
			if(MAX_LEBKUCHEN-charges.size()<charge.size()){
				logger.info("Ofen hat keinen platz fuer den charge("+ charge.size()+") hat gerade "+lebkuchenAnzahl.get()+".");
				//kein platz im ofen
				return false;
			}else {
					logger.info("Lebkuchen in Ofen: " + lebkuchenAnzahl.get());
					charges.put(charge.getUID(), charge);
					lebkuchenAnzahl.addAndGet(charge.size());
					logger.info("charge wurde hinzugefuegt: " + charge.getUID());
					logger.info("Lebkuchen in Ofen: " + lebkuchenAnzahl.get());
					
				return true;
			}
			}
		}
		return false;
	}

	@Override
	public Resource objectHolen(Object optionalParameter)
			throws RemoteException {
		if(checkInstance(UID.class, optionalParameter)){
			synchronized(charges){
				Charge remove = charges.remove(optionalParameter);
				if(remove!=null){
					lebkuchenAnzahl.addAndGet(-remove.size());
				}
				return remove;
			}
		}
		return null;
	}

}
