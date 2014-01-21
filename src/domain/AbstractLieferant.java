package domain;

import gui.ZutatenLagerAnzeige;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

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
	
	private void startLieferant() throws InterruptedException{
		long abladeZeit = (Math.abs(new Random().nextInt())%1000)+1000L;
		for(int i = 0; i< anzahl ; i++){
			//Benchmark
//			Thread.sleep(abladeZeit);
			zutatAbladen(zutatTyp);
		}
		
	}
	
	protected abstract void zutatAbladen(ZutatTypEnum zutatTypEnum);
	

}
