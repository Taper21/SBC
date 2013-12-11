package alternativ.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Itestserver extends Remote{
	
	public int getInt(boolean caller) throws RemoteException;
}
