package alternativ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.AbstractLieferant;
import domain.Zutat;
import domain.ZutatTypEnum;

public class AlternativLieferant extends AbstractLieferant {
	Logger logger = LoggerFactory.getLogger(AlternativLieferant.class);


	public AlternativLieferant(int anzahl, ZutatTypEnum zutatTyp) {
		super(anzahl, zutatTyp);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Zutat createZutatFromEnum(ZutatTypEnum zutatTypEnum) {
		logger.info("generated: " + zutatTypEnum);
		return null;
	}

	@Override
	protected void zutatAbladen(Zutat z) {
		// TODO Auto-generated method stub
		
	}

}
