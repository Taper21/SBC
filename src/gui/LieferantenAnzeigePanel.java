package gui;

import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LieferantenAnzeigePanel extends JPanel {
	
	public LieferantenAnzeigePanel() {
		super(new GridLayout(0,2));
	}
	
	public void addLieferant(String anzahl, String zutat){
		this.add(new JLabel(anzahl));
		this.add(new JLabel(zutat));
	}
}
