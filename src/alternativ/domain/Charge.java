package alternativ.domain;

import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;
import java.util.List;


public class Charge extends Resource implements Serializable {
	

	public static int MAX_SIZE = 5;

	private static final long serialVersionUID = 1L;
	List<Lebkuchen> charge = new ArrayList<Lebkuchen>();
	
	public boolean add(Lebkuchen lebkuchen) {
		if(isVoll()){
			logger.info("charge ist bereits voll");
			return false;
		}
		charge.add(lebkuchen);
		try {
			Thread.sleep((new Random().nextInt()%1000) + 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public int size(){
		return charge.size();
	}

	public boolean isVoll() {
		return size()==MAX_SIZE;
	}

	public boolean isLeer() {
		return size()==0;
	}

}
