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
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.domain.AlternativZutat;
import alternativ.domain.Charge;
import alternativ.domain.Resource;

import domain.ILebkuchen;
import domain.Zutat;
import domain.ZutatTypEnum;


public class ZutatenLager extends Anlage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(ZutatenLager.class);
	
	private ConcurrentLinkedQueue<AlternativZutat> honig = new ConcurrentLinkedQueue<AlternativZutat>();
	private ConcurrentLinkedQueue<AlternativZutat> mehl = new ConcurrentLinkedQueue<AlternativZutat>();
	private ConcurrentLinkedQueue<AlternativZutat> ei  = new ConcurrentLinkedQueue<AlternativZutat>();
	public static final String ZUTATEN_LAGER = "ZutatenLager";

	 public ZutatenLager() {
	        super(ZUTATEN_LAGER);
	    }
	
	@Override
	public boolean objectLiefern(Resource resource) throws RemoteException {
		logger.info("lager bekommt ein object");
		if(checkInstance(AlternativZutat.class, resource)){
			AlternativZutat zutat = (AlternativZutat) resource;
			logger.info(zutat + " received of type : " + zutat.getZutatTypEnum());
			switch(zutat.getZutatTypEnum()){
			case EI:
				synchronized(ei){
					ei.add(zutat);
					ei.notify();
				}
			break;
			case HONIG: 
				synchronized(honig){
					honig.add(zutat);
					honig.notify();
				}
			break;
			case MEHL: 
				synchronized(mehl){
					mehl.add(zutat);
					mehl.notify();
				}
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
		Resource returnValue = null;
		if(checkInstance(ZutatTypEnum.class, requestedType)){
			ZutatTypEnum zutat = (ZutatTypEnum) requestedType;
			switch(zutat){
			case EI: returnValue = ei.poll();
				returnValue = warteBisZutatVorhanden(returnValue, ei);
				break;
			case HONIG: returnValue = honig.poll();
			returnValue = warteBisZutatVorhanden(returnValue, honig);
				break;
			case MEHL: returnValue = mehl.poll();
			returnValue = warteBisZutatVorhanden(returnValue, mehl);
				break;
			}
			if(returnValue!=null){
				logger.info(zutat + "erfolgreich transmitted of type : " + zutat);
			}
		}
		return returnValue;
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
}
