package alternativ.mitarbeiter;

import java.util.ArrayList;
import java.util.List;

import alternativ.anlagen.Blech;
import alternativ.anlagen.FertigePackungenLager;
import alternativ.anlagen.Logistik;
import alternativ.anlagen.Ofen;
import alternativ.anlagen.Qualitaetskontrolle;
import alternativ.anlagen.ZutatenLager;
import alternativ.domain.Lebkuchen;

import domain.ILebkuchen;
import domain.Zutat;
import domain.ZutatTypEnum;
import domain.GUIDataManager;

public class AlternativZutatenManager implements GUIDataManager {
	
	private ZutatenLager lager;
	private Ofen ofen;
	private Qualitaetskontrolle qualitaetskontrolle;
	private Logistik logistik;
	private FertigePackungenLager fertigePackungenLager;
	private Blech blech;

	public AlternativZutatenManager(){
		this.lager = new ZutatenLager();
		this.blech = new Blech();
		this.ofen = new Ofen();
		this.qualitaetskontrolle = new Qualitaetskontrolle();
		this.logistik = new Logistik();
		this.fertigePackungenLager = new FertigePackungenLager();
	}

	@Override
	public List<Zutat> getAllMehl() {
		return lager.getAllMehl();
	}

	@Override
	public List<Zutat> getAllEier() {
		return lager.getAllEier();
	}

	@Override
	public List<Zutat> getAllHonig() {
		return lager.getAllHonig();
	}

	@Override
	public List<ILebkuchen> getAllLebkuchen() {
		List<ILebkuchen> result = new ArrayList<ILebkuchen>();
		result.addAll(blech.getAllLebkuchen());
		result.addAll(ofen.getAllLebkuchen());
		result.addAll(qualitaetskontrolle.getAllLebkuchen());
		result.addAll(logistik.getAllLebkuchen());
		result.addAll(fertigePackungenLager.getAllLebkuchen());
		return result;
	}

	@Override
	public List<ILebkuchen> getEntsorgtVerkostet() {
		return logistik.getAbfallVerkostet();
	}
	@Override
	public List<ILebkuchen> getImOfen() {
		return ofen.getAllLebkuchen();
	}

	@Override
	public List<ILebkuchen> getPackungen() {
		return fertigePackungenLager.getAllLebkuchen();
	}

	@Override
	public List<Zutat> getAllSchokolade() {
		return lager.getAllSchokolade();
	}

	@Override
	public List<Zutat> getAllNuesse() {
		return lager.getAllNuesse();
	}


}
