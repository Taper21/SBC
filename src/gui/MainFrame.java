package gui;

import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	public MainFrame() throws HeadlessException {
		super("Lebkuchenfabrik");
		this.setLayout(new GridLayout(3, 1));
		this.add(new LieferantenPanel());
		this.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainFrame();
	}

}
