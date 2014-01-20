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
	
	abstract String getNextId();
	
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + getUID();
	}

}
