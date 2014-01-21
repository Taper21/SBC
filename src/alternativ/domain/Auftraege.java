package alternativ.domain;

import java.util.ArrayList;
import java.util.List;

public class Auftraege extends Resource {
	private static final long serialVersionUID = 1L;

	public Auftraege(List<Auftrag> auftraege){
		this.priority = auftraege;
	}
	
	public List<Auftrag> getPriority() {
		return priority;
	}

	private final List<Auftrag> priority;
}
