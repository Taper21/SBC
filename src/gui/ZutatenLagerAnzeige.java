package gui;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.ScrollPane;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import domain.Notification;
import domain.Zutat;
import domain.ZutatTypEnum;
import domain.ZutatenManager;

public class ZutatenLagerAnzeige extends JPanel {
	
	String[] columnames = new String[]{"ID","Zutat","ID Lieferant"};
	String [][] data =new String[][]{{"","",""}};
	private JTable zutatenTable = new JTable(data,columnames);
	JScrollPane scrollpane =new JScrollPane(zutatenTable);
	HashMap<Long,Zutat> angezeigteZutaten = new HashMap<Long,Zutat>();

	public ZutatenLagerAnzeige(ZutatenManager zutatenManager, String name) {
		super(new GridLayout(0, 1));
		refreshpanel();
	}
	
	private void refreshpanel(){
		this.removeAll();
		zutatenTable = new JTable(data,columnames);
		scrollpane =new JScrollPane(zutatenTable);
		zutatenTable.setEnabled(false);
		this.add(scrollpane);
		this.repaint();
		this.setVisible(true);
		
	}
	
	public void addZutat(Zutat z){
		angezeigteZutaten.put(z.getId(), z);
		data = mapToDataArray();
		refreshpanel();
	}
	
	public void deleteZutat(Zutat z){
		angezeigteZutaten.remove(z.getId());
		data = mapToDataArray();
		refreshpanel();
	}
	
	private String[][] mapToDataArray(){
		String[][] newData = new String[angezeigteZutaten.size()][3];
		int row = 0;
		for(Zutat z: angezeigteZutaten.values()){
			newData[row][0] = z.getId()+"";
			newData[row][1] = z.getZutatTypEnum().toString();
			newData[row][2] = z.getLieferant().getId()+"";
		}
		return newData;
	}
	
	
	
	
	
	

}
