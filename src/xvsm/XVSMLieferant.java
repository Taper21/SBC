package xvsm;

import domain.AbstractLieferant;
import domain.Zutat;
import domain.ZutatTypEnum;

public class XVSMLieferant extends AbstractLieferant {

	public XVSMLieferant(int anzahl, ZutatTypEnum zutatTyp) {
		super(anzahl, zutatTyp);
	}

	@Override
	protected Zutat createZutatFromEnum(ZutatTypEnum zutatTypEnum) {
		return ZutatXVSMImpl.createInstance(zutatTypEnum,this);
	}

}
