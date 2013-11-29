package xvsm;

import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsCoreException;

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

	@Override
	protected void zutatAbladen(Zutat z) {
		System.out.println("Lieferant " + getId() + ";" + z.getZutatTypEnum() + "#" + z.getId());
		Standort targetLager;
		switch(z.getZutatTypEnum()){
			case EI:targetLager = Standort.EIERLAGER;
				break;
			case HONIG:targetLager = Standort.HONIGLAGER;
				break;
			case MEHL:targetLager = Standort.MEHLLAGER;
				break;
			default: targetLager = Standort.MEHLLAGER;
		}
		try {
			Space.getCapi().write(new Entry(z), Space.createOrLookUpContainer(targetLager));
		} catch (MzsCoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
