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

import alternativ.domain.Lebkuchen;

import domain.ILebkuchen;
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
	String[] lebkuchenColumnames = new String[]{"ID","Status","Charge-ID", "Mehl-ID", "Honig-ID", "Ei1-ID", "Ei2-ID", "Bäcker-ID", "Logistikmitarbeiter-ID", "QualitätMitarbeiter-ID"};
	String [][] lebkuchenData =new String[][]{{"","",""}};
	private DefaultTableModel lebkuchenModel = new DefaultTableModel(lebkuchenData,lebkuchenColumnames);
	private JTable lebkuchenTable = new JTable(lebkuchenModel);
	JScrollPane lebkuchenScrollPane =new JScrollPane(lebkuchenTable);
	

	public ZutatenLagerAnzeige(GUIDataManager zutatenManager, String name) {
		super(new GridLayout(2, 1), true);
		this.add(scrollpane);
		this.add(lebkuchenScrollPane);
		ZutatenLagerAnzeigenThread zutatenLagerAnzeigenThread = new ZutatenLagerAnzeigenThread(zutatenManager, this);
		zutatenLagerAnzeigenThread.start();
//		new ZutatenLagerAnzeigenThread(zutatenManager, this);
	}
	
	
	
	private String[][] zutatenMapToDataArray(List<Zutat> zutaten){
		String[][] newData = new String[zutaten.size()][3];
		int row = 0;
		for(Zutat z: zutaten){
			newData[row][0] = z.getId()+"";
			newData[row][1] = z.getZutatTypEnum().toString();
			newData[row][2] = z.getLieferant()+"";
			row++;
		}
		return newData;
	}
	private String[][] lebkuchenMapToDataArray(List<ILebkuchen> lebkuchen){
		String[][] newData = new String[lebkuchen.size()][lebkuchenColumnames.length];
		int row = 0;
		for(ILebkuchen z: lebkuchen){
			newData[row][0] = z.getId()+"";
			newData[row][1] = z.getStatus();
			newData[row][2] = z.getChargeId();
			newData[row][3] = z.getMehlId();
			newData[row][4] = z.getHonigId();
			newData[row][5] = z.getEi1Id();
			newData[row][6] = z.getEi2Id();
			newData[row][7] = z.getBaeckerId();
			newData[row][8] = z.getLogistikMitarbeiterId();
			newData[row][9] = z.getQualitaetMitarbeiterId();
			row++;
		}
		return newData;
	}

	public void setData(List<Zutat> zutaten, int mehlcount, int honigcount, int eiercount, List<ILebkuchen> lebkuchen) {
		data = zutatenMapToDataArray(zutaten);
		columnames = new String[]{"ID","Mehl("+mehlcount+") Honig("+honigcount+") Eier("+eiercount+")","ID Lieferant ("+")"};
		model.setDataVector(data, columnames);
		model.fireTableDataChanged();
		lebkuchenModel.setDataVector(lebkuchenMapToDataArray(lebkuchen), lebkuchenColumnames);
		lebkuchenModel.fireTableDataChanged();
//		zutatenTable = new JTable(data,columnames);
//		scrollpane =new JScrollPane(zutatenTable);
//		this.removeAll();
//		this.add(scrollpane);
//		scrollpane.repaint();
	}

}
