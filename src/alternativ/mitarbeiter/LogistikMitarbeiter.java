package alternativ.mitarbeiter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import alternativ.anlagen.FertigePackungenLager;
import alternativ.anlagen.Logistik;
import alternativ.domain.Charge;
import alternativ.domain.Lebkuchen;
import alternativ.domain.Packung;
import alternativ.domain.Resource;

public class LogistikMitarbeiter extends Mitarbeiter {

	public static int LEBKUCHEN_PRO_VERPACKUNG = 6;
	Queue<Lebkuchen> zuVerpackendeLebkuchen = new ConcurrentLinkedDeque<Lebkuchen>();
	Queue<Packung> fertigePackungen = new ConcurrentLinkedDeque<Packung>();
	
	public LogistikMitarbeiter(String id) {
		super(Logistik.LOGISTIK, FertigePackungenLager.FERTIGEPACKUNGENLAGER, null, id);
	}
	
	public static void main(String args[]){
		LogistikMitarbeiter logistikMitarbeiter = new LogistikMitarbeiter(args[0]);
		logistikMitarbeiter.logger.info("logistikMitarbeiter id: " + logistikMitarbeiter.getId() + " started.");
		while(!logistikMitarbeiter.close){
			logistikMitarbeiter.holeCharge();
			logistikMitarbeiter.verpackeLebkuchen();
			logistikMitarbeiter.fertigePackungAbliefern();
		}
	}
	

	private void fertigePackungAbliefern() {
		for(Packung x:fertigePackungen){
			logger.info("logistig gibt packung ab: " + x.getUID());
			gibObjectAnAnlage(ziel, x);
			
		}
		fertigePackungen = new ConcurrentLinkedDeque<Packung>();
	}

	private void holeCharge() {
		if(zuVerpackendeLebkuchen.size()<LEBKUCHEN_PRO_VERPACKUNG){
			logger.info("besorgeZutat Anfang");
			Resource r = nimmObjectVonAnlage(quelle,null);
			logger.info("besorgeZutat Ende");
			if(r!=null){
				if(checkInstance(Charge.class, r)){
					Charge charge = (Charge) r;
					for(Lebkuchen l : charge.getAll()){
						l.setLogistikMitarbeiterId(getId());
						logger.info("logistikMitarbeiter verpackt: " + l.getId() +" in summe: " + zuVerpackendeLebkuchen.size());
						zuVerpackendeLebkuchen.add(l);
					}
				}
			}
		}
	}

	private void verpackeLebkuchen() {
		Packung packung = new Packung();
		//verpacke solange genug lebkuchen
		while(zuVerpackendeLebkuchen.size()>=LEBKUCHEN_PRO_VERPACKUNG){
			//verpackt 6 lebkuchen
			for (int i = 0; i <LEBKUCHEN_PRO_VERPACKUNG; i++) {
				Lebkuchen lebkuchen = zuVerpackendeLebkuchen.poll();
				if(lebkuchen!=null){
					lebkuchen.setVerpackungId(packung.getUID().toString());
					packung.add(lebkuchen);
					logger.info("lebkuchen hinzugefügt: " + lebkuchen.getUID());
				}else{
					logger.error("zu verpackende Lebkuchen war leer, unmöglich!");
				}
			}
			if(packung.size()== LEBKUCHEN_PRO_VERPACKUNG){
				fertigePackungen.add(packung);
			}
			packung = new Packung();
		}
	}

	@Override
	public void verarbeiteZutat() {

	}

}
