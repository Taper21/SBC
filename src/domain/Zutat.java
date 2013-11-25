package domain;

public interface Zutat {
	
	public long getId();
	public AbstractLieferant getLieferant();
	public void abladen();
	public ZutatTypEnum getZutatTypEnum();
}
