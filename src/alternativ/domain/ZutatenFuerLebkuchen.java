package alternativ.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ZutatenFuerLebkuchen extends Resource {
	
	public ZutatenFuerLebkuchen(Set<Set<AlternativZutat>> zutatenSet){
		this.zutaten = zutatenSet;
	}
	
	final private Set<Set<AlternativZutat>> zutaten;

	
	public Set<Set<AlternativZutat>> getZutaten() {
		return zutaten;
	}

	static AtomicInteger id = new AtomicInteger(0);
	@Override
	String getNextId() {
		return ""+id.incrementAndGet();
	}
}
