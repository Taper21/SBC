package alternativ.domain;

import domain.AbstractLieferant;
import domain.Zutat;
import domain.ZutatTypEnum;

import java.io.Serializable;
import java.rmi.server.UID;
import java.util.concurrent.atomic.AtomicInteger;

public class AlternativZutat extends Resource implements Zutat  {
	
	private static final long serialVersionUID = 1L;
	private ZutatTypEnum typ;
	private long abstractLieferantId; 
	
	public AlternativZutat(ZutatTypEnum typ, long l){
		super();
		this.typ = typ;
		this.abstractLieferantId = l;
		
	}
	
	public String getStringId() {
		return uid.toString();
	}

	@Override
	public long getLieferant() {
		return abstractLieferantId;
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
