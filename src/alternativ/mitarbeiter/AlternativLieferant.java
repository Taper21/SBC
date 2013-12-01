package alternativ.mitarbeiter;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.anlagen.Anlage;
import alternativ.domain.AlternativZutat;

import domain.AbstractLieferant;
import domain.ZutatTypEnum;

public class AlternativLieferant extends AbstractLieferant {
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(AlternativLieferant.class);

	public AlternativLieferant(int anzahl, ZutatTypEnum zutatTyp) {
		super(anzahl, zutatTyp);
	}


	@Override
	protected void zutatAbladen(ZutatTypEnum zutatTypEnum) {
		AlternativZutat zutat = new AlternativZutat(zutatTypEnum, getId());
		logger.info("Lieferant " + getId() + " abgeladen typ: " +zutat.getZutatTypEnum()+" id: " +zutat.getStringId() );
	
	        try {
	            String name = "ZutatenLager";
	            Registry registry = LocateRegistry.getRegistry(null);
	            Anlage comp = (Anlage) registry.lookup(name);
	            comp.objectLiefern(zutat);
	            
	        } catch (Exception e) {
	            logger.error("AlternativZutat exception:");
	            e.printStackTrace();
	        }
	}

}
