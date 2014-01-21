package alternativ.anlagen;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import alternativ.domain.Charge;
import alternativ.domain.Lebkuchen;
import alternativ.domain.Resource;

public class Blech extends Anlage {
	
	public static String BLECH = "Blech"; 

	public Blech(String standort) {
		super(BLECH+" "+standort);
	}

	private ConcurrentHashMap<String,Charge> bleche = new ConcurrentHashMap<String, Charge>();
	
	@Override
	public boolean objectLiefern(Resource t) throws RemoteException,
			InterruptedException {
		if(checkInstance(Charge.class, t)){
			Charge l = (Charge)t; 
			bleche.put(	l.getBaeckerId(), l);
		}else if(checkInstance(Lebkuchen.class, t)){
			Lebkuchen l = (Lebkuchen)t; 
			Charge charge = bleche.get(l.getBaeckerId());
			if(charge==null){
				charge = new Charge();
				bleche.put(l.getBaeckerId(),charge);
			}
			l.setChargeId(charge.getUID());
			charge.add(l);
			return true;
		}
		
		return false;
	}

	@Override
	public Resource objectHolen(Object optionalParameter)
			throws RemoteException, InterruptedException {
		if(checkInstance(String.class, optionalParameter)){
			String key = (String) optionalParameter;
			return bleche.remove(key);
		}
		return null;
	}

	@Override
	public Collection<Charge> getCharges() {
		return bleche.values();
	}

}
