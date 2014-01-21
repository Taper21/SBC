package alternativ.anlagen;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import alternativ.domain.Auftraege;
import alternativ.domain.Auftrag;
import alternativ.domain.Resource;

public class LoadBalancer {
	private static ArrayList<AnlageInterface> auftragAblagen = new ArrayList<AnlageInterface>();
	private static HashMap<AnlageInterface, List<Auftrag>> auftraegeProAnlage = new HashMap<AnlageInterface, List<Auftrag>>();

	public static void main(String[] args) throws RemoteException, InterruptedException {
		int length = args.length;
		System.out.println("Anzahl an Standorten: " + length);
		connectToAuftragAblage(args);

		while (true) {
			System.out.println("--------------------");
			Thread.sleep(4000);
			auftraegeProAnlage = new HashMap<AnlageInterface, List<Auftrag>>();
			float durchschnitt = calculateNichtAngefangeneAuftraegeDurchschnitt();
			System.out.println(durchschnitt);
			balance(durchschnitt);
		}
	}

	private static void balance(float durchschnitt) throws RemoteException, InterruptedException {
		Comparator<Entry<AnlageInterface, List<Auftrag>>> comp = new Comparator<Entry<AnlageInterface, List<Auftrag>>>() {

			@Override
			public int compare(Entry<AnlageInterface, List<Auftrag>> o1, Entry<AnlageInterface, List<Auftrag>> o2) {
				return o1.getValue().size() - o2.getValue().size();
			}
		};
		Entry<AnlageInterface, List<Auftrag>> max = Collections.max(auftraegeProAnlage.entrySet(), comp);
		Entry<AnlageInterface, List<Auftrag>> min = Collections.min(auftraegeProAnlage.entrySet(), comp);
		if (max.getValue().size() - min.getValue().size() > durchschnitt/2 && max.getValue().size() > 2) {
			move(max, min);
		}
	}

	private static void move(Entry<AnlageInterface, List<Auftrag>> max, Entry<AnlageInterface, List<Auftrag>> min)
			throws RemoteException, InterruptedException {
		Resource nichtAngefangenerAuftrag = max.getKey().objectHolen(min.getKey().getName());
		System.out.println("tried to move " + nichtAngefangenerAuftrag + " from  " + max.getKey().getName() + " to: "
				+ min.getKey().getName());
		if (nichtAngefangenerAuftrag != null) {
			min.getKey().objectLiefern(nichtAngefangenerAuftrag);
		}
	}

	private static float calculateNichtAngefangeneAuftraegeDurchschnitt() throws RemoteException, InterruptedException {
		int summeOffen = 0;
		int size = auftragAblagen.size();
		if (size == 0) {
			return 0;
		}
		for (AnlageInterface auftragAblage : auftragAblagen) {
			Auftraege auftraege = (Auftraege) auftragAblage.objectHolen(null);
			List<Auftrag> nichtAngefangen = auftraege.getNichtAngefangeneAuftraege();
			int size2 = nichtAngefangen.size();
			System.out.println("offenProFabrik: " + size2);
			summeOffen += size2;
			auftraegeProAnlage.put(auftragAblage, nichtAngefangen);
		}
		System.out.println(summeOffen);
		System.out.println(size);
		return (float) summeOffen / size;
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
				auftragAblagen.add(anlage);
				System.out.println("connected to " + anlage.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
