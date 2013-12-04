package gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domain.Zutat;
import domain.ZutatTypEnum;
import domain.GUIDataManager;

public class ZutatenLagerAnzeigenThread extends Thread {
	
	private GUIDataManager zutatenManager;
	private ZutatenLagerAnzeige zutatenLagerAnzeige;
	
	public ZutatenLagerAnzeigenThread(GUIDataManager zutatenManager,ZutatenLagerAnzeige zutatenLagerAnzeige){
		this.zutatenManager=zutatenManager;
		this.zutatenLagerAnzeige = zutatenLagerAnzeige;
		this.start();
	}
	
	public void run(){
		while(true){
			try {
				Thread.sleep(500);
				updateGUI();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private synchronized void updateGUI(){
		List<Zutat> spaceZutaten = zutatenManager.getAllEier();
		spaceZutaten.addAll(zutatenManager.getAllHonig());
		spaceZutaten.addAll(zutatenManager.getAllMehl());
		List<String> spaceZutatenIds = new ArrayList<String>();
		for(Zutat z : spaceZutaten){
			if(zutatenLagerAnzeige.angezeigteZutaten.get(z.getId())==null){
				zutatenLagerAnzeige.addZutat(z);
			}
			spaceZutatenIds.add(z.getId());
		}
		Set<String> alleCurrent =new HashSet<String>();
		alleCurrent.addAll(zutatenLagerAnzeige.angezeigteZutaten.keySet());
		for(String zid : alleCurrent){
			if(!spaceZutatenIds.contains(zid)){
				zutatenLagerAnzeige.deleteZutat(zutatenLagerAnzeige.angezeigteZutaten.get(zid));
			}
		}
	}
	
	

}
