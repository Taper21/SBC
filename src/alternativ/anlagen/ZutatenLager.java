package alternativ.anlagen;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.domain.ZutatenFuerLebkuchen;
import alternativ.domain.AlternativZutat;
import alternativ.domain.Charge;
import alternativ.domain.Resource;

import domain.ILebkuchen;
import domain.Zutat;
import domain.ZutatTypEnum;

public class ZutatenLager extends Anlage implements Serializable {

	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(ZutatenLager.class);

	private LinkedBlockingQueue<AlternativZutat> honig = new LinkedBlockingQueue<AlternativZutat>();
	private LinkedBlockingQueue<AlternativZutat> mehl = new LinkedBlockingQueue<AlternativZutat>();
	private LinkedBlockingQueue<AlternativZutat> ei = new LinkedBlockingQueue<AlternativZutat>();
	private LinkedBlockingQueue<AlternativZutat> schokolade = new LinkedBlockingQueue<AlternativZutat>();
	private LinkedBlockingQueue<AlternativZutat> nuesse = new LinkedBlockingQueue<AlternativZutat>();
	public static final String ZUTATEN_LAGER = "ZutatenLager";

	public ZutatenLager() {
		super(ZUTATEN_LAGER);
	}

	@Override
	public boolean objectLiefern(Resource resource) throws RemoteException {
		logger.info("lager bekommt ein object");
		if (checkInstance(AlternativZutat.class, resource)) {
			AlternativZutat zutat = (AlternativZutat) resource;
			logger.info(zutat + " received of type : " + zutat.getZutatTypEnum());
			switch (zutat.getZutatTypEnum()) {
			case EI:
				ei.add(zutat);
				break;
			case HONIG:
				honig.add(zutat);
				break;
			case MEHL:
				mehl.add(zutat);
				break;
			case NUESSE:
				nuesse.add(zutat);
				break;
			case SCHOKOLADE:
				schokolade.add(zutat);
				break;
			}
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		new ZutatenLager();
	}

	@Override
	synchronized public Resource objectHolen(Object requestedType) throws RemoteException, InterruptedException {
		Set<Set<AlternativZutat>> zutatenSet = new HashSet<Set<AlternativZutat>>();
		int count = 1;
		while(count <=5) {
			if((!(ei.size() > 1) || honig.isEmpty() || mehl.isEmpty()) && count>1){
				break;
			}
			count++;
			Set<AlternativZutat> zutaten = new HashSet<AlternativZutat>();
			zutaten.add(ei.take());
			zutaten.add(ei.take());
			zutaten.add(honig.take());
			zutaten.add(mehl.take());
			AlternativZutat randomSchokoNuss = getRandomSchokoNuss();
			if (randomSchokoNuss != null) {
				zutaten.add(randomSchokoNuss);
			}
			zutatenSet.add(zutaten);
		}
		if(zutatenSet.isEmpty()){
			return null;
		}
		return new ZutatenFuerLebkuchen(zutatenSet);
	}

	synchronized private AlternativZutat getRandomSchokoNuss() throws InterruptedException {
		if (schokolade.isEmpty() && nuesse.isEmpty())
			return null;
		if (!schokolade.isEmpty() && !nuesse.isEmpty()) {
			switch (new Random().nextInt(3)) {
			case 0:
				return schokolade.poll();
			case 1:
				return nuesse.poll();
			case 2:
				return null;
			}
		}
		if (schokolade.isEmpty())
			return (new Random().nextBoolean() ? nuesse.poll() : null);
		if (nuesse.isEmpty())
			return (new Random().nextBoolean() ? schokolade.poll() : null);

		return null;
	}

	public List<Zutat> getAllMehl() {
		return new ArrayList<Zutat>(mehl);
	}

	public List<Zutat> getAllEier() {
		return new ArrayList<Zutat>(ei);
	}

	public List<Zutat> getAllHonig() {
		return new ArrayList<Zutat>(honig);
	}

	@Override
	public Collection<Charge> getCharges() {
		throw new NotImplementedException();
	}

	public List<Zutat> getAllSchokolade() {
		return new ArrayList<Zutat>(schokolade);
	}

	public List<Zutat> getAllNuesse() {
		return new ArrayList<Zutat>(nuesse);
	}
}
