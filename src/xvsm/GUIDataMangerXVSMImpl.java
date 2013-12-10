package xvsm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.aspects.AbstractContainerAspect;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.Operation;
import org.xvsm.protocol.FifoSelector;

import domain.ILebkuchen;
import domain.Zutat;
import domain.ZutatTypEnum;
import domain.GUIDataManager;

public class GUIDataMangerXVSMImpl implements GUIDataManager {
	
	ArrayList<Zutat> allezutaten = new ArrayList<Zutat>();
	
	@Override
	public List<Zutat> getAllHonig(){
		return getAllZutatenByTyp(ZutatTypEnum.HONIG);
	}
	
	@Override
	public List<Zutat> getAllMehl(){
		return getAllZutatenByTyp(ZutatTypEnum.MEHL);
	}

	@Override
	public List<Zutat> getAllEier(){
		return getAllZutatenByTyp(ZutatTypEnum.EI);
}
	
	private ArrayList<Zutat> getAllZutatenByTyp(ZutatTypEnum zutatTypEnum){
		//if(Space.getCore()!=null){
		try {
			return Space.getCapi().read(Space.createOrLookUpContainer(Space.getLager(zutatTypEnum) ),FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL),500,null);
		} catch (MzsCoreException e) {
			e.printStackTrace();
		} //}
		
		return new ArrayList<Zutat>();
	
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
			List<Lebkuchen> liste=  Space.getCapi().read(Space.createOrLookUpContainer(status),FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL),MzsConstants.RequestTimeout.INFINITE,null);
			for(Lebkuchen l : liste){
				l.setStatus(status.getName());
			}
			return liste;
		} catch (MzsCoreException e) {
			return new ArrayList<Lebkuchen>();
		}
	}
	
	

	@Override
	public List<ILebkuchen> getEntsorgtVerkostet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ILebkuchen> getImOfen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ILebkuchen> getPackungen() {
		// TODO Auto-generated method stub
		return null;
	}



}
