package xvsm;

import java.util.ArrayList;
import java.util.List;

import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsCoreException;

import domain.Zutat;
import domain.ZutatTypEnum;
import domain.ZutatenManager;

public class ZutatenMangerXVSMImpl implements ZutatenManager {

	@Override
	public List<Zutat> getAllZutatenByTyp(ZutatTypEnum zutatTypEnum) {
		try {
			return Space.getCapi().read(Space.createOrLookUpContainer(Space.getLager(zutatTypEnum) ),FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL),500,null);
		} catch (MzsCoreException e) {
			e.printStackTrace();
		} 
		return new ArrayList<Zutat>();
	
	}

}
