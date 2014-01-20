package gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;

import domain.Zutat;
import domain.ZutatTypEnum;
import domain.GUIDataManager;

public class ZutatenLagerAnzeigenThread extends Thread {
	
	private GUIDataManager zutatenManager;
	private ZutatenLagerAnzeige zutatenLagerAnzeige;
	
	public ZutatenLagerAnzeigenThread(GUIDataManager zutatenManager,ZutatenLagerAnzeige zutatenLagerAnzeige){
		this.zutatenManager=zutatenManager;
		this.zutatenLagerAnzeige = zutatenLagerAnzeige;
	}
	
	public void run(){
		while(true){
			try {
				Thread.sleep(500);
				updateGUI();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void updateGUI(){
		List<Zutat> allEier = zutatenManager.getAllEier();
		List<Zutat> spaceZutaten = new ArrayList<Zutat>(allEier);
		List<Zutat> allHonig = zutatenManager.getAllHonig();
		spaceZutaten.addAll(allHonig);
		List<Zutat> allMehl = zutatenManager.getAllMehl();
		spaceZutaten.addAll(allMehl);
		List<Zutat> allSchoko = zutatenManager.getAllSchokolade();
		spaceZutaten.addAll(allSchoko);
		List<Zutat> allNuesse = zutatenManager.getAllNuesse();
		spaceZutaten.addAll(allNuesse);
		zutatenLagerAnzeige.setData(spaceZutaten, allMehl.size(), allHonig.size(), allEier.size(),allSchoko.size(),allNuesse.size(), zutatenManager.getAllLebkuchen());
		zutatenLagerAnzeige.setData(zutatenManager.getEntsorgtVerkostet());
		zutatenLagerAnzeige.setDataOfen(zutatenManager.getImOfen());
		zutatenLagerAnzeige.setDataPackungen(zutatenManager.getPackungen());
	}
	
	

}
