package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractLieferant extends Thread implements Serializable{
	private int anzahl;
	protected ZutatTypEnum zutatTyp;
	
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
			Thread.sleep(abladeZeit);
			zutatAbladen(zutatTyp);
		}
		
	}
	
	protected abstract void zutatAbladen(ZutatTypEnum zutatTypEnum);
	

}
