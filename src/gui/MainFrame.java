package gui;

import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import alternativ.mitarbeiter.AlternativZutatenManager;

import xvsm.ZutatenMangerXVSMImpl;

public class MainFrame extends JFrame {

	public MainFrame() throws HeadlessException {
		super("Lebkuchenfabrik");
		this.setLayout(new GridLayout(3, 1));
		this.add(new LieferantenPanel());
		
		JPanel zutatenPanel = new JPanel(new GridLayout(1, 2));
		zutatenPanel.add(new ZutatenLagerAnzeige(new ZutatenMangerXVSMImpl(), "XVSM Zutaten:"));
		zutatenPanel.add(new ZutatenLagerAnzeige(new AlternativZutatenManager(), "Alternativ Zutaten:"));
		
		this.add(zutatenPanel);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainFrame();
	}

}
