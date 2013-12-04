package xvsm;

import java.io.Serializable;

public class Lebkuchen implements Serializable{
	
	private ZutatXVSMImpl honig;
	private ZutatXVSMImpl mehl;
	private ZutatXVSMImpl ei1;
	private ZutatXVSMImpl ei2;
	private long chargeID;
	
	public Lebkuchen(ZutatXVSMImpl honig, ZutatXVSMImpl mehl, ZutatXVSMImpl ei1, ZutatXVSMImpl ei2, long chargeID){
		this.honig=honig;
		this.mehl=mehl;
		this.ei1=ei1;
		this.ei2=ei2;
		this.chargeID =chargeID;
	}
	
	public ZutatXVSMImpl getHonigZutat(){
		return honig;
	}
	
	public ZutatXVSMImpl getMehlZutat(){
		return mehl;
	}
	
	public ZutatXVSMImpl getEi1Zutat(){
		return ei1;
	}
	
	public ZutatXVSMImpl getEi2Zutat(){
		return ei2;
	}
	
	public String toString(){
		return "Lebkuchen Honig#"+ honig.getId() +" Mehl#" + mehl.getId() + " Ei#" + ei1.getId() +" Ei#" + ei2.getId(); 
	}

}
