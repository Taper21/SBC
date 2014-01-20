package alternativ.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Packung extends Resource {

	List<Lebkuchen> lebkuchen = new ArrayList<Lebkuchen>();
	
	public void add(Lebkuchen l) {
		lebkuchen.add(l);
	}

	public int size() {
		return lebkuchen.size();
	}

	public List<Lebkuchen> getAllLebkuchen() {
		return lebkuchen;
	}

	static AtomicInteger id = new AtomicInteger(0);
	@Override
	String getNextId() {
		return ""+id.incrementAndGet();
	}
}
