package alternativ.mitarbeiter;


import java.rmi.RemoteException;
import java.rmi.server.UID;

import org.slf4j.LoggerFactory;

import domain.ILebkuchen;
import domain.ZutatTypEnum;

import alternativ.anlagen.AnlageInterface;
import alternativ.anlagen.Blech;
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
	private boolean warteAufCharge;
	private String chargeZumAbholen;
	private AnlageInterface blech;

	public Baecker(String id){
		super(ZutatenLager.ZUTATEN_LAGER, Ofen.OFEN, Qualitaetskontrolle.QUALITAETSKONTROLLE,id);
		blech = bindAnlage(Blech.BLECH);
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
		Charge fertigeCharge;
		if(warteAufCharge){
			try {
				Thread.sleep(BACKZEIT);
				logger.info("warte auf charge:" + chargeZumAbholen);
				warteAufCharge = false;
				try {
					fertigeCharge = (Charge) ziel.objectHolen(getId());
					logger.info("hole lebkuchen "+ fertigeCharge.getUID() + " b: " + fertigeCharge.getBaeckerId() );
					if(fertigeCharge != null){
						fertigeCharge.setStatusOfLebkuchen(Lebkuchen.Status.GEBACKEN);
						weiteresZiel.objectLiefern(fertigeCharge);
						logger.info("Charge fertig gebacken: " + fertigeCharge.getUID() );
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
			Resource charge;
				charge = nimmObjectVonAnlage(blech, this.getId());
				if(charge!=null){
					
					if(gibObjectAnAnlage(ziel,charge )){
						chargeZumAbholen = charge.getUID();
						warteAufCharge = true;
					}else {
						gibObjectAnAnlage(blech,charge);
						warteAufCharge = false;
					}
				}
	}


	private void teigMischen() {
		try {
			blech.objectLiefern(new Lebkuchen(Lebkuchen.Status.GEFERTIGT, honig, mehl, ei1, ei2, getId()));
			honig = null;
			mehl = null;
			ei1 = null;
			ei2 = null;
		} catch (RemoteException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void getResources(){
		if(honig == null && mehl ==null && ei1==null && ei2==null){
			honig = nimmObjectVonAnlage(quelle,ZutatTypEnum.HONIG);
			mehl = nimmObjectVonAnlage(quelle,ZutatTypEnum.MEHL);
			ei1 = nimmObjectVonAnlage(quelle,ZutatTypEnum.EI);
			ei2 = nimmObjectVonAnlage(quelle,ZutatTypEnum.EI);
		}
	}

	@Override
	public void verarbeiteZutat() {
		// TODO Auto-generated method stub

	}

}
