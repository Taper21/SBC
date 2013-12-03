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



}