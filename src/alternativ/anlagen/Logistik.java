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

public class Logistik extends Anlage {

	public static final String LOGISTIK = "Logistik";
	
	public Queue<Charge> charges = new ConcurrentLinkedQueue<Charge>();
	public Queue<Charge> abfallEimer = new ConcurrentLinkedQueue<Charge>();
	
	public Logistik() {
		super(LOGISTIK);
	}
	
	public static void main(String[] args) {
    	new Logistik();
    }

	@Override
	public boolean objectLiefern(Resource t) throws RemoteException {
		if(checkInstance(Charge.class, t)){
			Charge charge = (Charge) t;
			logger.info("Logistik bekommt eine Charge id: " + charge.getUID());
			switch(charge.getStatus()){
			case ABFALL: abfallEimer.add(charge);
			logger.info("Abfall");
				break;
			case NICHT_KONTROLLIERT:
			case OK: charges.add(charge);
			logger.info("OK/NICHT_KONTROLLIERT");
				break;
			default:
				break;
			}
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
