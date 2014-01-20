package alternativ.anlagen;

import java.rmi.Remote;
import java.rmi.RemoteException;

import alternativ.domain.Resource;

public interface AnlageInterface extends Remote {

	public boolean objectLiefern(Resource t) throws RemoteException, InterruptedException;
	public Resource objectHolen(Object optionalParameter) throws RemoteException, InterruptedException;
	public String getName() throws RemoteException, InterruptedException;
}
