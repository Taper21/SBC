package domain;

import java.util.List;

public interface ZutatenManager {
	
	public List<Zutat> getAllZutatenByTyp(ZutatTypEnum zutatTypEnum);

}
