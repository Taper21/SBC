package alternativ.domain;

import java.io.Serializable;
import java.rmi.server.UID;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.mitarbeiter.AlternativLieferant;

public abstract class Resource implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	protected Logger logger = LoggerFactory.getLogger(Resource.class);
	
	protected String uid = getNextId();
	
	public String getUID(){
		return uid;
	}
	
	static AtomicInteger id = new AtomicInteger(0);
	final private String getNextId() {
		return ""+id.incrementAndGet();
	}
	
	
	@Override
	final public String toString() {
		return this.getClass().getSimpleName() + " " + getUID();
	}
	

}
