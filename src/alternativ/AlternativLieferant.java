package alternativ;

import domain.AbstractLieferant;
import domain.Zutat;
import domain.ZutatTypEnum;

public class AlternativLieferant extends AbstractLieferant {

	public AlternativLieferant(int anzahl, ZutatTypEnum zutatTyp) {
		super(anzahl, zutatTyp);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Zutat createZutatFromEnum(ZutatTypEnum zutatTypEnum) {
		//Zu implementieren
		return null;
	}

	@Override
	protected void zutatAbladen(Zutat z) {
		// TODO Auto-generated method stub
		
	}

}
