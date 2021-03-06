package xvsm;

public enum Standort {
	
	MEHLLAGER("MehlLager"),HONIGLAGER("HonigLager"),EIERLAGER("EierLager"),SCHOKOLADENLAGER("SchokoladeLager"),NUESSELAGER("NuesseLager"),LEBKUCHEN_GEFERTIGT("GefertigteLebkuchen"),OFEN("Ofen"),GEBACKEN("Gebacken"),VERKOSTET("Verkostet"),ENTSORGT("Entsorgt"),KONTROLLIERT("Kontrolliert"),VERPACKT("Verpackt"),FERTIGE_AUFTRAEGE("FertigeAuftraege"),UNFERTIGE_AUFTRAEGE("UnFertigeAuftraege");
	
	private String name;
	
	private Standort(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}

}
