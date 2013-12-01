package alternativ.mitarbeiter;

import java.io.ObjectInputStream.GetField;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import domain.ZutatTypEnum;

import alternativ.domain.Resource;

public class Bäcker extends Mitarbeiter {



	private Resource honig = null;
	private Resource mehl = null;
	private Resource ei1 = null;
	private Resource ei2 = null;

	public Bäcker(String id){
		super("ZutatenLager", "Ofen", id);
	}
	
	
	public static void main(String[] args) {
		Bäcker bäcker = new Bäcker(args[0]);
		bäcker.getLogger().info("Bäcker started mit id: " + bäcker.getId());
		bäcker.waitTillResourceHere();
		bäcker.getLogger().info("Bäcker " + bäcker.getId() + "hat alle Zutaten");
	}
	


	private void waitTillResourceHere(){
		while(null == honig && null == mehl && null == ei1 && null == ei2){
			if(honig==null){
				honig = besorgeZutat(ZutatTypEnum.HONIG);
			}
			if(honig==null){
				honig = besorgeZutat(ZutatTypEnum.MEHL);
			}
			if(ei1==null){
				ei1 = besorgeZutat(ZutatTypEnum.EI);
			}
			if(ei2==null){
				ei2 = besorgeZutat(ZutatTypEnum.EI);
			}
		}
	}

	@Override
	public void verarbeiteZutat() {
		// TODO Auto-generated method stub

	}

	@Override
	public void gibZutatAb() {
		// TODO Auto-generated method stub

	}

}
