package alternativ.domain;

import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import alternativ.domain.Lebkuchen.Status;


public class Charge extends Resource implements Serializable {
	

	public static int MAX_SIZE = 5;

	private static final long serialVersionUID = 1L;
	List<Lebkuchen> charge = new ArrayList<Lebkuchen>();

	private Status status;
	
	public boolean add(Lebkuchen lebkuchen) {
		if(isVoll()){
			logger.info("charge ist bereits voll");
			return false;
		}
		charge.add(lebkuchen);
		try {
//			Benchmark
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

	public Lebkuchen takeRandomLebkuchen() {
		int i = Math.abs((new Random().nextInt()%(charge.size())));
		return charge.get(i);
	}

	public void setSchmecktSchlecht() {
		setStatus(Charge.Status.ABFALL);
		setStatusOfLebkuchen(alternativ.domain.Lebkuchen.Status.WEGGESCHMISSEN);
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status {
		ABFALL,OK;
	}

	public Status getStatus() {
		return status;
	}

	public List<Lebkuchen> getAll() {
		return charge;
	}

	public void setStatusOfLebkuchen(alternativ.domain.Lebkuchen.Status status) {
		for(Lebkuchen l:charge){
			if(l.getStatus().compareTo(Lebkuchen.Status.GEGESSEN.name())!=0){
				l.setStatus(status);
			}
		}
	}

	public void remove(Lebkuchen toRemove) {
		if(toRemove!=null){
			charge.remove(toRemove);
		}
	}

	public String getBaeckerId() {
		if(charge.size()>0){
			return charge.get(0).getBaeckerId();
		}
		return "";
	}
	

}
