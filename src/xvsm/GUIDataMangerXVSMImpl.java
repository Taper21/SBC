package xvsm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.FifoCoordinator.FifoSelector;
import org.mozartspaces.capi3.IsolationLevel;
import org.mozartspaces.capi3.LindaCoordinator;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.aspects.AbstractContainerAspect;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.Operation;

import domain.IAuftrag;
import domain.ILebkuchen;
import domain.Zutat;
import domain.ZutatTypEnum;
import domain.GUIDataManager;

public class GUIDataMangerXVSMImpl implements GUIDataManager {
	
	private static ArrayList<Zutat> allezutaten = new ArrayList<Zutat>();
	private static ArrayList<Zutat> eier = new ArrayList<Zutat>();
	private static ArrayList<Zutat> honig= new ArrayList<Zutat>();
	private static ArrayList<Zutat> mehl= new ArrayList<Zutat>();
	private static ArrayList<Zutat> schoko= new ArrayList<Zutat>();
	private static ArrayList<Zutat> nuesse= new ArrayList<Zutat>();
	
	private static long auftragID = 0;
	
	
	@Override
	public List<Zutat> getAllHonig(){
		List<Zutat> alleHonig = getAllZutatenByTyp(ZutatTypEnum.HONIG);
		if(alleHonig==null){
			return honig;
		}else{
			honig = new ArrayList<Zutat>();
			honig.addAll(alleHonig);
			return honig;
		}
	}
	
	@Override
	public List<Zutat> getAllMehl(){
		List<Zutat> alleMehl = getAllZutatenByTyp(ZutatTypEnum.MEHL);
		if(alleMehl==null){
			return mehl;
		}else{
			mehl = new ArrayList<Zutat>();
			mehl.addAll(alleMehl);
			return mehl;
		}
	}

	@Override
	public List<Zutat> getAllEier(){
		List<Zutat> alleEier = getAllZutatenByTyp(ZutatTypEnum.EI);
		if(alleEier==null){
			return eier;
		}else{
			eier = new ArrayList<Zutat>();
			eier.addAll(alleEier);
			return eier;
		}
}
	
	private ArrayList<Zutat> getAllZutatenByTyp(ZutatTypEnum zutatTypEnum){
		//if(Space.getCore()!=null){
		try {
			return Space.getCapi().read(Space.createOrLookUpContainer(Space.getLager(zutatTypEnum) ),FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL),500,null);
		} catch (MzsCoreException e) {
			e.printStackTrace();
		} //}
		return null;
	}

	@Override
	public List<ILebkuchen> getAllLebkuchen() {
		ArrayList<ILebkuchen> alle = new ArrayList<ILebkuchen>();
		alle.addAll(getAllLebkuchenWithStatus(Standort.LEBKUCHEN_GEFERTIGT));
		alle.addAll(getAllLebkuchenWithStatus(Standort.OFEN));
		alle.addAll(getAllLebkuchenWithStatus(Standort.GEBACKEN));
		alle.addAll(getAllLebkuchenWithStatus(Standort.VERKOSTET));
		alle.addAll(getAllLebkuchenWithStatus(Standort.ENTSORGT));
		alle.addAll(getAllLebkuchenWithStatus(Standort.KONTROLLIERT));
		alle.addAll(getAllLebkuchenWithStatus(Standort.VERPACKT));

		return alle;
	}
	
	private List<Lebkuchen> getAllLebkuchenWithStatus(Standort status){
		try {
			List<FifoSelector> selector = new ArrayList<FifoSelector>();
			selector.add(FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL));
			Space.getCapi().read(Space.createOrLookUpContainer(status), selector, 1000, null, IsolationLevel.REPEATABLE_READ, null);
			List<Lebkuchen> liste=  Space.getCapi().read(Space.createOrLookUpContainer(status),FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL),MzsConstants.RequestTimeout.INFINITE,null);
			for(Lebkuchen l : liste){
				l.setStatus(status.getName());
			}
			return liste;
		} catch (MzsCoreException e) {
			return null;
		}
	}
	
	@Override
	public List<ILebkuchen> getEntsorgtVerkostet() {
		List<ILebkuchen> liste = new ArrayList<ILebkuchen>(getAllLebkuchenWithStatus(Standort.ENTSORGT));
		liste.addAll(getAllLebkuchenWithStatus(Standort.VERKOSTET));
		return liste;
	}

	@Override
	public List<ILebkuchen> getImOfen() {
		return new ArrayList<ILebkuchen>(getAllLebkuchenWithStatus(Standort.OFEN));
	}

	@Override
	public List<ILebkuchen> getPackungen() {
		return new ArrayList<ILebkuchen>(getAllLebkuchenWithStatus(Standort.VERPACKT));
	}

	@Override
	public List<Zutat> getAllSchokolade() {
		List<Zutat> alleSchokolade = getAllZutatenByTyp(ZutatTypEnum.SCHOKOLADE);
		if(alleSchokolade==null){
			return schoko;
		}else{
			schoko = new ArrayList<Zutat>();
			schoko.addAll(alleSchokolade);
			return schoko;
		}
	}

	@Override
	public List<Zutat> getAllNuesse() {
		List<Zutat> alleNuesse = getAllZutatenByTyp(ZutatTypEnum.NUESSE);
		if(alleNuesse==null){
			return nuesse;
		}else{
			nuesse = new ArrayList<Zutat>();
			nuesse.addAll(alleNuesse);
			return nuesse;
		}
	}

	@Override
	public void erzeugeAuftrag(int packungungen, int normaleLebkuchen,
			int schokoLebkuchen, int nussLebkuchen) {
		Auftrag auftag = new Auftrag(auftragID++, packungungen, normaleLebkuchen, nussLebkuchen,schokoLebkuchen);
		try {
			Space.getCapi().write(new Entry(auftag,FifoCoordinator.newCoordinationData()), Space.createOrLookUpContainer(Standort.UNBEARBEITETE_AUFTRAEGE));
		} catch (MzsCoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<IAuftrag> getAllAuftraege() {
		try {
			List<FifoSelector> selector = new ArrayList<FifoSelector>();
			selector.add(FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL));
			List<Auftrag> liste=  Space.getCapi().read(Space.createOrLookUpContainer(Standort.UNBEARBEITETE_AUFTRAEGE),FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL),MzsConstants.RequestTimeout.INFINITE,null);
			ArrayList<IAuftrag> auftragliste= new ArrayList<IAuftrag>();
			auftragliste.addAll(liste);
			return auftragliste;
		} catch (MzsCoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ILebkuchen> getAllKontrolliert() {
		return new ArrayList<ILebkuchen>();
	}

}
