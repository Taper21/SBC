package alternativ.mitarbeiter;


import java.rmi.RemoteException;
import java.rmi.server.UID;

import domain.ZutatTypEnum;

import alternativ.anlagen.Ofen;
import alternativ.anlagen.Qualitaetskontrolle;
import alternativ.anlagen.ZutatenLager;
import alternativ.domain.Charge;
import alternativ.domain.Lebkuchen;
import alternativ.domain.Resource;

public class Bäcker extends Mitarbeiter {



	private static final int BACKZEIT = 10000;
	private Resource honig = null;
	private Resource mehl = null;
	private Resource ei1 = null;
	private Resource ei2 = null;
	private Charge charge = new Charge();
	private boolean lagerWirktLeer;
	private boolean warteAufCharge;
	private UID chargeZumAbholen;;

	public Bäcker(String id){
		super(ZutatenLager.ZUTATEN_LAGER, Ofen.OFEN, Qualitaetskontrolle.QUALITAETSKONTROLLE,id);
	}
	
	
	public static void main(String[] args) {
		Bäcker bäcker = new Bäcker(args[0]);
		bäcker.getLogger().info("Bäcker started mit id: " + bäcker.getId());
		for(;;){
			bäcker.getResources();
//			bäcker.getLogger().info("Bäcker " + bäcker.getId() + "hat alle Zutaten");
			bäcker.teigMischen();
//			bäcker.getLogger().info("Bäcker " + bäcker.getId() + "mischt einen Lebkuchen ");
			bäcker.versucheZuBacken();
			bäcker.holeChargeVonOfen();
		}
	}




	private void holeChargeVonOfen() {
		Charge fertigeLebkuchen;
		if(warteAufCharge){
			try {
				Thread.sleep(BACKZEIT);
				warteAufCharge = false;
				try {
					fertigeLebkuchen = (Charge) ziel.objectHolen(chargeZumAbholen);
					if(fertigeLebkuchen != null){
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


	private void versucheZuBacken() {
		if(charge.isVoll()){
			backen();
		}else if(lagerWirktLeer && !charge.isLeer()){
			backen();
		}
	}


	private void backen() {
		if(gibZutatAb(charge)){
			chargeZumAbholen = charge.getUID();
			charge=new Charge();
			lagerWirktLeer = false;
			warteAufCharge = true;
		}
	}


	private void teigMischen() {
		if(!lagerWirktLeer){
			if(charge.add(new Lebkuchen(Lebkuchen.Status.GEFERTIGT, charge.getUID(), honig, mehl, ei1, ei2))){
				honig = null;
				mehl = null;
				ei1 = null;
				ei2 = null;
			}
		}
	}

	private void getResources(){
		if(honig==null){
			honig = besorgeZutat(ZutatTypEnum.HONIG);
		}
		if(mehl==null){
			mehl = besorgeZutat(ZutatTypEnum.MEHL);
		}
		if(ei1==null){
			ei1 = besorgeZutat(ZutatTypEnum.EI);
		}
		if(ei2==null){
			ei2 = besorgeZutat(ZutatTypEnum.EI);
		}
		if(null == honig || null == mehl || null == ei1 || null == ei2){
			lagerWirktLeer = true;
		}else {
			lagerWirktLeer = false;
		}
	}

	@Override
	public void verarbeiteZutat() {
		// TODO Auto-generated method stub

	}

}
