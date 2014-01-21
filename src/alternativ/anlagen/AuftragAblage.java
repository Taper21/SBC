package alternativ.anlagen;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import alternativ.domain.Auftraege;
import alternativ.domain.Auftrag;
import alternativ.domain.Charge;
import alternativ.domain.Resource;

public class AuftragAblage extends Anlage {
	
	ArrayList<Auftrag> priorityList = new ArrayList<Auftrag>();
	
	public static final String AUFTRAGABLAGE = "AuftragAblage";

	public AuftragAblage() {
		super(AUFTRAGABLAGE);
	}

	@Override
	public boolean objectLiefern(Resource t) throws RemoteException, InterruptedException {
		for(Auftrag x:priorityList){
			if(x.getID().equals(t.getUID())){
				x.addErledigt(1);
			}
		}
		return false;
	}

	@Override
	public Resource objectHolen(Object optionalParameter) throws RemoteException, InterruptedException {
		return new Auftraege(priorityList);
	}

	@Override
	public Collection<Charge> getCharges() {
		return null;
	}

	public void submitAuftrag(int packungen, int normaleLebkuchen, int schokoLebkuchen, int nussLebkuchen) {
		Auftrag auftrag = new Auftrag(normaleLebkuchen, nussLebkuchen, schokoLebkuchen, packungen);
		priorityList.add(auftrag);
		System.out.println("Neuer Auftrag, groeße ist nun "+ priorityList.size());
	}

}
