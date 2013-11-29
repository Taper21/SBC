package gui;

import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Zutat;
import domain.ZutatTypEnum;
import domain.ZutatenManager;

public class ZutatenLagerAnzeige extends JPanel {
	
	ZutatenManager zutatenManager;
	String name;

	public ZutatenLagerAnzeige(ZutatenManager zutatenManager, String name) {
		super(new GridLayout(1, 0));
		this.zutatenManager = zutatenManager;
		this.name =name;
		new ZutatenLagerAnzeigenThread(this).start();
		
	}
	
	private void printZutaten(ZutatTypEnum zutatTypEnum){
		for(Zutat z: zutatenManager.getAllZutatenByTyp(zutatTypEnum)){
			this.add(new JLabel(z.getZutatTypEnum() + "#" + z.getId() + " von Lieferant:" + z.getLieferant().getId()));
		}
	}
	public void refreshPanel(){
		this.removeAll();
		this.add(new JLabel(name));
		printZutaten(ZutatTypEnum.HONIG);
		printZutaten(ZutatTypEnum.MEHL);
		printZutaten(ZutatTypEnum.EI);
		this.repaint();
	}
	
	

}
