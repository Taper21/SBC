package gui;

public class ZutatenLagerAnzeigenThread extends Thread {
	
	private ZutatenLagerAnzeige zutatenLagerAnzeige;
	
	public ZutatenLagerAnzeigenThread(ZutatenLagerAnzeige zutatenLagerAnzeige){
		this.zutatenLagerAnzeige =zutatenLagerAnzeige;
	}
	
	public void run(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		zutatenLagerAnzeige.refreshPanel();
	}

}
