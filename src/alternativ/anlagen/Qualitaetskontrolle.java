package alternativ.anlagen;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import alternativ.domain.AlternativZutat;
import alternativ.domain.Charge;
import alternativ.domain.Resource;

public class Qualitaetskontrolle extends Anlage {

	public static final String QUALITAETSKONTROLLE = "Qualitaetskontrolle";
	
	public Queue<Charge> charges = new ConcurrentLinkedQueue<Charge>();
	
	public Qualitaetskontrolle() {
		super(QUALITAETSKONTROLLE);
	}
	
	public static void main(String[] args) {
    	new Qualitaetskontrolle();
    }

	@Override
	public boolean objectLiefern(Resource t) throws RemoteException {
		if(checkInstance(Charge.class, t)){
			Charge charge = (Charge) t;
			logger.info("Qualitätskontrolle bekommt eine Charge id: " + charge.getUID());
			charges.add(charge);
			return true;
			}
		return false;
	}

	@Override
	public Resource objectHolen(Object optionalParameter)
			throws RemoteException {
		return charges.poll();
}

}