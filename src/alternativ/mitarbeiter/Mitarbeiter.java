package alternativ.mitarbeiter;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.anlagen.Anlage;
import alternativ.anlagen.AnlageInterface;
import alternativ.domain.Resource;

public abstract class Mitarbeiter {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected AnlageInterface quelle;
	protected AnlageInterface ziel;
	protected AnlageInterface weiteresZiel;

	private String id;

	protected boolean close;

	private String ort;

	public Mitarbeiter(String quelle, String ziel, String weiteresZiel, String id, String ort) {
		this.id = id;
		this.ort = ort;
		this.quelle = bindAnlage(quelle);
		this.ziel = bindAnlage(ziel);
		this.weiteresZiel = bindAnlage(weiteresZiel);
	}

	protected AnlageInterface bindAnlage(String rmiName) {
		if (rmiName == null) {
			return null;
		}
		rmiName = rmiName + " " + getOrt();
		try {
			Registry registry = null;
			try {
				registry = LocateRegistry.createRegistry(1099);
				// This call will throw an exception if the registry does not already exist
			} catch (RemoteException e) {
				registry = LocateRegistry.getRegistry(1099);
			}
			return (AnlageInterface) registry.lookup(rmiName);
		} catch (Exception e) {
			logger.error("Mitarbeiter exception:");
			e.printStackTrace();
		}
		return null;
	}

	private String getOrt() {
		return ort;
	}

	public Resource nimmObjectVonAnlage(AnlageInterface anlage, Object optionalParameter) {
		try {
			Resource object = anlage.objectHolen(optionalParameter);
			logger.info("hole " + object + " mit param " + optionalParameter + " von " + anlage.getName());
			return object;
		} catch (RemoteException | InterruptedException e) {
			if (e instanceof ConnectException) {
				close = true;
			}
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public abstract void verarbeiteZutat();

	public boolean gibObjectAnAnlage(AnlageInterface anlage, Resource resource) {
		try {
			logger.info("gib " + resource + " an anlage. " + anlage.getName());
			return anlage.objectLiefern(resource);
		} catch (RemoteException | InterruptedException e) {
			if (e instanceof ConnectException) {
				close = true;
			}
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public Logger getLogger() {
		return logger;
	}

	public String getId() {
		return this.id;
	}

	public boolean checkInstance(Class<?> type, Object param) {
		if (param == null) {
			return false;
		}
		boolean returnValue = type == param.getClass();
		if (!returnValue) {
			logger.error("expected type. " + type + " got type: " + param.getClass());
		}
		return returnValue;
	}

	public void stop() {
		close = true;
	}
}
