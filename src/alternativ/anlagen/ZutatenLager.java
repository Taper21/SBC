package alternativ.anlagen;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.domain.AlternativZutat;
import alternativ.domain.Resource;

import domain.Zutat;
import domain.ZutatTypEnum;


public class ZutatenLager extends Anlage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(ZutatenLager.class);
	
	private Queue<AlternativZutat> honig = new ConcurrentLinkedQueue<AlternativZutat>();
	private Queue<AlternativZutat> mehl = new ConcurrentLinkedQueue<AlternativZutat>();
	private Queue<AlternativZutat> ei  = new ConcurrentLinkedQueue<AlternativZutat>();

	 public ZutatenLager() {
	        super("ZutatenLager");
	    }
	
	@Override
	public boolean objectLiefern(Resource resource) throws RemoteException {
		if(checkInstance(AlternativZutat.class, resource)){
			AlternativZutat zutat = (AlternativZutat) resource;
			logger.info(zutat + " received of type : " + zutat.getZutatTypEnum());
			switch(zutat.getZutatTypEnum()){
			case EI: ei.add(zutat);
			break;
			case HONIG: honig.add(zutat);
			break;
			case MEHL: mehl.add(zutat);
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
	public Resource objectHolen(Object requestedType) throws RemoteException {
		Resource returnValue = null;
		if(checkInstance(ZutatTypEnum.class, requestedType)){
			ZutatTypEnum zutat = (ZutatTypEnum) requestedType;
			switch(zutat){
			case EI: returnValue = ei.poll();
				break;
			case HONIG: returnValue = honig.poll();
				break;
			case MEHL: returnValue = mehl.poll();
				break;
			}
			if(returnValue!=null){
				logger.info(zutat + "erfolgreich transmitted of type : " + zutat);
			}
		}
		return returnValue;
	}

}
