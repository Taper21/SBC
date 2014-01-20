package domain;

public enum ZutatTypEnum {
	HONIG("Honig"),MEHL("Mehl"),EI("Eier"),NUESSE("Nüsse"),SCHOKOLADE("Schokolade");
	
	private String name;
	
	private ZutatTypEnum(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
	
	public String toString(){
		return name;
	}
}
