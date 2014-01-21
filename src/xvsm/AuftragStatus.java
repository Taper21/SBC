package xvsm;

public enum AuftragStatus {

	UNFERTIG("Unfertig"),FERTIG("Fertig");
	
	private String name;
	
	private AuftragStatus(String name){
		this.name= name;
	}
	
	public String getName(){
		return name;
	}
}
