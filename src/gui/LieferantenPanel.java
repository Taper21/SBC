package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class LieferantenPanel extends JPanel {

	public LieferantenPanel() {
		super(new GridLayout(0, 2));
		this.setPreferredSize(new Dimension(0 , 100));
		LieferantenAnzeigePanel right = new LieferantenAnzeigePanel();
		NeueLieferantenPanel left =new NeueLieferantenPanel(right); 
		this.add(left);
		this.add(right);
	}
	

}
