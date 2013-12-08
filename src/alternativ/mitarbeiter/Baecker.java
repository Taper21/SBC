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
	private boolean lagerWirktLeer;
	private boolean warteAufCharge;
	private UID chargeZumAbholen;;

	public Baecker(String id){
		super(ZutatenLager.ZUTATEN_LAGER, Ofen.OFEN, Qualitaetskontrolle.QUALITAETSKONTROLLE,id);
	}
	
	
	public static void main(String[] args) {
		Baecker baecker = new Baecker(args[0]);
		baecker.getLogger().info("Bäcker started mit id: " + baecker.getId());
		for(;;){
			baecker.getResources();
//			bäcker.getLogger().info("Bäcker " + bäcker.getId() + "hat alle Zutaten");
			baecker.teigMischen();
//			bäcker.getLogger().info("Bäcker " + bäcker.getId() + "mischt einen Lebkuchen ");
			baecker.versucheZuBacken();
			baecker.holeChargeVonOfen();
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
			if(charge.add(new Lebkuchen(Lebkuchen.Status.GEFERTIGT, charge.getUID(), honig, mehl, ei1, ei2, getId()))){
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
