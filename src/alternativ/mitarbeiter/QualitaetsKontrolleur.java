package alternativ.mitarbeiter;


import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.Random;

import domain.ZutatTypEnum;

import alternativ.anlagen.Logistik;
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
	private String ort;


	public QualitaetsKontrolleur(String id, String ausscheidungsRate, String ort){
		super(Qualitaetskontrolle.QUALITAETSKONTROLLE, Logistik.LOGISTIK, null,id, ort);
		this.ausscheidungsRate = Long.parseLong(ausscheidungsRate);
	}
	
	
	public static void main(String[] args) {
		QualitaetsKontrolleur qualitaetsKontrolleur = new QualitaetsKontrolleur(args[0], args[1], args[2]);
		qualitaetsKontrolleur.getLogger().info("QualitaetsKontrolleur started mit id: " + qualitaetsKontrolleur.getId());
		while(!qualitaetsKontrolleur.close){
			qualitaetsKontrolleur.nimmCharge();
			qualitaetsKontrolleur.kosteLebkuchen();
			qualitaetsKontrolleur.gibLebkuchenZuLogistik();
		}
	}


	private void gibLebkuchenZuLogistik() {
		if(zuPruefendeCharge!=null){
			if(gibObjectAnAnlage(ziel, zuPruefendeCharge)){
				logger.info(zuPruefendeCharge.getStatus() + " Charge zu Logistik weitergereicht id: " + zuPruefendeCharge.getUID());
				zuPruefendeCharge = null;
			}
		}
	}


	private void kosteLebkuchen() {
		if(zweiteKontrolle){
			if(zuPruefendeCharge!=null){
				Lebkuchen bissen = zuPruefendeCharge.takeRandomLebkuchen();
				bissen.setStatus(Lebkuchen.Status.GEGESSEN);
				logger.info("Random Lebkuchenbissen id: " + bissen.getUID());
			}
		}
		if(zuPruefendeCharge!=null){
			kontrolle();
		}
	}


	private void kontrolle() {
		if(zweiteKontrolle){
			zweiteKontrolle = false;
			if((Math.abs(new Random().nextLong())%100)<=ausscheidungsRate){
				zuPruefendeCharge.setSchmecktSchlecht();
			}else{
				zuPruefendeCharge.setStatus(Charge.Status.OK);
				zuPruefendeCharge.setStatusOfLebkuchen(Lebkuchen.Status.KONTROLLIERT);
			}
		}else{
			zuPruefendeCharge.setStatus(Charge.Status.OK);
			zweiteKontrolle = true;
		}
	}


	private void nimmCharge() {
		Resource zuPruefendeCharge = nimmObjectVonAnlage(quelle, null);
		if(checkInstance(Charge.class, zuPruefendeCharge)){
			this.zuPruefendeCharge = (Charge) zuPruefendeCharge;
			for(Lebkuchen x:((Charge) zuPruefendeCharge).getAll()){
				x.setQualitaetsMitarbeiterId(getId());
			}
		}
	}


	@Override
	public void verarbeiteZutat() {
		
	}


	public void start() {
		while(!close){
			nimmCharge();
			kosteLebkuchen();
			gibLebkuchenZuLogistik();
		}
	}



}
