package alternativ.domain;

import java.io.Serializable;
import java.rmi.server.UID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.mitarbeiter.AlternativLieferant;

public abstract class Resource implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected Logger logger = LoggerFactory.getLogger(Resource.class);
	
	protected UID uid = new UID();
	
	public UID getUID(){
		return uid;
	}

}
