package xvsm;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.FifoCoordinator.FifoSelector;
import org.mozartspaces.capi3.IsolationLevel;
import org.mozartspaces.capi3.LindaCoordinator;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;

import domain.ZutatTypEnum;

import xvsm.Lebkuchen;


public class Baecker {
	
	private String id;
	private long chargeID = 0;
	private ZutatXVSMImpl nextExtraZutat = null;
	private boolean nextExtraGewaehlt = false;
	private TransactionReference schokoTransaction;
	private TransactionReference nussTransaction;
	
	
	List<Lebkuchen> charge;
	
	
	public Baecker(String id, int port){
		this.id = id;
		Space.setPort(port);
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
		new Baecker(args[0], Integer.parseInt(args[1]));
	}
	
	private void fertigen(){
		TransactionReference tx = null;
		try {
			//Lebkuchen fertigen
			charge = entnehmeZutatenFuerCharge();
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
	
	private ZutatXVSMImpl getExtraZutat(){
		if(!nextExtraGewaehlt){
			ArrayList<ZutatXVSMImpl> moeglichOptionen = new ArrayList<ZutatXVSMImpl>();
			moeglichOptionen.add(null);
			ZutatXVSMImpl schoko = tryExtraZutat(Standort.SCHOKOLADENLAGER);
			ZutatXVSMImpl nuss = tryExtraZutat(Standort.NUESSELAGER);
			if(schoko != null){
				moeglichOptionen.add(schoko);
			}
			if(nuss!=null){
				moeglichOptionen.add(nuss);
			}
			int randomindex = new Random().nextInt(moeglichOptionen.size());
			nextExtraZutat = moeglichOptionen.get(randomindex);
			if(nextExtraZutat!=null){
				if(nextExtraZutat.getZutatTypEnum().equals(ZutatTypEnum.SCHOKOLADE)){
					try {
						Space.getCapi().commitTransaction(schokoTransaction);
					} catch (MzsCoreException e) {
						e.printStackTrace();
					}
				}else if(nextExtraZutat.getZutatTypEnum().equals(ZutatTypEnum.NUESSE)){
					try {
						Space.getCapi().commitTransaction(nussTransaction);
					} catch (MzsCoreException e) {
						e.printStackTrace();
					}
				}
			nextExtraGewaehlt=true;
			moeglichOptionen.remove(randomindex);
			for(ZutatXVSMImpl z: moeglichOptionen){
				if(z!=null){
					if(z.getZutatTypEnum().equals(ZutatTypEnum.SCHOKOLADE)){
						try {
							Space.getCapi().rollbackTransaction(schokoTransaction);
						} catch (MzsCoreException e) {
							e.printStackTrace();
						}
					}else if(z.getZutatTypEnum().equals(ZutatTypEnum.NUESSE)){
						try {
							Space.getCapi().rollbackTransaction(nussTransaction);
						} catch (MzsCoreException e) {
							e.printStackTrace();
						}
					}
				}
			}}
		}
		return nextExtraZutat;
	}
	
	private ZutatXVSMImpl tryExtraZutat(Standort vonLager){
		TransactionReference tez =null;
		try{
		URI uri = new URI("xvsm://localhost:" + Space.getPort());
		if(vonLager == Standort.NUESSELAGER){
			nussTransaction = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri );
			tez = nussTransaction;
		}else{
			schokoTransaction = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri );
			tez = schokoTransaction;
		}
		 List<FifoSelector> selector = new ArrayList<FifoSelector>();
		 selector.add(FifoCoordinator.newSelector(1));
		 List<ZutatXVSMImpl> alleSchoko = Space.getCapi().take(Space.createOrLookUpContainer(vonLager), selector, MzsConstants.RequestTimeout.TRY_ONCE, tez, IsolationLevel.REPEATABLE_READ,null);
		 ZutatXVSMImpl schokolade = alleSchoko.iterator().next();
		 return schokolade;
		}catch(Exception e){
			try {
				Space.getCapi().rollbackTransaction(tez);
			} catch (MzsCoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}

	
	private Lebkuchen entnehmenZutatenFuer1Lebkuchen(){
		TransactionReference tx =null;
		try{
		URI uri = new URI("xvsm://localhost:" + Space.getPort());
		 tx = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri);
		Lebkuchen lebkuchen =null;
		List<FifoSelector> selector = new ArrayList<FifoSelector>();
		selector.add(FifoCoordinator.newSelector(1));
			List<ZutatXVSMImpl> allehonig = Space.getCapi().take(Space.createOrLookUpContainer(Standort.HONIGLAGER), selector, MzsConstants.RequestTimeout.TRY_ONCE, tx, IsolationLevel.REPEATABLE_READ,null);
			List<ZutatXVSMImpl> allemehl = Space.getCapi().take(Space.createOrLookUpContainer(Standort.MEHLLAGER), selector, MzsConstants.RequestTimeout.TRY_ONCE, tx, IsolationLevel.REPEATABLE_READ,null);
			List<ZutatXVSMImpl> alleEier1 = Space.getCapi().take(Space.createOrLookUpContainer(Standort.EIERLAGER), selector, MzsConstants.RequestTimeout.TRY_ONCE, tx, IsolationLevel.REPEATABLE_READ,null);
			List<ZutatXVSMImpl> alleEier2 = Space.getCapi().take(Space.createOrLookUpContainer(Standort.EIERLAGER), selector, MzsConstants.RequestTimeout.TRY_ONCE, tx, IsolationLevel.REPEATABLE_READ,null);
			ZutatXVSMImpl honig = allehonig.iterator().next();
			ZutatXVSMImpl mehl = allemehl.iterator().next();
			ZutatXVSMImpl ei1 = alleEier1.iterator().next();
			ZutatXVSMImpl ei2 = alleEier2.iterator().next();
			lebkuchen = new Lebkuchen(honig, mehl, ei1, ei2, chargeID, id);
			ZutatXVSMImpl extra = getExtraZutat();
			if(extra != null){
				switch(extra.getZutatTypEnum()){
					case NUESSE: lebkuchen.setNuss(extra);
								 lebkuchen.setSorte(LebkuchenSorte.NUSSLEBKUCHEN);
						break;
					case SCHOKOLADE: lebkuchen.setSchoko(extra);
									 lebkuchen.setSorte(LebkuchenSorte.SCHOKOLEBKUCHEN);
						break;
				}
			}else{
				lebkuchen.setSorte(LebkuchenSorte.LEBKUCHEN);
			}
			long zubereitungszeit = ((new Random().nextLong())%1000)+1000;
			Thread.sleep(zubereitungszeit);
			Space.getCapi().write(Space.createOrLookUpContainer(Standort.LEBKUCHEN_GEFERTIGT), 1000, tx,new Entry(lebkuchen,LindaCoordinator.newCoordinationData()));
			Space.getCapi().commitTransaction(tx);
			nextExtraGewaehlt = false;
			nextExtraZutat=null;
			return lebkuchen;
		}catch(Exception e){
			try {
				Space.getCapi().rollbackTransaction(tx);
			} catch (MzsCoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}finally{
			try {
				Space.getCapi().rollbackTransaction(nussTransaction);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Space.getCapi().rollbackTransaction(schokoTransaction);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private void backeCharge(){
		boolean gebacken = false;
		TransactionReference tx=null;
		while(!gebacken){
			try{
				URI uri = new URI("xvsm://localhost:" + Space.getPort()); 
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
