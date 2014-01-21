package domain;

import java.util.List;

import alternativ.domain.Lebkuchen;

public interface GUIDataManager {
	
	public List<Zutat> getAllMehl();
	public List<Zutat> getAllEier();
	public List<Zutat> getAllHonig();
	public List<Zutat> getAllSchokolade();
	public List<Zutat> getAllNuesse();
	public List<ILebkuchen> getAllLebkuchen();
	public List<ILebkuchen> getEntsorgtVerkostet();
	public List<ILebkuchen> getImOfen();
	public List<ILebkuchen> getPackungen();
	public void erzeugeAuftrag(int packungungen, int normaleLebkuchen, int schokoLebkuchen, int nussLebkuchen);
	public List<IAuftrag> getAllAuftraege();
	public List<ILebkuchen> getAllKontrolliert();

}
