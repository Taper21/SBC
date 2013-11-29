package alternativ.mitarbeiter;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alternativ.anlagen.rmi.Anlage;
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
	
		 if (System.getSecurityManager() == null) {
	            System.setSecurityManager(new SecurityManager());
	        }
	        try {
	            String name = "ZutatenLager";
	            Registry registry = LocateRegistry.getRegistry("127.0.0.1");
	            Anlage comp = (Anlage) registry.lookup(name);
	            comp.objectLiefern(zutat);
	            
	        } catch (Exception e) {
	            logger.error("AlternativZutat exception:");
	            e.printStackTrace();
	        }
	}

}
