package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractLieferant extends Thread{
	private int anzahl;
	protected ZutatTypEnum zutatTyp;
	protected List<Zutat> gelieferteZutaten = new ArrayList<Zutat>();
	
	public AbstractLieferant(int anzahl, ZutatTypEnum zutatTyp){
		this.anzahl = anzahl;
		this.zutatTyp = zutatTyp;
	}
	
	public void run(){
		try {
			startLieferant();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void startLieferant() throws InterruptedException{
		long abladeZeit = ((new Random().nextLong())%1000)+1000;
		for(int i = 0; i< anzahl ; i++){
			Zutat zutat = createZutatFromEnum(zutatTyp);
			Thread.sleep(abladeZeit);
			zutatAbladen(zutat);
			gelieferteZutaten.add(zutat);
		}
		
	}
	
	protected abstract Zutat createZutatFromEnum(ZutatTypEnum zutatTypEnum);
	protected abstract void zutatAbladen(Zutat z);
	

}
