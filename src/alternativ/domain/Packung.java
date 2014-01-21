package alternativ.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Packung extends Resource {

	List<Lebkuchen> lebkuchen = new ArrayList<Lebkuchen>();
	private String auftragId;
	
	public void add(Lebkuchen l) {
		lebkuchen.add(l);
	}

	public int size() {
		return lebkuchen.size();
	}

	public List<Lebkuchen> getAllLebkuchen() {
		return lebkuchen;
	}

	public void setAuftragId(String string) {
		this.auftragId = string;
	}

	public String getAuftragId() {
		return auftragId;
	}

	
}
