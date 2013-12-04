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
	private long lieferantId;
	
	public static synchronized ZutatXVSMImpl createInstance(ZutatTypEnum zutatTypEnum, long lieferantId){
		return new ZutatXVSMImpl(idSequenz++,zutatTypEnum, lieferantId);
	}

	public ZutatXVSMImpl(long id, ZutatTypEnum zutatTypEnum,long lieferantId2) {
		this.id = id;
		this.zutatTypEnum = zutatTypEnum;
		this.lieferantId = lieferantId2;
	}

	@Override
	public String getId() {
		return id+"";
	}

	@Override
	public long getLieferant() {
		return lieferantId;
	}

	@Override
	public ZutatTypEnum getZutatTypEnum() {
		return zutatTypEnum;
	}

}
