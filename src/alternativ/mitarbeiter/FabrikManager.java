package alternativ.mitarbeiter;

import gui.MainFrame;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import alternativ.anlagen.AuftragAblage;
import alternativ.anlagen.Blech;
import alternativ.anlagen.FertigePackungenLager;
import alternativ.anlagen.Logistik;
import alternativ.anlagen.Ofen;
import alternativ.anlagen.Qualitaetskontrolle;
import alternativ.anlagen.ZutatenLager;
import alternativ.domain.Lebkuchen;

import domain.IAuftrag;
import domain.ILebkuchen;
import domain.Zutat;
import domain.ZutatTypEnum;
import domain.GUIDataManager;

public class FabrikManager implements GUIDataManager {
	
	private ZutatenLager lager;
	private Ofen ofen;
	private Qualitaetskontrolle qualitaetskontrolle;
	private Logistik logistik;
	private FertigePackungenLager fertigePackungenLager;
	private Blech blech;
	private AuftragAblage aufgabenAblage;

	public FabrikManager(){
		String standort = MainFrame.getOrt();
		if(StringUtils.isEmpty(standort)){
			throw new IllegalArgumentException("Firma braucht standort");
		}
		this.lager = new ZutatenLager(standort);
		this.blech = new Blech(standort);
		this.ofen = new Ofen(standort);
		this.qualitaetskontrolle = new Qualitaetskontrolle(standort);
		this.logistik = new Logistik(standort);
		this.fertigePackungenLager = new FertigePackungenLager(standort);
		this.aufgabenAblage = new AuftragAblage(standort);
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

	@Override
	public void erzeugeAuftrag(int packungen, int normaleLebkuchen,
			int schokoLebkuchen, int nussLebkuchen) {
		aufgabenAblage.submitAuftrag(packungen, normaleLebkuchen, schokoLebkuchen, nussLebkuchen);
	}

	@Override
	public List<IAuftrag> getAllAuftraege() {
		return aufgabenAblage.getAllAuftraege();
	}

	@Override
	public List<ILebkuchen> getAllKontrolliert() {
		return logistik.getAllKontrolliert();
	}


}
