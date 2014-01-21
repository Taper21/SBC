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

	public List<Auftrag> getNichtAngefangeneAuftraege() {
		List<Auftrag> returnValue = new ArrayList<Auftrag>();
		for(Auftrag auftrag: priority){
			if(!auftrag.isAngefangen()&&!auftrag.isBeendet()){
				returnValue.add(auftrag);
			}
		}
		return returnValue;
	}

	public Auftrag getNichtAngefangenerAuftrag() {
		List<Auftrag> possible = getNichtAngefangeneAuftraege();
		if(!possible.isEmpty()){
			return possible.get(0);
		}
		return null;
	}
}
