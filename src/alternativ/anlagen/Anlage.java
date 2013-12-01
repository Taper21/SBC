package alternativ.anlagen;

import java.rmi.Remote;
import java.rmi.RemoteException;

import alternativ.domain.Resource;


public interface Anlage extends Remote {
	void objectLiefern(Resource t) throws RemoteException;
	Resource objectHolen(Object optionalParameter) throws RemoteException;
}
