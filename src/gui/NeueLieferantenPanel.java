package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NeueLieferantenPanel extends JPanel {
	
	private final static String honig = "Honig";
	private final static String mehl = "Mehl";
	private final static String eier = "Eier";
	String[] zutatenArray = {honig,mehl,eier};
	
	JTextField anzahlTextfield = new JTextField(5);
	JComboBox zutatComboBox = new JComboBox(zutatenArray);
	JButton addLieferantButton = new JButton("+Lieferant");
	JButton startAllLieferanten = new JButton("Start");
	JLabel fehlerLabel = new JLabel("ERROR");
	LieferantenAnzeigePanel anzeigePanel;

	public NeueLieferantenPanel(final LieferantenAnzeigePanel anzeigePanel) {
		super(new GridLayout(4,2));
		this.setBackground(Color.GRAY);
		this.add(new JLabel("Anzahl:"));
		this.add(anzahlTextfield);
		this.add(new JLabel("Zutat:"));
		this.add(zutatComboBox);
		this.add(addLieferantButton);
		this.add(startAllLieferanten);
		fehlerLabel.setForeground(Color.RED);
		fehlerLabel.setVisible(false);
		this.add(fehlerLabel);
		this.anzeigePanel = anzeigePanel;
		addLieferantButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					Integer.parseInt(anzahlTextfield.getText());
					anzeigePanel.addLieferant(anzahlTextfield.getText(), ((String)zutatComboBox.getSelectedItem()));
					fehlerLabel.setVisible(true);
					fehlerLabel.setVisible(false);
				}catch(Exception ex){
					fehlerLabel.setVisible(true);
				}
			}
		});
	}


}
