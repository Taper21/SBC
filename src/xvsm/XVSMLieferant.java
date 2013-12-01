package xvsm;

import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsCoreException;

import domain.AbstractLieferant;
import domain.Zutat;
import domain.ZutatTypEnum;

public class XVSMLieferant extends AbstractLieferant {

	public XVSMLieferant(int anzahl, ZutatTypEnum zutatTyp) {
		super(anzahl, zutatTyp);
	}

	private Zutat createZutatFromEnum(ZutatTypEnum zutatTypEnum) {
		return ZutatXVSMImpl.createInstance(zutatTypEnum,this);
	}

	@Override
	protected void zutatAbladen(ZutatTypEnum zutatTypEnum) {
		Zutat z = createZutatFromEnum(zutatTypEnum);
		System.out.println("Lieferant " + getId() + ";" + z.getZutatTypEnum() + "#" + z.getId());
		try {
			Space.getCapi().write(new Entry(z), Space.createOrLookUpContainer(Space.getLager(zutatTypEnum)));
		} catch (MzsCoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
