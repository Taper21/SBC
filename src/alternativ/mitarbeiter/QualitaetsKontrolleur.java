package alternativ.mitarbeiter;


import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.Random;

import domain.ZutatTypEnum;

import alternativ.anlagen.Ofen;
import alternativ.anlagen.Qualitaetskontrolle;
import alternativ.anlagen.ZutatenLager;
import alternativ.domain.Charge;
import alternativ.domain.Lebkuchen;
import alternativ.domain.Resource;

public class QualitaetsKontrolleur extends Mitarbeiter {


	

	private Charge zuPruefendeCharge;
	private long ausscheidungsRate;
	private boolean zweiteKontrolle = true;


	public QualitaetsKontrolleur(String id, String ausscheidungsRate){
		super(Qualitaetskontrolle.QUALITAETSKONTROLLE, "Logistik", null,id);
		this.ausscheidungsRate = Long.parseLong(ausscheidungsRate);
	}
	
	
	public static void main(String[] args) {
		QualitaetsKontrolleur qualitaetsKontrolleur = new QualitaetsKontrolleur(args[0], args[1]);
		qualitaetsKontrolleur.getLogger().info("QualitaetsKontrolleur started mit id: " + qualitaetsKontrolleur.getId());
		while(true){
			qualitaetsKontrolleur.nimmCharge();
			qualitaetsKontrolleur.kosteLebkuchen();
			qualitaetsKontrolleur.gibLebkuchenZuLogistik();
		}
	}


	private void gibLebkuchenZuLogistik() {
		if(zuPruefendeCharge!=null){
			if(gibZutatAb(zuPruefendeCharge)){
				logger.info(zuPruefendeCharge.getStatus() + " Charge zu Logistik weitergereicht id: " + zuPruefendeCharge.getUID());
				zuPruefendeCharge = null;
			}
		}
	}


	private void kosteLebkuchen() {
		if(zuPruefendeCharge!=null){
			Lebkuchen bissen = zuPruefendeCharge.takeRandomLebkuchen();
			logger.info("Random Lebkuchenbissen id: " + bissen.getUID());
			kontrolle();
		}
	}


	private void kontrolle() {
		if(zweiteKontrolle){
			zweiteKontrolle = false;
			if((new Random().nextLong()%100)<=ausscheidungsRate){
				zuPruefendeCharge.setSchmecktSchlecht();
			}else{
				zuPruefendeCharge.setStatus(Charge.Status.OK);
			}
		}else{
			zuPruefendeCharge.setStatus(Charge.Status.NICHT_KONTROLLIERT);
			zweiteKontrolle = true;
		}
	}


	private void nimmCharge() {
		Resource zuPruefendeCharge = besorgeZutat(null);
		if(checkInstance(Charge.class, zuPruefendeCharge)){
			this.zuPruefendeCharge = (Charge) zuPruefendeCharge;
		}
	}


	@Override
	public void verarbeiteZutat() {
		
	}


}