package xvsm;

public enum Standort {
	
	MEHLLAGER("MehlLager"),HONIGLAGER("HonigLager"),EIERLAGER("EierLager");
	
	private String name;
	
	private Standort(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}

}