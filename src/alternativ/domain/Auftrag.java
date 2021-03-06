package alternativ.domain;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;

import domain.IAuftrag;

public class Auftrag extends Resource implements IAuftrag {
	
	private static final long serialVersionUID = 1L;
	private int normal;
	private int nuss;
	private int schoko;
	private int anzahl;
	private AtomicInteger erledigt = new AtomicInteger();
	private String status = "nicht angefangen";
	private boolean angefangen = false;
	private boolean beendet;

	public Auftrag(int normal, int nuss, int schoko, int anzahl){
		if(normal+schoko+nuss!=6){
			throw new IllegalArgumentException("Vorschlag darf nur 6 in Summe ergeben");
		}
		this.normal = normal;
		this.nuss = nuss;
		this.schoko = schoko;
		this.anzahl = anzahl;
	}

	@Override
	public String getID() {
		return uid;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public String getGesamtPackungszahl() {
		return anzahl+"";
	}

	@Override
	public String getNormaleLebkuchenAnzahl() {
		return normal+"";
	}

	@Override
	public String getNussLebkuchenAnzahl() {
		return nuss+"";
	}

	@Override
	public String getSchokoLebkuchenAnzahl() {
		return schoko+"";
	}

	public int getNormal() {
		return normal;
	}

	public int getSchoko() {
		return schoko;
	}

	public int getNuss() {
		return nuss;
	}

	public void addErledigt() {
		setStatus(" angefangen");
		angefangen=true;
		this.erledigt.incrementAndGet();
		if(erledigt.get()==anzahl){
			setBeendet(true);
			setStatus(" erledigt");
		}
	}

	@Override
	public String getErledigtePackungen() {
		return erledigt+"";
	}

	public boolean isAngefangen() {
		return angefangen;
	}

	public void setStatus(String string) {
		this.status = string;
	}

	public void setBeendet(boolean beendet) {
		this.beendet = beendet;
	}
	
	public boolean isBeendet(){
		return beendet;
	}


}
