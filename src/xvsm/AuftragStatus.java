package xvsm;

public enum AuftragStatus {

	UNBEARBEITET("Unbearbeitet"),BEARBEITET("Bearbeitet"),FERTIG("Fertig");
	
	private String name;
	
	private AuftragStatus(String name){
		this.name= name;
	}
	
	public String getName(){
		return name;
	}
}
