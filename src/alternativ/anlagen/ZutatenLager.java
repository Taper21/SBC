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


public class ZutatenLager implements Anlage, Serializable{
	
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(ZutatenLager.class);
	
	private Queue<AlternativZutat> honig = new ConcurrentLinkedQueue<AlternativZutat>();
	private Queue<AlternativZutat> mehl = new ConcurrentLinkedQueue<AlternativZutat>();
	private Queue<AlternativZutat> ei  = new ConcurrentLinkedQueue<AlternativZutat>();

	 public ZutatenLager() {
	        super();
	    }
	
	@Override
	public void objectLiefern(Resource resource) throws RemoteException {
		if(!(resource instanceof AlternativZutat)){
			throw new IllegalArgumentException("Resource supposed to be AlternativZutat");
		}
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
	}


    public static void main(String[] args) {
    	Logger logger = LoggerFactory.getLogger(ZutatenLager.class);
        try {
        	Registry createRegistry = LocateRegistry.createRegistry(1099);
            String name = "ZutatenLager";
            Anlage engine = new ZutatenLager();
            UnicastRemoteObject.exportObject(engine,0);
            createRegistry.rebind(name,  engine);
            logger.info("ZutatenLager bound");
        } catch (Exception e) {
            logger.error("ZutatenLager exception:");
            e.printStackTrace();
        }
    }

	@Override
	public Resource objectHolen(Object requestedType) throws RemoteException {
		if(!(requestedType instanceof ZutatTypEnum)){
			throw new IllegalArgumentException("Parameter supposed to be ZutatTypEnum");
		}
		ZutatTypEnum zutat = (ZutatTypEnum) requestedType;
		Resource returnValue = null;
		switch(zutat){
		case EI: returnValue = ei.poll();
		case HONIG: returnValue = honig.poll();
		case MEHL: returnValue = mehl.poll();
		}
		if(returnValue!=null){
			logger.info(zutat + "erfolgreich transmitted of type : " + zutat);
		}
		return returnValue;
	}

}
