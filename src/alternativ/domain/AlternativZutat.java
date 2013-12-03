package alternativ.domain;

import domain.AbstractLieferant;
import domain.Zutat;
import domain.ZutatTypEnum;

import java.io.Serializable;
import java.rmi.server.UID;

public class AlternativZutat extends Resource implements Zutat  {
	
	private static final long serialVersionUID = 1L;
	private ZutatTypEnum typ;
	private long lieferant; 
	
	public AlternativZutat(ZutatTypEnum typ, long lieferantId){
		super();
		this.typ = typ;
		this.lieferant = lieferantId;
		
	}
	
	public String getStringId() {
		return uid.toString();
	}

	@Override
	public AbstractLieferant getLieferant() {
		return null;
	}

	@Override
	public ZutatTypEnum getZutatTypEnum() {
		return typ;
	}

	@Override
	public String getId() {
		return "";
	}

}
