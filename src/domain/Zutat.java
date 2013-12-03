package domain;

import java.io.Serializable;

public interface Zutat extends Serializable{
	
	public String getId();
	public AbstractLieferant getLieferant();
//	public void abladen();
	public ZutatTypEnum getZutatTypEnum();
}
