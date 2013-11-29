package alternativ.anlagen.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Anlage extends Remote {
	<T> T objectLiefern(T t) throws RemoteException;
}
