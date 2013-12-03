package alternativ.mitarbeiter;

import java.util.ArrayList;
import java.util.List;

import alternativ.domain.Lebkuchen;
import alternativ.domain.Resource;

public class Packung extends Resource {

	List<Lebkuchen> lebkuchen = new ArrayList<Lebkuchen>();
	
	public void add(Lebkuchen l) {
		lebkuchen.add(l);
	}

	public int size() {
		return lebkuchen.size();
	}

}
