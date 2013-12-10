package xvsm;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.FifoCoordinator.FifoSelector;
import org.mozartspaces.capi3.LindaCoordinator;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;

import xvsm.Lebkuchen;


public class Baecker {
	
	private String id;
	private long chargeID = 0;
	
	List<Lebkuchen> charge;
	
	
	public Baecker(String id){
		this.id = id;
		
		while(true){
			fertigen();
			backeCharge();
			chargeID++;
			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Baecker(args[0]);
	}
	
	private void fertigen(){
		TransactionReference tx = null;
		try {
			//Lebkuchen fertigen
//			URI uri = new URI("xvsm://localhost:9876");
//			tx = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri );
			charge = entnehmeZutatenFuerCharge();
//			ArrayList<Entry> entries = new ArrayList<Entry>();
//			for(Lebkuchen l : charge){
//				entries.add(new Entry(l, LindaCoordinator.newCoordinationData()));
//			}
//			Entry[] entryArray = entries.toArray(new Entry[1]);
//			Space.getCapi().write(Space.createOrLookUpContainer(Standort.LEBKUCHEN_GEFERTIGT), 1000, tx,entryArray);
//			Space.getCapi().commitTransaction(tx);
			System.out.println("Charge "+ chargeID + " gefertig");
			for(Lebkuchen l :charge){
				System.out.println(l.toString());
			}
		} catch (Exception e) {
			try {
				Space.getCapi().rollbackTransaction(tx);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	private List<Lebkuchen> entnehmeZutatenFuerCharge(){
		List<Lebkuchen> charge = new ArrayList<Lebkuchen>();
		int anzahl = 0;
		Lebkuchen current =null;
		while(current == null){
			current = entnehmenZutatenFuer1Lebkuchen();
		}
		charge.add(current);
		anzahl++;
		while (anzahl < 5 && (current=entnehmenZutatenFuer1Lebkuchen())!=null){
			charge.add(current);
			anzahl++;
		}
		return charge;
		
	}
	
	private Lebkuchen entnehmenZutatenFuer1Lebkuchen(){
		TransactionReference tx =null;
		try{
		URI uri = new URI("xvsm://localhost:9876");
		 tx = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri );
		Lebkuchen lebkuchen =null;
			List<ZutatXVSMImpl> allehonig = Space.getCapi().take(Space.createOrLookUpContainer(Standort.HONIGLAGER), FifoCoordinator.newSelector(1), MzsConstants.RequestTimeout.TRY_ONCE, tx);
			List<ZutatXVSMImpl> allemehl = Space.getCapi().take(Space.createOrLookUpContainer(Standort.MEHLLAGER), FifoCoordinator.newSelector(1), MzsConstants.RequestTimeout.TRY_ONCE, tx);
			List<ZutatXVSMImpl> alleEier1 = Space.getCapi().take(Space.createOrLookUpContainer(Standort.EIERLAGER), FifoCoordinator.newSelector(1), MzsConstants.RequestTimeout.TRY_ONCE, tx);
			List<ZutatXVSMImpl> alleEier2 = Space.getCapi().take(Space.createOrLookUpContainer(Standort.EIERLAGER), FifoCoordinator.newSelector(1), MzsConstants.RequestTimeout.TRY_ONCE, tx);
			ZutatXVSMImpl honig = allehonig.iterator().next();
			ZutatXVSMImpl mehl = allemehl.iterator().next();
			ZutatXVSMImpl ei1 = alleEier1.iterator().next();
			ZutatXVSMImpl ei2 = alleEier2.iterator().next();
			lebkuchen = new Lebkuchen(honig, mehl, ei1, ei2, chargeID, id);
			long zubereitungszeit = ((new Random().nextLong())%1000)+1000;
			Thread.sleep(zubereitungszeit);
			Space.getCapi().write(Space.createOrLookUpContainer(Standort.LEBKUCHEN_GEFERTIGT), 1000, tx,new Entry(lebkuchen,LindaCoordinator.newCoordinationData()));
			Space.getCapi().commitTransaction(tx);
			return lebkuchen;
		}catch(Exception e){
			try {
				Space.getCapi().rollbackTransaction(tx);
			} catch (MzsCoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}
	
	private void backeCharge(){
		boolean gebacken = false;
		TransactionReference tx=null;
		while(!gebacken){
			try{
				URI uri = new URI("xvsm://localhost:9876"); 
				while((10-Space.getCapi().read(Space.createOrLookUpContainer(Standort.OFEN), FifoCoordinator.newSelector(FifoSelector.COUNT_MAX), MzsConstants.RequestTimeout.INFINITE, null).size())<charge.size()){
					Thread.sleep(200);
				}
				tx = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri);
				Space.getCapi().write(chargeToEntryList(), Space.createOrLookUpContainer(Standort.OFEN), MzsConstants.RequestTimeout.DEFAULT, tx);
				Lebkuchen template = new Lebkuchen(this.chargeID,this.id);
				int d = Space.getCapi().delete(Space.createOrLookUpContainer(Standort.LEBKUCHEN_GEFERTIGT), Arrays.asList(LindaCoordinator.newSelector(template,MzsConstants.Selecting.COUNT_ALL)), MzsConstants.RequestTimeout.TRY_ONCE, tx);
				System.out.println("Deletions : " + d);
				Space.getCapi().commitTransaction(tx);
				tx = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri);
				Thread.sleep(10000);
				Space.getCapi().write(chargeToEntryList(), Space.createOrLookUpContainer(Standort.GEBACKEN), MzsConstants.RequestTimeout.TRY_ONCE, tx);
				d = Space.getCapi().delete(Space.createOrLookUpContainer(Standort.OFEN), Arrays.asList(LindaCoordinator.newSelector(template, MzsConstants.Selecting.COUNT_ALL)), MzsConstants.RequestTimeout.TRY_ONCE, tx);
				System.out.println("Deletions : " + d);
				Space.getCapi().commitTransaction(tx);
				gebacken=true;
			} catch (Exception e) {
				try {
					Space.getCapi().rollbackTransaction(tx);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}
	
	private List<Entry> chargeToEntryList(){
		ArrayList<Entry> entries = new ArrayList<Entry>();
		for(Lebkuchen l : charge){
			entries.add(new Entry(l, LindaCoordinator.newCoordinationData()));
		}
		return entries;
	}

}
