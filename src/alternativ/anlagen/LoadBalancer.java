package alternativ.anlagen;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoadBalancer {
	public static void main(String[] args) {
		int length = args.length;
		System.out.println("Anzahl an Standorten: " + length);

		connectToAuftragAblage(args);

	}

	private static void connectToAuftragAblage(String[] args) {
		for (String ort : args) {
			String rmiName = AuftragAblage.AUFTRAGABLAGE + " " + ort;
			try {
				Registry registry = null;
				try {
					registry = LocateRegistry.createRegistry(1099);
					// This call will throw an exception if the registry does not already exist
				} catch (RemoteException e) {
					registry = LocateRegistry.getRegistry(1099);
				}
				AnlageInterface anlage = (AnlageInterface) registry.lookup(rmiName);
				System.out.println("connected to " + anlage.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
