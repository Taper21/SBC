package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import alternativ.mitarbeiter.AlternativLieferant;

import xvsm.XVSMLieferant;

import domain.Lieferant;
import domain.ZutatTypEnum;

public class NeueLieferantenPanel extends JPanel {
	
	ZutatTypEnum[] zutatenArray = {ZutatTypEnum.HONIG,ZutatTypEnum.MEHL,ZutatTypEnum.EI,ZutatTypEnum.SCHOKOLADE,ZutatTypEnum.NUESSE};
	
	JTextField anzahlTextfield = new JTextField(5);
	JComboBox zutatComboBox = new JComboBox(zutatenArray);
	JButton addLieferantButton = new JButton("+Lieferant");
	JButton startAllLieferanten = new JButton("Start");
	JLabel fehlerLabel = new JLabel("ERROR");
	JCheckBox spaceBasedCheckBox = new JCheckBox();
	LieferantenAnzeigePanel anzeigePanel;
	List<Lieferant> lieferanten = new ArrayList<Lieferant>();

	public NeueLieferantenPanel(final LieferantenAnzeigePanel anzeigePanel) {
		super(new GridLayout(5,2));
		this.setBackground(Color.GRAY);
		this.add(new JLabel("Anzahl:"));
		this.add(anzahlTextfield);
		this.add(new JLabel("Zutat:"));
		this.add(zutatComboBox);
		this.add(addLieferantButton);
		this.add(startAllLieferanten);
		this.add(new JLabel("Space-Based:"));
		this.add(spaceBasedCheckBox);
		fehlerLabel.setForeground(Color.RED);
		fehlerLabel.setVisible(false);
		this.add(fehlerLabel);
		this.anzeigePanel = anzeigePanel;
		addLieferantButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int anzahl = Integer.parseInt(anzahlTextfield.getText());
					ZutatTypEnum zutatTypEnum = (ZutatTypEnum)zutatComboBox.getSelectedItem();
					anzeigePanel.addLieferant(anzahlTextfield.getText(), zutatTypEnum.toString());
					lieferanten.add(new Lieferant(anzahl, zutatTypEnum));
					fehlerLabel.setVisible(true);
					fehlerLabel.setVisible(false);
				}catch(Exception ex){
					fehlerLabel.setVisible(true);
				}
			}
		});
		startAllLieferanten.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean isSpaceBased = spaceBasedCheckBox.isSelected();
				for(Lieferant l: lieferanten){
					if(isSpaceBased){
						XVSMLieferant lieferant = new XVSMLieferant(l.getAnzahl(), l.getZutatTypEnum());
						lieferant.start();
					}else{
						AlternativLieferant lieferant = new AlternativLieferant(l.getAnzahl(), l.getZutatTypEnum());
						lieferant.start();
					}
					
				}
				lieferanten.clear();
				anzeigePanel.clear();
			}
		});
	}


}
