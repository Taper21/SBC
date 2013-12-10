package domain;

import java.util.List;

import alternativ.domain.Lebkuchen;

public interface GUIDataManager {
	
	public List<Zutat> getAllMehl();
	public List<Zutat> getAllEier();
	public List<Zutat> getAllHonig();
	public List<ILebkuchen> getAllLebkuchen();
	public List<ILebkuchen> getEntsorgtVerkostet();
	public List<ILebkuchen> getImOfen();
	public List<ILebkuchen> getPackungen();

}
