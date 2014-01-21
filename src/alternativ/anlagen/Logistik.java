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
import alternativ.domain.Auftrag;
import alternativ.domain.Charge;
import alternativ.domain.Lebkuchen;
import alternativ.domain.Lebkuchen.Status;
import alternativ.domain.Packung;
import alternativ.domain.Resource;

public class Logistik extends Anlage {

	public static final String LOGISTIK = "Logistik";

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
	public synchronized boolean objectLiefern(Resource t) throws RemoteException {

		if (checkInstance(Charge.class, t)) {

			Charge charge = (Charge) t;
			logger.info("Logistik bekommt eine Charge id: " + charge.getUID());
			switch (charge.getStatus()) {
			case ABFALL:
				abfallEimer.add(charge);
				logger.info("Abfall");
				removeAbgebissen(charge);
				break;
			case OK:
				removeAbgebissen(charge);
				logger.info("OK");
				split(charge);
				break;
			default:
				break;
			}
			synchronized (this) {
				this.notifyAll();
			}
			return true;
		}
		return false;
	}

	private void split(Charge charge) {
		for (Lebkuchen l : charge.getAll()) {
			l.setStatus(Status.WARTE_AUF_VERPACKUNG);
			switch (l.getType()) {
			case Normal:
				normal.add(l);
				break;
			case Nuss:
				nuss.add(l);
				break;
			case Schoko:
				schoko.add(l);
				break;
			}
		}
	}

	private void removeAbgebissen(Charge charge) {
		Lebkuchen toRemove = null;
		for (Lebkuchen x : charge.getAll()) {
			if (x.getStatus().compareTo(Status.GEGESSEN.name()) == 0) {
				toRemove = x;
				abgebissen.add(x);
				logger.info("abgebissen entfernt: " + x.getId());
				break;
			}
		}
		charge.remove(toRemove);
	}

	@Override
	synchronized public Resource objectHolen(Object optionalParameter) throws RemoteException, InterruptedException {
		synchronized (this) {
			while (nuss.size() + schoko.size() + normal.size() < 6) {
				System.out.println("block logistikworker");
				this.wait();
				return null;
			}
			
			//first request contains no optional Parameter, try again after waiting so long;
		}
		if (checkInstance(List.class, optionalParameter)) {
			List<Auftrag> vorschlag = (List<Auftrag>) optionalParameter;
			if (vorschlag.isEmpty()) {
				System.out.println("keine Vorschläge");
				return makeDefaultPriorityPackage();
			} else {
				for (Auftrag vItem : vorschlag) {
					if (!vItem.getGesamtPackungszahl().equals(vItem.getErledigtePackungen())) {
						if (normal.size() >= vItem.getNormal() && nuss.size() >= vItem.getNuss()
								&& schoko.size() >= vItem.getSchoko()) {
							System.out.println("Auftragspackung wird angelegt " + vItem.getID());
							Packung packung = new Packung();
							injectPackung(vItem.getNormal(), packung, normal);
							injectPackung(vItem.getNuss(), packung, nuss);
							injectPackung(vItem.getSchoko(), packung, schoko);
							packung.setAuftragId(vItem.getID());
							return packung;
						}
					}
				}
				Packung returnValue = makeDefaultPriorityPackage();
				if (returnValue == null) {
					// konnte keine packung liefern warten bis was reinkommt
					synchronized (this) {
						this.wait();
					}
				}
				return returnValue;
			}

		}
		// konnte keine packung liefern warten bis was reinkommt
		synchronized (this) {
			this.wait();
		}
		return null;
	}

	private Packung makeDefaultPriorityPackage() {
		int nussSize = nuss.size();
		int normalSize = normal.size();
		int schokoSize = schoko.size();
		if (normalSize > 1 && nussSize > 1 && schokoSize > 1) {
			Packung packung = new Packung();
			packung.add(normal.poll());
			packung.add(normal.poll());
			packung.add(nuss.poll());
			packung.add(nuss.poll());
			packung.add(schoko.poll());
			packung.add(schoko.poll());
			return packung;
		}
		if (normalSize > 2 && nussSize > 2) {
			Packung packung = new Packung();
			packung.add(normal.poll());
			packung.add(normal.poll());
			packung.add(normal.poll());
			packung.add(nuss.poll());
			packung.add(nuss.poll());
			packung.add(nuss.poll());
			return packung;
		}
		if (normalSize > 2 && schokoSize > 2) {
			Packung packung = new Packung();
			packung.add(normal.poll());
			packung.add(normal.poll());
			packung.add(normal.poll());
			packung.add(schoko.poll());
			packung.add(schoko.poll());
			packung.add(schoko.poll());
			return packung;
		}
		if (nussSize > 2 && schokoSize > 2) {
			Packung packung = new Packung();
			packung.add(nuss.poll());
			packung.add(nuss.poll());
			packung.add(nuss.poll());
			packung.add(schoko.poll());
			packung.add(schoko.poll());
			packung.add(schoko.poll());
			return packung;
		}
		return null;
	}

	private void injectPackung(int count, Packung packung, ConcurrentLinkedQueue<Lebkuchen> mutate) {
		while (count > 0) {
			count--;
			packung.add(mutate.poll());
		}
	}

	@Override
	public Collection<Charge> getCharges() {
		List<Charge> result = new ArrayList<Charge>();
		result.addAll(abfallEimer);
		return result;
	}

	@Override
	public List<ILebkuchen> getAllLebkuchen() {
		List<ILebkuchen> allLebkuchen = super.getAllLebkuchen();

		allLebkuchen.addAll(normal);
		allLebkuchen.addAll(nuss);
		allLebkuchen.addAll(schoko);
		allLebkuchen.addAll(abgebissen);
		return allLebkuchen;
	}

	public List<ILebkuchen> getAbfallVerkostet() {
		List<ILebkuchen> result = new ArrayList<ILebkuchen>();
		for (Charge c : abfallEimer) {
			for (Lebkuchen l : c.getAll()) {
				result.add(l);
			}
		}
		result.addAll(abgebissen);
		return result;
	}

}
