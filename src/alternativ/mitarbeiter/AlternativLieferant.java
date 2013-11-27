package alternativ.mitarbeiter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.domain.AlternativZutat;

import domain.AbstractLieferant;
import domain.Zutat;
import domain.ZutatTypEnum;

public class AlternativLieferant extends AbstractLieferant {
	Logger logger = LoggerFactory.getLogger(AlternativLieferant.class);
	List<AlternativZutat> generierteZutaten = new ArrayList<AlternativZutat>();

	public AlternativLieferant(int anzahl, ZutatTypEnum zutatTyp) {
		super(anzahl, zutatTyp);
	}

	@Override
	protected Zutat createZutatFromEnum(ZutatTypEnum zutatTypEnum) {
		AlternativZutat alternativZutat = new AlternativZutat(zutatTypEnum, getId());
		generierteZutaten.add(alternativZutat);
		logger.info("Lieferant " + getId() + " generated typ: " +zutatTypEnum+" id: " +alternativZutat.getStringId() );
		return null;
	}

	@Override
	protected void zutatAbladen(Zutat z) {
		if(generierteZutaten.size()>0){
			AlternativZutat remove = generierteZutaten.remove(generierteZutaten.size()-1);
			logger.info("Lieferant " + getId() + " abgeladen typ: " +remove.getZutatTypEnum()+" id: " +remove.getStringId() );
		}
	}

}
