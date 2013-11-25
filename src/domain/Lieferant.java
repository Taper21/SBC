package domain;

public class Lieferant {

	private int anzahl;
	private ZutatTypEnum zutatTypEnum;
	
	public Lieferant(int anzahl, ZutatTypEnum zutatTypEnum){
		this.anzahl = anzahl;
		this.zutatTypEnum = zutatTypEnum;
	}
	
	public ZutatTypEnum getZutatTypEnum() {
		return zutatTypEnum;
	}
	public void setZutatTypEnum(ZutatTypEnum zutatTypEnum) {
		this.zutatTypEnum = zutatTypEnum;
	}
	public int getAnzahl() {
		return anzahl;
	}
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
}
