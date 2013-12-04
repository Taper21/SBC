package xvsm;

import java.io.Serializable;

import javax.sql.rowset.serial.SerialArray;

import org.mozartspaces.core.Capi;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.MzsCore;

import domain.AbstractLieferant;
import domain.Zutat;
import domain.ZutatTypEnum;

public class ZutatXVSMImpl implements Zutat,Serializable {
	
	private static long idSequenz =0;
	private long id;
	private ZutatTypEnum zutatTypEnum;
	private AbstractLieferant lieferant;
	
	public static synchronized ZutatXVSMImpl createInstance(ZutatTypEnum zutatTypEnum, AbstractLieferant lieferant){
		return new ZutatXVSMImpl(idSequenz++,zutatTypEnum, lieferant);
	}

	public ZutatXVSMImpl(long id, ZutatTypEnum zutatTypEnum,AbstractLieferant lieferant) {
		this.id = id;
		this.zutatTypEnum = zutatTypEnum;
		this.lieferant = lieferant;
	}

	@Override
	public String getId() {
		return id+"";
	}

	@Override
	public AbstractLieferant getLieferant() {
		return lieferant;
	}

	@Override
	public ZutatTypEnum getZutatTypEnum() {
		return zutatTypEnum;
	}

}
