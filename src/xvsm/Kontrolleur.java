package xvsm;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.mozartspaces.capi3.Coordinator;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.FifoCoordinator.FifoSelector;
import org.mozartspaces.capi3.IsolationLevel;
import org.mozartspaces.capi3.LindaCoordinator;
import org.mozartspaces.capi3.Selector;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsConstants.TransactionTimeout;
import org.mozartspaces.core.TransactionReference;
import org.mozartspaces.xvsmp.util.PredefinedSelectorCreators.FifoSelectorCreator;

public class Kontrolleur {

	private String id;
	private Long rate;
	private static long counter = 1;
	
	public Kontrolleur(String id, Long rate, int port){
		this.id = id;
		this.rate = rate;
		Space.setPort(port);
		while(true){
			kontrolliere();
		}
	}
	
	private void kontrolliere(){
		TransactionReference tx =null;
		try{
			URI uri = new URI("xvsm://localhost:"+ Space.getPort());
			tx = Space.getCapi().createTransaction(500,uri );
			List<FifoSelector> selector = new ArrayList<FifoSelector>();
			selector.add(FifoCoordinator.newSelector(1));
			//Space.getCapi().read(Space.createOrLookUpContainer(Standort.GEBACKEN), selector, MzsConstants.RequestTimeout.DEFAULT, tx, IsolationLevel.REPEATABLE_READ, null);
			Iterator<Serializable> it = Space.getCapi().read(Space.createOrLookUpContainer(Standort.GEBACKEN), FifoCoordinator.newSelector(1), MzsConstants.RequestTimeout.TRY_ONCE, tx).iterator();
			if(it.hasNext()){
				Lebkuchen template = (Lebkuchen) it.next();
				template.setSorte(null);
				List<Lebkuchen> charge = Space.getCapi().take(Space.createOrLookUpContainer(Standort.GEBACKEN), LindaCoordinator.newSelector(template, MzsConstants.Selecting.COUNT_ALL), MzsConstants.RequestTimeout.TRY_ONCE, tx);
				for(Lebkuchen l: charge){
					l.setKontrolleurID(id);
				}
				Standort status = Standort.KONTROLLIERT;
				if(counter%2==0){
				Lebkuchen kostprobe =charge.remove(0);
				kostprobe.setStatus("Verkostet");
				Space.getCapi().write(Space.createOrLookUpContainer(Standort.VERKOSTET), MzsConstants.RequestTimeout.DEFAULT, tx, new Entry(kostprobe,FifoCoordinator.newCoordinationData()));
				status = Standort.KONTROLLIERT;
				if(!isKontrolleOK()){
					status = Standort.ENTSORGT;
				}
				}
				List<Entry> entries = new ArrayList<Entry>();
				for(Lebkuchen l : charge){
					l.setStatus(status.getName());
					entries.add(new Entry(l, LindaCoordinator.newCoordinationData()));
				}
				Space.getCapi().write(entries, Space.createOrLookUpContainer(status), MzsConstants.RequestTimeout.DEFAULT, tx);
				Space.getCapi().commitTransaction(tx);
				counter++;
			}
		}catch(Exception e){
			try{
				Space.getCapi().rollbackTransaction(tx);
				Thread.sleep(1000);
			}catch (Exception e1) {
				// TODO: handle exception
			}
		}
	}
	
	private boolean isKontrolleOK(){
		return (Math.abs((new Random().nextLong()%100))>=rate);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
//		new Kontrolleur("1", Long.parseLong("2"));
		new Kontrolleur(args[0], Long.parseLong(args[1]),Integer.parseInt(args[2]));
		}catch(Exception e){
			System.out.println("Bitte uebergeben Sie die Arumente 'id' 'rate'");
		}
	}

}
