package alternativ.mitarbeiter;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import alternativ.anlagen.AuftragAblage;
import alternativ.anlagen.FertigePackungenLager;
import alternativ.anlagen.Logistik;
import alternativ.domain.Auftraege;
import alternativ.domain.Auftrag;
import alternativ.domain.Charge;
import alternativ.domain.Lebkuchen;
import alternativ.domain.Packung;
import alternativ.domain.Resource;

public class LogistikMitarbeiter extends Mitarbeiter {

	public static int LEBKUCHEN_PRO_VERPACKUNG = 6;
	Queue<Lebkuchen> zuVerpackendeLebkuchen = new ConcurrentLinkedDeque<Lebkuchen>();
	Queue<Packung> fertigePackungen = new ConcurrentLinkedDeque<Packung>();

	public LogistikMitarbeiter(String id) {
		super(Logistik.LOGISTIK, FertigePackungenLager.FERTIGEPACKUNGENLAGER, AuftragAblage.AUFTRAGABLAGE, id);
	}

	public static void main(String args[]) {
		LogistikMitarbeiter logistikMitarbeiter = new LogistikMitarbeiter(args[0]);
		logistikMitarbeiter.logger.info("logistikMitarbeiter id: " + logistikMitarbeiter.getId() + " started.");
		while (!logistikMitarbeiter.close) {
			logistikMitarbeiter.holeLebkuchenFuerPackung();
			// logistikMitarbeiter.verpackeLebkuchen();
			// logistikMitarbeiter.fertigePackungAbliefern();
		}
	}

	private void fertigePackungAbliefern() {
		for (Packung x : fertigePackungen) {
			logger.info("logistig gibt packung ab: " + x.getUID());
			gibObjectAnAnlage(ziel, x);

		}
		fertigePackungen = new ConcurrentLinkedDeque<Packung>();
	}

	private void holeLebkuchenFuerPackung() {
		Packung r = null;
		Auftraege nimmObjectVonAnlage = (Auftraege) nimmObjectVonAnlage(weiteresZiel, null);
		List<Auftrag> priority = nimmObjectVonAnlage.getPriority();
		r = (Packung) nimmObjectVonAnlage(quelle, priority);
		if (r == null) {
			return;
		}
		if (r.getAllLebkuchen().size() == 6) {
			for (Lebkuchen l : r.getAllLebkuchen()) {
				l.setLogistikMitarbeiterId(this.getId());
				l.setVerpackungId(r.getUID());
			}
			for(Auftrag auftrag:priority){
				if(r.getAuftragId() == auftrag.getID()){
					gibObjectAnAnlage(weiteresZiel, auftrag);
				}
			}
			gibObjectAnAnlage(ziel, r);
		} else {
			System.out.println("FATAL ERROR PACKUNG HAS WRONG NUMBER OF LEBKUCHEN IN LOGISTIKMITARBEITER");
		}
	}

	private void verpackeLebkuchen() {
		Packung packung = new Packung();
		// verpacke solange genug lebkuchen
		while (zuVerpackendeLebkuchen.size() >= LEBKUCHEN_PRO_VERPACKUNG) {
			// verpackt 6 lebkuchen
			for (int i = 0; i < LEBKUCHEN_PRO_VERPACKUNG; i++) {
				Lebkuchen lebkuchen = zuVerpackendeLebkuchen.poll();
				if (lebkuchen != null) {
					lebkuchen.setVerpackungId(packung.getUID().toString());
					packung.add(lebkuchen);
					logger.info("lebkuchen hinzugefügt: " + lebkuchen.getUID());
				} else {
					logger.error("zu verpackende Lebkuchen war leer, unmöglich!");
				}
			}
			if (packung.size() == LEBKUCHEN_PRO_VERPACKUNG) {
				fertigePackungen.add(packung);
			}
			packung = new Packung();
		}
	}

	@Override
	public void verarbeiteZutat() {

	}

}
