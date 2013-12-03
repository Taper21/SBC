package alternativ.mitarbeiter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import alternativ.anlagen.FertigePackungenLager;
import alternativ.anlagen.Logistik;
import alternativ.domain.Charge;
import alternativ.domain.Lebkuchen;
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
		while(true){
			logistikMitarbeiter.holeCharge();
			logistikMitarbeiter.verpackeLebkuchen();
			logistikMitarbeiter.fertigePackungAbliefern();
		}
	}
	

	private void fertigePackungAbliefern() {
		for(Packung x:fertigePackungen){
			gibZutatAb(x);
		}
	}

	private void holeCharge() {
		if(zuVerpackendeLebkuchen.size()<LEBKUCHEN_PRO_VERPACKUNG){
			Resource r = besorgeZutat(null);
			if(r!=null){
				if(checkInstance(Charge.class, r)){
					Charge charge = (Charge) r;
					for(Lebkuchen l : charge.getAll()){
						zuVerpackendeLebkuchen.add(l);
					}
				}
			}
		}
	}

	private void verpackeLebkuchen() {
		Packung packung = new Packung();
		if(zuVerpackendeLebkuchen.size()>=LEBKUCHEN_PRO_VERPACKUNG){
			for (int i = 0; i <LEBKUCHEN_PRO_VERPACKUNG; i++) {
				Lebkuchen lebkuchen = zuVerpackendeLebkuchen.poll();
				if(lebkuchen!=null){
					packung.add(lebkuchen);
				}else{
					logger.error("zu verpackende Lebkuchen war leer, unmöglich!");
				}
			}
		}
		if(packung.size()== LEBKUCHEN_PRO_VERPACKUNG){
			fertigePackungen.add(packung);
		}
	}

	@Override
	public void verarbeiteZutat() {

	}

}
