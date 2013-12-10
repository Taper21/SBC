package xvsm;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.LindaCoordinator;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.TransactionReference;

public class Logistik {
	
	private String id;
	private static int verpackungsID =0;
	
	public Logistik(String id){
		this.id = id;
		while(true){
			verpacke();
		}
	}
	
	private void verpacke(){
		TransactionReference tx =null;
		try{
			URI uri = new URI("xvsm://localhost:9876");
			tx = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri );
		List<Lebkuchen> charge = Space.getCapi().take(Space.createOrLookUpContainer(Standort.KONTROLLIERT), FifoCoordinator.newSelector(6), MzsConstants.RequestTimeout.TRY_ONCE, tx);
		Standort status = Standort.VERPACKT;
		List<Entry> entries = new ArrayList<Entry>();
		for(Lebkuchen l: charge){
			l.setLogistik(id);
			l.setStatus(status.getName());
			l.setVerpackungsID(verpackungsID);
			entries.add(new Entry(l,FifoCoordinator.newCoordinationData()));
		}
		Space.getCapi().write(entries, Space.createOrLookUpContainer(status), MzsConstants.RequestTimeout.DEFAULT, tx);
		Space.getCapi().commitTransaction(tx);
		verpackungsID++;
		}catch (Exception e) {
			try{
				Space.getCapi().rollbackTransaction(tx);
			}catch (Exception e1) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		new Logistik(args[0]);
		new Logistik("L");

	}

}
