package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import domain.GUIDataManager;

public class LieferantenPanel extends JPanel {

	public LieferantenPanel(GUIDataManager spaced, GUIDataManager alternativ) {
		super(new GridLayout(0, 3));
		this.setPreferredSize(new Dimension(0 , 100));
		LieferantenAnzeigePanel middle = new LieferantenAnzeigePanel();
		NeueLieferantenPanel left =new NeueLieferantenPanel(middle); 
		NeuenAuftragPanel right = new NeuenAuftragPanel( spaced,  alternativ);
		this.add(left);
		this.add(middle);
		this.add(right);
	}
	

}
