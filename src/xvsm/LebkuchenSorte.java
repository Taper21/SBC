package xvsm;

public enum LebkuchenSorte {
	
	SCHOKOLEBKUCHEN(1),NUSSLEBKUCHEN(2),LEBKUCHEN(0);
	private Integer lvalue;
	
	private LebkuchenSorte(Integer lvalue) {
	 this.lvalue = lvalue;
	}
	
	public Integer getLValue(){
		return lvalue;
	}

}
