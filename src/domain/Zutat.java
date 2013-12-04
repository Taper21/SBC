package domain;

import java.io.Serializable;

public interface Zutat extends Serializable{
	
	public String getId();
	public long getLieferant();
//	public void abladen();
	public ZutatTypEnum getZutatTypEnum();
}
