package xvsm;

import domain.AbstractLieferant;
import domain.Zutat;
import domain.ZutatTypEnum;

public class ZutatXVSMImpl implements Zutat {
	
	private static long idSequenz =0;
	private long id;
	private ZutatTypEnum zutatTypEnum;
	private AbstractLieferant lieferant;
	
	public static ZutatXVSMImpl createInstance(ZutatTypEnum zutatTypEnum, AbstractLieferant lieferant){
		return new ZutatXVSMImpl(idSequenz++,zutatTypEnum, lieferant);
	}

	public ZutatXVSMImpl(long id, ZutatTypEnum zutatTypEnum,AbstractLieferant lieferant) {
		this.id = id;
		this.zutatTypEnum = zutatTypEnum;
		this.lieferant = lieferant;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public AbstractLieferant getLieferant() {
		return lieferant;
	}

	@Override
	public void abladen() {
		System.out.println("Lieferant " + lieferant.getId() + ";" + zutatTypEnum + "#" + getId());

	}

	@Override
	public ZutatTypEnum getZutatTypEnum() {
		return zutatTypEnum;
	}

}
