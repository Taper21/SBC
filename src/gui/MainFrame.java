package gui;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.text.TabExpander;

import domain.GUIDataManager;

import alternativ.mitarbeiter.AlternativZutatenManager;

import xvsm.GUIDataMangerXVSMImpl;
import xvsm.Space;

public class MainFrame extends JFrame {

	public MainFrame(String[] args) throws HeadlessException {
		super("Lebkuchenfabrik");
		int port = Integer.parseInt(args[1]);
		Space.setPort(port);
		GUIDataManager spaced = new GUIDataMangerXVSMImpl();
		GUIDataManager alternativ = new AlternativZutatenManager();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());
		this.add(new LieferantenPanel(spaced,alternativ), BorderLayout.NORTH);
		JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
		tabs.add("Spaced", new ZutatenLagerAnzeige(spaced, "XVSM Zutaten:"));
		tabs.add("Alternativ", new ZutatenLagerAnzeige(alternativ, "Alternativ Zutaten:"));	
		this.add(tabs, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainFrame(args);
	}

}
