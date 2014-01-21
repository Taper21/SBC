package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

import domain.GUIDataManager;
import domain.Lieferant;
import domain.ZutatTypEnum;

public class NeuenAuftragPanel extends JPanel {
	
	JTextField anzahlTextfield = new JTextField(5);
	JCheckBox spaceBasedCheckBox = new JCheckBox();
	JTextField normalTextfield = new JTextField(5);
	JTextField schokoTextfield = new JTextField(5);
	JTextField nussTextfield = new JTextField(5);
	JButton submit = new JButton("Auftrag anlegen");
	JLabel fehlermeldung = new JLabel("FEHLER");
	
	public NeuenAuftragPanel(final GUIDataManager spaced, final GUIDataManager alternativ) {
		super(new GridLayout(6,2));
		this.setBackground(Color.LIGHT_GRAY);
		this.add(new JLabel("Anzahl(Packungen):"));
		this.add(anzahlTextfield);
		this.add(new JLabel("Anzahl(normale Lebkuchen):"));
		this.add(normalTextfield);
		this.add(new JLabel("Anzahl(Schokolebkuchen):"));
		this.add(schokoTextfield);
		this.add(new JLabel("Anzahl(NussLebkuchen):"));
		this.add(nussTextfield);
		this.add(new JLabel("Space-Based:"));
		this.add(spaceBasedCheckBox);
		this.add(submit);
		fehlermeldung.setForeground(Color.RED);
		fehlermeldung.setVisible(false);
		this.add(fehlermeldung);
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					String text = anzahlTextfield.getText();
					StringUtils.isEmpty(text);
					int anzahl = StringUtils.isEmpty(text)?0:Integer.parseInt(text);
					text = schokoTextfield.getText();
					int schoko = StringUtils.isEmpty(text)?0:Integer.parseInt(text);
					text = nussTextfield.getText();
					int nuss = StringUtils.isEmpty(text)?0:Integer.parseInt(text);
					text = normalTextfield.getText();
					int normal = StringUtils.isEmpty(text)?0:Integer.parseInt(text);
					if(schoko+nuss+normal == 6){
						fehlermeldung.setVisible(false);
						if(spaceBasedCheckBox.isSelected()){
							spaced.erzeugeAuftrag(anzahl, normal, schoko, nuss);
						}else{
							alternativ.erzeugeAuftrag(anzahl, normal, schoko, nuss);
						}
					}else{
						fehlermeldung.setText("In einer Packung müssen 6 Lebkuchen sein");
						fehlermeldung.setVisible(true);
					}
						
				}catch(NumberFormatException nfx){
					fehlermeldung.setText("Nur Zahlen als Eingabe möglich");
					fehlermeldung.setVisible(true);
				}
			}
		});
		
	}

}
