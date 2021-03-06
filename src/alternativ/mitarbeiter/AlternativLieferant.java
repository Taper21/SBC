package alternativ.mitarbeiter;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.anlagen.Anlage;
import alternativ.anlagen.AnlageInterface;
import alternativ.domain.AlternativZutat;

import domain.AbstractLieferant;
import domain.ZutatTypEnum;

public class AlternativLieferant extends AbstractLieferant {
	private static final long serialVersionUID = 1L;
	private int gesamt;
	Logger logger = LoggerFactory.getLogger(AlternativLieferant.class);
	private String ort;

	public AlternativLieferant(int anzahl, ZutatTypEnum zutatTyp, String ort) {
		super(anzahl, zutatTyp);
		this.ort = ort;
	}


	@Override
	protected void zutatAbladen(ZutatTypEnum zutatTypEnum) {
		AlternativZutat zutat = new AlternativZutat(zutatTypEnum, getId());
		logger.info("Lieferant " + getId() + " abgeladen typ: " +zutat.getZutatTypEnum()+" id: " +zutat.getStringId() +" gesamt: " + ++gesamt);
	        try {
	            String name = "ZutatenLager" + " "+ ort;
	            Registry registry = LocateRegistry.getRegistry(null);
	            AnlageInterface comp = (AnlageInterface) registry.lookup(name);
	            comp.objectLiefern(zutat); 
	        } catch (Exception e) {
	            logger.error("AlternativZutat exception:");
	            e.printStackTrace();
	        }
	}

}
