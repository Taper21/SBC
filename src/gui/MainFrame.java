package gui;

import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.text.TabExpander;

import alternativ.mitarbeiter.AlternativZutatenManager;

import xvsm.GUIDataMangerXVSMImpl;

public class MainFrame extends JFrame {

	public MainFrame() throws HeadlessException {
		super("Lebkuchenfabrik");
		this.setLayout(new GridLayout(3, 1));
		this.add(new LieferantenPanel());
		JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
		tabs.add("Spaced", new ZutatenLagerAnzeige(new GUIDataMangerXVSMImpl(), "XVSM Zutaten:"));
		tabs.add("Alternativ", new ZutatenLagerAnzeige(new AlternativZutatenManager(), "Alternativ Zutaten:"));	
		this.add(tabs);
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
