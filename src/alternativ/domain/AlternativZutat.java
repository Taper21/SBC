package alternativ.domain;

import domain.AbstractLieferant;
import domain.Zutat;
import domain.ZutatTypEnum;

import java.io.Serializable;
import java.rmi.server.UID;

public class AlternativZutat extends Resource implements Zutat  {
	
	private static final long serialVersionUID = 1L;
	private ZutatTypEnum typ;
	private AbstractLieferant abstractLieferant; 
	
	public AlternativZutat(ZutatTypEnum typ, AbstractLieferant lieferant){
		super();
		this.typ = typ;
		this.abstractLieferant = lieferant;
		
	}
	
	public String getStringId() {
		return uid.toString();
	}

	@Override
	public AbstractLieferant getLieferant() {
		return abstractLieferant;
	}

	@Override
	public ZutatTypEnum getZutatTypEnum() {
		return typ;
	}

	@Override
	public String getId() {
		return getStringId();
	}

}
