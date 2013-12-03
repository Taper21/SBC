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
import alternativ.mitarbeiter.Packung;

public class FertigePackungenLager extends Anlage {

	public static final String FERTIGEPACKUNGENLAGER = "FertigePackungenLager";
	
	public Queue<Packung> fertig = new ConcurrentLinkedQueue<Packung>();
	
	public FertigePackungenLager() {
		super(FERTIGEPACKUNGENLAGER);
	}
	
	public static void main(String[] args) {
    	new FertigePackungenLager();
    }

	@Override
	public boolean objectLiefern(Resource t) throws RemoteException {
		if(checkInstance(Packung.class, t)){
			Packung packung = (Packung) t;
			logger.info("FertigePackungenLager bekommt eine Packung id: " + packung.getUID());
			return true;
		}
		return false;
	}

	@Override
	public Resource objectHolen(Object optionalParameter)
			throws RemoteException {
		throw new UnsupportedOperationException();
}

}
