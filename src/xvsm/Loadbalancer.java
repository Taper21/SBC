package xvsm;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.LindaCoordinator;
import org.mozartspaces.capi3.Transaction;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;

public class Loadbalancer {
	
	private static List<Integer> spaceport = new ArrayList<Integer>();
	private static HashMap<Integer,Integer> port_load = new HashMap<Integer, Integer>();
	boolean fehler = false;
	
	public Loadbalancer(){
		while(true){
			fehler=false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int summe=0;
			port_load = new HashMap<Integer,Integer>();
			for(Integer port: spaceport){
				int load = getLoad(port);
				if(load==-1){
					fehler=true;
				}
				port_load.put(port, load);
				summe += load;
			}
			if(spaceport.size()!=0 && !fehler){
				int avg = summe/spaceport.size();
				ArrayList<Integer> zuVielLoad = new ArrayList<Integer>();
				ArrayList<Integer> zuWenigLoad = new ArrayList<Integer>();
				for(Integer port : port_load.keySet()){
					if(port_load.get(port)<=(avg/2-1)){
						zuWenigLoad.add(port);
					}else if(port_load.get(port)>=(avg*2+1)){
						zuVielLoad.add(port);
					}
				}
				for(Integer von : zuVielLoad){
					for(Integer zu: zuWenigLoad){
						moveLoad(von, zu);
					}
				}
				
			}
		}
	}
	
	private void moveLoad(int von, int zu){
		Auftrag tmp = new Auftrag(null);
		tmp.setErledigt(0);
		Space.setPort(von);
		TransactionReference tx = null;
		try {
			
			//SUCHE UND BEARBEITE
			URI uri = new URI("xvsm://localhost:" + von);
			 tx = Space.getCapi().createTransaction(MzsConstants.TransactionTimeout.INFINITE,uri);
			List<Auftrag> alle = Space.getCapi().take(Space.createOrLookUpContainer(Standort.UNFERTIGE_AUFTRAEGE), LindaCoordinator.newSelector(tmp, 1), MzsConstants.RequestTimeout.TRY_ONCE, tx);
			Auftrag auftrag = alle.iterator().next();
			auftrag.setErledigt(zu*(-1));
			auftrag.setStatus(AuftragStatus.FERTIG);
			Entry entry = new Entry(auftrag,FifoCoordinator.newCoordinationData());
			Space.getCapi().write(entry, Space.createOrLookUpContainer(Standort.FERTIGE_AUFTRAEGE), 1000, tx);
			Space.getCapi().commitTransaction(tx);
			
			//FUEGE BEI NEUER FABRIK HINZU
			Space.setPort(zu);
			auftrag.setErledigt(0);
			auftrag.setStatus(AuftragStatus.UNFERTIG);
			entry = new Entry(auftrag,FifoCoordinator.newCoordinationData());
			Space.getCapi().write(entry, Space.createOrLookUpContainer(Standort.UNFERTIGE_AUFTRAEGE), 1000, null);
			Space.getCapi().commitTransaction(tx);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try{
				Space.getCapi().rollbackTransaction(tx);
			}catch(Exception e1){
				
			}
		}
	}
	
	private int getLoad(int port){
		Space.setPort(port);
		Auftrag tmp = new Auftrag(null);
		tmp.setErledigt(0);
		try {
			List<Auftrag> alle = Space.getCapi().read(Space.createOrLookUpContainer(Standort.UNFERTIGE_AUFTRAEGE), LindaCoordinator.newSelector(tmp, MzsConstants.Selecting.COUNT_ALL), MzsConstants.RequestTimeout.TRY_ONCE, null);
			return alle.size();
		} catch (MzsCoreException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(String s : args){
			spaceport.add(Integer.parseInt(s));
		}
		new Loadbalancer();
	}

}
