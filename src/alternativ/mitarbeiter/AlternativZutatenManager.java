package alternativ.mitarbeiter;

import java.util.ArrayList;
import java.util.List;

import alternativ.anlagen.Logistik;
import alternativ.anlagen.Ofen;
import alternativ.anlagen.Qualitaetskontrolle;
import alternativ.anlagen.ZutatenLager;

import domain.Zutat;
import domain.ZutatTypEnum;
import domain.GUIDataManager;

public class AlternativZutatenManager implements GUIDataManager {
	
	private ZutatenLager lager;
	private Ofen ofen;
	private Qualitaetskontrolle qualitaetskontrolle;
	private Logistik logistik;

	public AlternativZutatenManager(){
		this.lager = new ZutatenLager();
		this.ofen = new Ofen();
		this.qualitaetskontrolle = new Qualitaetskontrolle();
		this.logistik = new Logistik();
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


}
