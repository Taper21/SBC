package alternativ.anlagen;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.anlagen.rmi.Anlage;
import alternativ.mitarbeiter.AlternativLieferant;

public class ZutatenLager implements Anlage {
	
	Logger logger = LoggerFactory.getLogger(ZutatenLager.class);

	 public ZutatenLager() {
	        super();
	    }
	
	@Override
	public <T> T objectLiefern(T t) throws RemoteException {
		logger.info(t + "received");
		return null;
	}


    public static void main(String[] args) {
    	Logger logger = LoggerFactory.getLogger(ZutatenLager.class);
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "ZutatenLager";
            Anlage engine = new ZutatenLager();
            Anlage stub =
                (Anlage) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            logger.info("ZutatenLager bound");
        } catch (Exception e) {
            logger.error("ZutatenLager exception:");
            e.printStackTrace();
        }
    }

}
