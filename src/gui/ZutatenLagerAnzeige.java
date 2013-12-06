package gui;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import domain.Zutat;
import domain.ZutatTypEnum;
import domain.GUIDataManager;

public class ZutatenLagerAnzeige extends JPanel {
	private static final long serialVersionUID = 1L;
	String[] columnames = new String[]{"ID","Zutat","ID Lieferant"};
	String [][] data =new String[][]{{"","",""}};
	private DefaultTableModel model = new DefaultTableModel(data,columnames);
	private JTable zutatenTable = new JTable(model);
	JScrollPane scrollpane =new JScrollPane(zutatenTable);
	List<Zutat> angezeigteZutaten = new ArrayList<Zutat>();
	String[] lebkuchenColumnames = new String[]{"ID","Status","Charge-ID", "Mehl-ID", "Honig-ID", "Ei1-ID", "Ei2-ID", "Bäcker-ID", ""};
	String [][] lebkuchenData =new String[][]{{"","",""}};
	private DefaultTableModel lebkuchenModel = new DefaultTableModel(lebkuchenData,columnames);
	private JTable lebkuchenTable = new JTable(lebkuchenModel);
	

	public ZutatenLagerAnzeige(GUIDataManager zutatenManager, String name) {
		super(new GridLayout(2, 1), true);
		this.add(scrollpane);
		this.add(new JScrollPane(new JTable(new DefaultTableModel())));
		ZutatenLagerAnzeigenThread zutatenLagerAnzeigenThread = new ZutatenLagerAnzeigenThread(zutatenManager, this);
		zutatenLagerAnzeigenThread.start();
//		new ZutatenLagerAnzeigenThread(zutatenManager, this);
	}
	
	
	
	private String[][] mapToDataArray(){
		String[][] newData = new String[angezeigteZutaten.size()][3];
		int row = 0;
		for(Zutat z: angezeigteZutaten){
			newData[row][0] = z.getId()+"";
			newData[row][1] = z.getZutatTypEnum().toString();
			newData[row][2] = z.getLieferant()+"";
			row++;
		}
		return newData;
	}

	public void setData(List<Zutat> spaceZutaten, int mehlcount, int honigcount, int eiercount) {
		angezeigteZutaten = spaceZutaten;
		data = mapToDataArray();
		columnames = new String[]{"ID","Mehl("+mehlcount+") Honig("+honigcount+") Eier("+eiercount+")","ID Lieferant ("+")"};
		model.setDataVector(data, columnames);
		model.fireTableDataChanged();
//		zutatenTable = new JTable(data,columnames);
//		scrollpane =new JScrollPane(zutatenTable);
//		this.removeAll();
//		this.add(scrollpane);
//		scrollpane.repaint();
	}

}
