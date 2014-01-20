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
	private static long verpackungsID =0;
	
	public Logistik(String id) throws InterruptedException{
		this.id = id;
		while(true){
			if(!tryVerpacke3Sorten()){
				if(!tryVerpacke2Sorten(LebkuchenSorte.SCHOKOLEBKUCHEN, LebkuchenSorte.NUSSLEBKUCHEN)){
					if(!tryVerpacke2Sorten(LebkuchenSorte.SCHOKOLEBKUCHEN, LebkuchenSorte.LEBKUCHEN)){
						if(!tryVerpacke2Sorten(LebkuchenSorte.NUSSLEBKUCHEN, LebkuchenSorte.LEBKUCHEN)){
							if(!tryVerpacke1Sorten(LebkuchenSorte.SCHOKOLEBKUCHEN)){
								if(!tryVerpacke1Sorten(LebkuchenSorte.NUSSLEBKUCHEN)){
									if(!tryVerpacke1Sorten(LebkuchenSorte.LEBKUCHEN)){
										Thread.sleep(500);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	
//	private void verpacke(){
//		TransactionReference tx =null;
//		try{
//			URI uri = new URI("xvsm://localhost:9876");
//			tx = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri );
//		List<Lebkuchen> charge = Space.getCapi().take(Space.createOrLookUpContainer(Standort.KONTROLLIERT), FifoCoordinator.newSelector(6), MzsConstants.RequestTimeout.TRY_ONCE, tx);
//		Standort status = Standort.VERPACKT;
//		List<Entry> entries = new ArrayList<Entry>();
//		for(Lebkuchen l: charge){
//			l.setLogistik(id);
//			l.setStatus(status.getName());
//			l.setVerpackungsID(verpackungsID);
//			entries.add(new Entry(l,FifoCoordinator.newCoordinationData()));
//		}
//		Space.getCapi().write(entries, Space.createOrLookUpContainer(status), MzsConstants.RequestTimeout.DEFAULT, tx);
//		Space.getCapi().commitTransaction(tx);
//		verpackungsID++;
//		}catch (Exception e) {
//			try{
//				Space.getCapi().rollbackTransaction(tx);
//			}catch (Exception e1) {
//				// TODO: handle exception
//			}
//		}
//	}
	
	private boolean tryVerpacke3Sorten(){
		TransactionReference tx =null;
		try{
			URI uri = new URI("xvsm://localhost:9876");
			tx = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri );
		Lebkuchen schoko = new Lebkuchen(LebkuchenSorte.SCHOKOLEBKUCHEN);
		Lebkuchen nuss = new Lebkuchen(LebkuchenSorte.NUSSLEBKUCHEN);
		Lebkuchen normal = new Lebkuchen(LebkuchenSorte.LEBKUCHEN);
		List<Lebkuchen> schokolebkuchen = Space.getCapi().take(Space.createOrLookUpContainer(Standort.KONTROLLIERT), LindaCoordinator.newSelector(schoko, 2), MzsConstants.RequestTimeout.TRY_ONCE, tx);
		List<Lebkuchen> nusslebkuchen = Space.getCapi().take(Space.createOrLookUpContainer(Standort.KONTROLLIERT), LindaCoordinator.newSelector(nuss, 2), MzsConstants.RequestTimeout.TRY_ONCE, tx);
		List<Lebkuchen> lebkuchen = Space.getCapi().take(Space.createOrLookUpContainer(Standort.KONTROLLIERT), LindaCoordinator.newSelector(normal, 2), MzsConstants.RequestTimeout.TRY_ONCE, tx);
		Standort status = Standort.VERPACKT;
		List<Entry> entries = new ArrayList<Entry>();
		List<Lebkuchen> alleLebkuchen = new ArrayList<Lebkuchen>();
		alleLebkuchen.addAll(schokolebkuchen);
		alleLebkuchen.addAll(nusslebkuchen);
		alleLebkuchen.addAll(lebkuchen);
		for(Lebkuchen l: alleLebkuchen){
			l.setLogistik(id);
			l.setStatus(status.getName());
			l.setVerpackungsID(verpackungsID);
			entries.add(new Entry(l,FifoCoordinator.newCoordinationData()));
		}
		Space.getCapi().write(entries, Space.createOrLookUpContainer(status), MzsConstants.RequestTimeout.DEFAULT, tx);
		Space.getCapi().commitTransaction(tx);
		verpackungsID++;
		return true;
		}catch (Exception e) {
			try{
				Space.getCapi().rollbackTransaction(tx);
			}catch (Exception e1) {
				// TODO: handle exception
			}
			return false;
		}
	}
	
	private boolean tryVerpacke2Sorten(LebkuchenSorte sorte1, LebkuchenSorte sorte2){
		TransactionReference tx =null;
		try{
			URI uri = new URI("xvsm://localhost:9876");
			tx = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri );
		Lebkuchen templatesorte1 = new Lebkuchen(sorte1);
		Lebkuchen templatesorte2 = new Lebkuchen(sorte2);
		List<Lebkuchen> alleSorte1 = Space.getCapi().take(Space.createOrLookUpContainer(Standort.KONTROLLIERT), LindaCoordinator.newSelector(templatesorte1, 3), MzsConstants.RequestTimeout.TRY_ONCE, tx);
		List<Lebkuchen> alleSorte2 = Space.getCapi().take(Space.createOrLookUpContainer(Standort.KONTROLLIERT), LindaCoordinator.newSelector(templatesorte2, 3), MzsConstants.RequestTimeout.TRY_ONCE, tx);
		Standort status = Standort.VERPACKT;
		List<Entry> entries = new ArrayList<Entry>();
		List<Lebkuchen> alleLebkuchen = new ArrayList<Lebkuchen>();
		alleLebkuchen.addAll(alleSorte1);
		alleLebkuchen.addAll(alleSorte2);
		for(Lebkuchen l: alleLebkuchen){
			l.setLogistik(id);
			l.setStatus(status.getName());
			l.setVerpackungsID(verpackungsID);
			entries.add(new Entry(l,FifoCoordinator.newCoordinationData()));
		}
		Space.getCapi().write(entries, Space.createOrLookUpContainer(status), MzsConstants.RequestTimeout.DEFAULT, tx);
		Space.getCapi().commitTransaction(tx);
		verpackungsID++;
		return true;
		}catch (Exception e) {
			try{
				Space.getCapi().rollbackTransaction(tx);
			}catch (Exception e1) {
				// TODO: handle exception
			}
			return false;
		}
	}
	
	private boolean tryVerpacke1Sorten(LebkuchenSorte sorte){
		TransactionReference tx =null;
		try{
			URI uri = new URI("xvsm://localhost:9876");
			tx = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri );
		Lebkuchen templatesorte1 = new Lebkuchen(sorte);
		List<Lebkuchen> alleSorte1 = Space.getCapi().take(Space.createOrLookUpContainer(Standort.KONTROLLIERT), LindaCoordinator.newSelector(templatesorte1, 6), MzsConstants.RequestTimeout.TRY_ONCE, tx);
		Standort status = Standort.VERPACKT;
		List<Entry> entries = new ArrayList<Entry>();
		for(Lebkuchen l: alleSorte1){
			l.setLogistik(id);
			l.setStatus(status.getName());
			l.setVerpackungsID(verpackungsID);
			entries.add(new Entry(l,FifoCoordinator.newCoordinationData()));
		}
		Space.getCapi().write(entries, Space.createOrLookUpContainer(status), MzsConstants.RequestTimeout.DEFAULT, tx);
		Space.getCapi().commitTransaction(tx);
		verpackungsID++;
		return true;
		}catch (Exception e) {
			try{
				Space.getCapi().rollbackTransaction(tx);
			}catch (Exception e1) {
				// TODO: handle exception
			}
			return false;
		}
	}


	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		new Logistik(args[0]);
//		new Logistik("L");

	}

}
