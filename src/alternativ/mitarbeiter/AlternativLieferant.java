package alternativ.mitarbeiter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.domain.AlternativZutat;

import domain.AbstractLieferant;
import domain.ZutatTypEnum;

public class AlternativLieferant extends AbstractLieferant {
	Logger logger = LoggerFactory.getLogger(AlternativLieferant.class);

	public AlternativLieferant(int anzahl, ZutatTypEnum zutatTyp) {
		super(anzahl, zutatTyp);
	}


	@Override
	protected void zutatAbladen(ZutatTypEnum zutatTypEnum) {
		AlternativZutat zutat = new AlternativZutat(zutatTypEnum, getId());
		logger.info("Lieferant " + getId() + " abgeladen typ: " +zutat.getZutatTypEnum()+" id: " +zutat.getStringId() );
	}

}
