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

import alternativ.mitarbeiter.FabrikManager;

import xvsm.GUIDataMangerXVSMImpl;

public class MainFrame extends JFrame {

	private static String ort;

	public MainFrame() throws HeadlessException {
		super(getOrt());
		GUIDataManager spaced = new GUIDataMangerXVSMImpl();
		GUIDataManager alternativ = new FabrikManager();
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
		ort=args[0];
		new MainFrame();
	}

	public static String getOrt() {
		return ort;
	}

}
