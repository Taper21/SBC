package domain;

import gui.ZutatenLagerAnzeige;

public class Notification {
	
	private ZutatenLagerAnzeige zutatenLagerAnzeige;
	
	public Notification(){
		
	}
	
	
	public void addZutat(Zutat z){
		zutatenLagerAnzeige.addZutat(z);
	}
	
	public void deleteZutat(Zutat z){
		zutatenLagerAnzeige.deleteZutat(z);
	}


	public void setZutatenLagerAnzeige(ZutatenLagerAnzeige zutatenLagerAnzeige) {
		this.zutatenLagerAnzeige = zutatenLagerAnzeige;
	}

}
