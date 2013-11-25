package domain;

public enum ZutatTypEnum {
	HONIG("Honig"),MEHL("Mehl"),EI("Eier");
	
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
