package alternativ.domain;

import java.io.Serializable;

public class Vorschlag implements Serializable{
	private static final long serialVersionUID = 1L;
	private final int normal;
	private final int schoko;
	private final int nuss;

	public Vorschlag(int normal, int schoko, int nuss){
		if(normal+schoko+nuss!=6){
			throw new IllegalArgumentException("Vorschlag darf nur 6 in Summe ergeben");
		}
		this.normal = normal;
		this.schoko = schoko;
		this.nuss = nuss;
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
}
