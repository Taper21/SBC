package alternativ.domain;

import domain.AbstractLieferant;
import domain.Zutat;
import domain.ZutatTypEnum;

import java.io.Serializable;
import java.rmi.server.UID;

public class AlternativZutat implements Zutat, Serializable, Resource {
	
	private static final long serialVersionUID = 1L;
	private ZutatTypEnum typ;
	private UID id;
	private long lieferant; 
	
	public AlternativZutat(ZutatTypEnum typ, long lieferantId){
		this.typ = typ;
		this.id = new UID();
		this.lieferant = lieferant;
		
	}
	
	public String getStringId() {
		return id.toString();
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
	public long getId() {
		throw new UnsupportedOperationException("use StringId instead");
	}

}
