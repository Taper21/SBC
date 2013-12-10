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
import alternativ.domain.Resource;

public class Ofen extends Anlage {

	private static int MAX_LEBKUCHEN = 10;
	
	private AtomicInteger lebkuchenAnzahl = new AtomicInteger(0);
	private ConcurrentHashMap<String, Charge> charges = new ConcurrentHashMap<String,Charge>(MAX_LEBKUCHEN);

	public static final String OFEN = "Ofen";
	
	public Ofen() {
		super(OFEN);
	}
	
	public static void main(String[] args) {
    	new Ofen();
    }

	@Override
	public boolean objectLiefern(Resource t) throws RemoteException, InterruptedException {
		if(checkInstance(Charge.class, t)){
			Charge charge = (Charge) t;
			logger.info("ofen bekommt eine Charge id: " + charge.getBaeckerId());
			synchronized(charges){
				
			//solange kein platz im ofen:
			while(MAX_LEBKUCHEN-charges.size()<charge.size()){
				logger.info("Ofen hat keinen platz fuer den charge("+ charge.size()+") hat gerade "+lebkuchenAnzahl.get()+".");
				//charge vom baecker ist voll
				if(charge.isVoll()){
					//charge ist voll, baecker soll warten bis genug platz im ofen
					logger.info("ofen ist voll lasse baecker warten weil charge ist voll");
					charges.wait();
				}else{
					logger.info("ofen ist voll lasse baecker zubereiten weil charge ist nicht voll");
					//charge nicht voll lass den baecker charge vollmachen
					return false;					
				}
			}
			//so viel platz noch frei, charge noch nicht voll, besser baecker noch baecker vorbereiten lassen
			if(MAX_LEBKUCHEN-lebkuchenAnzahl.get()>charge.MAX_SIZE && charge.size() != charge.MAX_SIZE){
				logger.info("ofen ist frei("+lebkuchenAnzahl + "), charge ist nicht voll, lass baecker charge vollmachen");
				return false;				
			}
				logger.info("Lebkuchen in Ofen: " + lebkuchenAnzahl.get());
				charges.put(charge.getBaeckerId(), charge);
				lebkuchenAnzahl.addAndGet(charge.size());
				charge.setStatusOfLebkuchen(Lebkuchen.Status.IN_OFEN);
				logger.info("charge wurde hinzugefuegt: " + charge.getUID());
				logger.info("Lebkuchen in Ofen: " + lebkuchenAnzahl.get());
				return true;
			}
		}
		return false;
	}

	@Override
	public Resource objectHolen(Object optionalParameter)
			throws RemoteException {
		if(checkInstance(String.class, optionalParameter)){
				
			synchronized(charges){
			Charge remove = charges.remove(optionalParameter);
			if(remove!=null){
				lebkuchenAnzahl.addAndGet(-remove.size());
			}
			charges.notify();
			return remove;
			}
		}
		return null;
	}

	@Override
	public Collection<Charge> getCharges() {
		return charges.values();
	}
}
