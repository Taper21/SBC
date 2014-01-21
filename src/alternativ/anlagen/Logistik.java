package alternativ.anlagen;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import domain.ILebkuchen;

import alternativ.domain.AlternativZutat;
import alternativ.domain.Charge;
import alternativ.domain.Lebkuchen;
import alternativ.domain.Lebkuchen.Status;
import alternativ.domain.Packung;
import alternativ.domain.Resource;
import alternativ.domain.Vorschlag;

public class Logistik extends Anlage {

	public static final String LOGISTIK = "Logistik";
	
	public ConcurrentLinkedQueue<Charge> charges = new ConcurrentLinkedQueue<Charge>();
	public ConcurrentLinkedQueue<Lebkuchen> schoko = new ConcurrentLinkedQueue<Lebkuchen>();
	public ConcurrentLinkedQueue<Lebkuchen> nuss = new ConcurrentLinkedQueue<Lebkuchen>();
	public ConcurrentLinkedQueue<Lebkuchen> normal = new ConcurrentLinkedQueue<Lebkuchen>();
	
	public ConcurrentLinkedQueue<Charge> abfallEimer = new ConcurrentLinkedQueue<Charge>();
	public ConcurrentLinkedQueue<Lebkuchen> abgebissen = new ConcurrentLinkedQueue<Lebkuchen>();
	
	public Logistik() {
		super(LOGISTIK);
	}
	
	public static void main(String[] args) {
    	new Logistik();
    }

	@Override
	 public boolean objectLiefern(Resource t) throws RemoteException {
		if(checkInstance(Charge.class, t)){
			synchronized(charges){
				
			Charge charge = (Charge) t;
			logger.info("Logistik bekommt eine Charge id: " + charge.getUID());
			switch(charge.getStatus()){
			case ABFALL: abfallEimer.add(charge);
			logger.info("Abfall");
				removeAbgebissen(charge);
				break;
			case OK: 
				removeAbgebissen(charge);
			case NICHT_KONTROLLIERT:
				charges.add(charge);
			logger.info("OK/NICHT_KONTROLLIERT");
				split(charge);
				break;
			default:
				break;
			}
			charges.notify();
			}
			return true;
			}
		return false;
	}

	private void split(Charge charge) {
		for(Lebkuchen l:charge.getAll()){
			switch(l.getType()){
			case Normal: normal.add(l); break;
			case Nuss: nuss.add(l); break;
			case Schoko: schoko.add(l); break;
			}
		}
	}

	private void removeAbgebissen(Charge charge) {
		Lebkuchen toRemove=null;
		for(Lebkuchen x:charge.getAll()){
			if(x.getStatus().compareTo(Status.GEGESSEN.name())==0){
				toRemove = x;
				abgebissen.add(x);
				logger.info("abgebissen entfernt: " + x.getId());
				break;
			}
		}
		charge.remove(toRemove);
	}

	@Override
	synchronized public Resource objectHolen(Object optionalParameter)
			throws RemoteException, InterruptedException {
		if(checkInstance(List.class, optionalParameter)){
			List<Vorschlag> vorschlag = (List<Vorschlag>) optionalParameter;
			if(vorschlag.isEmpty()){
				if(normal.size()>1&&nuss.size()>1&&schoko.size()>1){
					Packung packung = new Packung();
					packung.add(normal.poll());
					packung.add(normal.poll());
					packung.add(nuss.poll());
					packung.add(nuss.poll());
					packung.add(schoko.poll());
					packung.add(schoko.poll());
					return packung;
				}
				if(normal.size()>2&&nuss.size()>2){
					Packung packung = new Packung();
					packung.add(normal.poll());
					packung.add(normal.poll());
					packung.add(normal.poll());
					packung.add(nuss.poll());
					packung.add(nuss.poll());
					packung.add(nuss.poll());
					return packung;
				}
				if(normal.size()>2&&schoko.size()>2){
					Packung packung = new Packung();
					packung.add(normal.poll());
					packung.add(normal.poll());
					packung.add(normal.poll());
					packung.add(schoko.poll());
					packung.add(schoko.poll());
					packung.add(schoko.poll());
					return packung;
				}
			}
		}
		Charge poll = charges.poll();
		poll = warteBisZutatVorhanden(poll, charges);
		return poll;
}

	@Override
	public Collection<Charge> getCharges() {
		List<Charge> result = new ArrayList<Charge>();
		result.addAll(charges);
		result.addAll(abfallEimer);
		return result;
	}
	
	@Override
	public List<ILebkuchen> getAllLebkuchen() {
		List<ILebkuchen> allLebkuchen = super.getAllLebkuchen();
		allLebkuchen.addAll(abgebissen);
		return allLebkuchen;
	}

	public List<ILebkuchen> getAbfallVerkostet() {
		List<ILebkuchen> result = new ArrayList<ILebkuchen>();
		for(Charge c:abfallEimer){
			for(Lebkuchen l : c.getAll()){
				result.add(l);
			}
		}
		result.addAll(abgebissen);
		return result;
	}

}
