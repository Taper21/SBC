package alternativ.mitarbeiter;


import java.rmi.RemoteException;
import java.rmi.server.UID;

import domain.ILebkuchen;
import domain.ZutatTypEnum;

import alternativ.anlagen.Ofen;
import alternativ.anlagen.Qualitaetskontrolle;
import alternativ.anlagen.ZutatenLager;
import alternativ.domain.Charge;
import alternativ.domain.Lebkuchen;
import alternativ.domain.Resource;

public class Baecker extends Mitarbeiter {



	private static final int BACKZEIT = 10000;
	private Resource honig = null;
	private Resource mehl = null;
	private Resource ei1 = null;
	private Resource ei2 = null;
	private Charge charge = new Charge();
	private boolean warteAufCharge;
	private String chargeZumAbholen;;

	public Baecker(String id){
		super(ZutatenLager.ZUTATEN_LAGER, Ofen.OFEN, Qualitaetskontrolle.QUALITAETSKONTROLLE,id);
	}
	
	
	public static void main(String[] args) {
		Baecker baecker = new Baecker(args[0]);
		baecker.getLogger().info("Bäcker started mit id: " + baecker.getId());
		while(!baecker.close){
			baecker.getResources();
			baecker.teigMischen();
			baecker.backen();
			baecker.holeChargeVonOfen();
		}
	}




	private void holeChargeVonOfen() {
		Charge fertigeLebkuchen;
		if(warteAufCharge){
			try {
				Thread.sleep(BACKZEIT);
				logger.info("warte auf charge:" + chargeZumAbholen);
				warteAufCharge = false;
				try {
					fertigeLebkuchen = (Charge) ziel.objectHolen(getId());
					logger.info("hole lebkuchen "+ fertigeLebkuchen.getUID() + " b: " + fertigeLebkuchen.getBaeckerId() );
					if(fertigeLebkuchen != null){
						fertigeLebkuchen.setStatusOfLebkuchen(Lebkuchen.Status.GEBACKEN);
						weiteresZiel.objectLiefern(fertigeLebkuchen);
						logger.info("Charge fertig gebacken: " + fertigeLebkuchen.getUID() );
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}




	private void backen() {
			if(gibZutatAb(charge)){
				chargeZumAbholen = charge.getUID();
				charge=new Charge();
				warteAufCharge = true;
			} else {
				
			}
	}


	private void teigMischen() {
			if(charge.add(new Lebkuchen(Lebkuchen.Status.GEFERTIGT, charge.getUID(), honig, mehl, ei1, ei2, getId()))){
				honig = null;
				mehl = null;
				ei1 = null;
				ei2 = null;
		}
	}

	private void getResources(){
		if(honig == null && mehl ==null && ei1==null && ei2==null){
			honig = besorgeZutat(ZutatTypEnum.HONIG);
			mehl = besorgeZutat(ZutatTypEnum.MEHL);
			ei1 = besorgeZutat(ZutatTypEnum.EI);
			ei2 = besorgeZutat(ZutatTypEnum.EI);
		}
	}

	@Override
	public void verarbeiteZutat() {
		// TODO Auto-generated method stub

	}

}
