package alternativ.anlagen;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import domain.IAuftrag;

import alternativ.domain.Auftraege;
import alternativ.domain.Auftrag;
import alternativ.domain.Charge;
import alternativ.domain.Resource;

public class AuftragAblage extends Anlage {
	
	Auftraege priorityList = new Auftraege(new ArrayList<Auftrag>());
	
	public static final String AUFTRAGABLAGE = "AuftragAblage";

	public AuftragAblage() {
		super(AUFTRAGABLAGE);
	}

	@Override
	public boolean objectLiefern(Resource t) throws RemoteException, InterruptedException {
		for(Auftrag x:priorityList.getPriority()){
			System.out.println("auftrag in der ablage: "+ x.getID() + " auftrag vom mitarbeiter " + t.getUID());
			if(x.getID().equals(t.getUID())){
				x.addErledigt();
				System.out.println("auftrag " + x.getUID() + " hat " + x.getErledigtePackungen());
			}
		}
		return false;
	}

	@Override
	public Resource objectHolen(Object optionalParameter) throws RemoteException, InterruptedException {
		return priorityList;
	}

	@Override
	public Collection<Charge> getCharges() {
		return null;
	}

	public void submitAuftrag(int packungen, int normaleLebkuchen, int schokoLebkuchen, int nussLebkuchen) {
		Auftrag auftrag = new Auftrag(normaleLebkuchen, nussLebkuchen, schokoLebkuchen, packungen);
		priorityList.getPriority().add(auftrag);
		System.out.println("Neuer Auftrag, groeße ist nun "+ priorityList.getPriority().size());
	}

	public List<IAuftrag> getAllAuftraege() {
		ArrayList<IAuftrag> returnValue = new ArrayList<IAuftrag>();
		for(Auftrag x:priorityList.getPriority()){
			returnValue.add(x);
		}
		return returnValue;
	}

}
