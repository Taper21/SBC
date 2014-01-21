package alternativ.anlagen;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import domain.IAuftrag;

import alternativ.domain.Auftraege;
import alternativ.domain.Auftrag;
import alternativ.domain.Charge;
import alternativ.domain.Packung;
import alternativ.domain.Resource;

public class AuftragAblage extends Anlage {

	Auftraege priorityList = new Auftraege(new ArrayList<Auftrag>());

	public static final String AUFTRAGABLAGE = "AuftragAblage";

	public AuftragAblage(String standort) {
		super(AUFTRAGABLAGE + " " + standort);
	}

	@Override
	public boolean objectLiefern(Resource t) throws RemoteException, InterruptedException {
		if (t instanceof Packung) {
			Packung p = (Packung) t;
			for (Auftrag x : priorityList.getPriority()) {
				
				System.out.println("auftrag in der ablage: " + x.getID() + " paket angekommen mit auftrag: " + p.getAuftragId());
				if (x.getID().equals(p.getUID())) {
					x.addErledigt();
					System.out.println("auftrag " + x.getUID() + " hat " + x.getErledigtePackungen());
				}
			}
		} else if(t instanceof Auftrag){
			Auftrag a = (Auftrag) t;
			System.out.println("Auftrag vom load balancer angekommen mit Status" + a.getStatus());
			a.setStatus("nicht angefangen");
			a.setBeendet(false);
			priorityList.getPriority().add(a);
		}
		return false;
	}

	@Override
	public Resource objectHolen(Object optionalParameter) throws RemoteException, InterruptedException {
		if (optionalParameter instanceof String) {
			Auftrag nichtAngefangenerAuftrag = priorityList.getNichtAngefangenerAuftrag();
			nichtAngefangenerAuftrag.setStatus("nach " + optionalParameter);
			nichtAngefangenerAuftrag.setBeendet(true);
			System.out.println("Loadbalancer holt sich: " + nichtAngefangenerAuftrag);
			return nichtAngefangenerAuftrag;
		}
		return priorityList;
	}

	@Override
	public Collection<Charge> getCharges() {
		return null;
	}

	public void submitAuftrag(int packungen, int normaleLebkuchen, int schokoLebkuchen, int nussLebkuchen) {
		Auftrag auftrag = new Auftrag(normaleLebkuchen, nussLebkuchen, schokoLebkuchen, packungen);
		priorityList.getPriority().add(auftrag);
		System.out.println("Neuer Auftrag, groeße ist nun " + priorityList.getPriority().size());
	}

	public List<IAuftrag> getAllAuftraege() {
		ArrayList<IAuftrag> returnValue = new ArrayList<IAuftrag>();
		for (Auftrag x : priorityList.getPriority()) {
			returnValue.add(x);
		}
		return returnValue;
	}

}
