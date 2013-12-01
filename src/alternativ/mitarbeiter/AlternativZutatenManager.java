package alternativ.mitarbeiter;

import java.util.ArrayList;
import java.util.List;

import domain.Zutat;
import domain.ZutatTypEnum;
import domain.ZutatenManager;

public class AlternativZutatenManager implements ZutatenManager {

	@Override
	public List<Zutat> getAllZutatenByTyp(ZutatTypEnum zutatTypEnum) {
		return new ArrayList<Zutat>();
	}

}
