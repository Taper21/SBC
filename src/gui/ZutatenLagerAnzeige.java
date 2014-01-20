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
import javax.swing.JTabbedPane;
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
	String[] lebkuchenColumnames = new String[]{"ID","Status","Charge-ID", "Mehl-ID", "Honig-ID", "Ei1-ID", "Ei2-ID","Schocko-ID", "Nuss-ID", "Bäcker-ID", "Logistikmitarbeiter-ID", "QualitätMitarbeiter-ID", "Verpackung-ID","Auftrags-ID"};
	private DefaultTableModel modelEntsorgtVerkostet = new DefaultTableModel();
	String [][] lebkuchenData =new String[][]{{"","",""}};
	private DefaultTableModel lebkuchenModel = new DefaultTableModel(lebkuchenData,lebkuchenColumnames);
	private JTable lebkuchenTable = new JTable(lebkuchenModel);
	JScrollPane lebkuchenScrollPaneInProduction =new JScrollPane(lebkuchenTable);
	JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
	private DefaultTableModel modelImOfen= new DefaultTableModel();
	protected DefaultTableModel modelPackungen= new DefaultTableModel();
	

	public ZutatenLagerAnzeige(GUIDataManager zutatenManager, String name) {
		super(new GridLayout(2, 1), true);
		this.add(scrollpane);
		tabs.add("alle",lebkuchenScrollPaneInProduction);
		tabs.add("gegessen/weggeworfen",new JScrollPane((new JTable(modelEntsorgtVerkostet))));
		tabs.add("Ofen",new JScrollPane(new JTable(modelImOfen)));
		tabs.add("Packungen",new JScrollPane(new JTable(modelPackungen)));
		this.add(tabs);
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
			int colum = 0;
			newData[row][colum++] = z.getId()+"";
			newData[row][colum++] = z.getStatus();
			newData[row][colum++] = z.getChargeId();
			newData[row][colum++] = z.getMehlId();
			newData[row][colum++] = z.getHonigId();
			newData[row][colum++] = z.getEi1Id();
			newData[row][colum++] = z.getEi2Id();
			newData[row][colum++] = z.getSchokoId();
			newData[row][colum++] = z.getNussId();
			newData[row][colum++] = z.getBaeckerId();
			newData[row][colum++] = z.getLogistikMitarbeiterId();
			newData[row][colum++] = z.getQualitaetMitarbeiterId();
			newData[row][colum++] = z.getVerpackungId();
			newData[row][colum] = z.getAuftragsId();
			row++;
		}
		return newData;
	}

	public void setData(final List<Zutat> zutaten,final int mehlcount,final int honigcount,final int eiercount,final int schokocount, final int nuessecount,final List<ILebkuchen> lebkuchen) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				data = zutatenMapToDataArray(zutaten);
				columnames = new String[]{"ID","Mehl("+mehlcount+") Honig("+honigcount+") Eier("+eiercount+") Schockolade("+schokocount+") Nüsse("+nuessecount+")","ID Lieferant ("+")"};
				model.setDataVector(data, columnames);
				model.fireTableDataChanged();
				lebkuchenModel.setDataVector(lebkuchenMapToDataArray(lebkuchen), lebkuchenColumnames);
//				lebkuchenModel = new DefaultTableModel(lebkuchenMapToDataArray(lebkuchen), lebkuchenColumnames);
//				lebkuchenTable.setModel(lebkuchenModel);
				lebkuchenModel.fireTableDataChanged();
//				zutatenTable = new JTable(data,columnames);
//				scrollpane =new JScrollPane(zutatenTable);
//				this.removeAll();
//				this.add(scrollpane);
//				scrollpane.repaint();
				tabs.setTitleAt(0, "alle Lebkuchen: " + lebkuchen.size());
			}
		});
		
	}
	
	public void setData(final List<ILebkuchen> entsorgtVerkostet){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				modelEntsorgtVerkostet.setDataVector(lebkuchenMapToDataArray(entsorgtVerkostet), lebkuchenColumnames);
				modelEntsorgtVerkostet.fireTableDataChanged();
				tabs.setTitleAt(1, "gegessen/weggeworfen: " + entsorgtVerkostet.size());
			}
		});
	}



	public void setDataOfen(final List<ILebkuchen> imOfen) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				modelImOfen.setDataVector(lebkuchenMapToDataArray(imOfen), lebkuchenColumnames);
				modelImOfen.fireTableDataChanged();
				tabs.setTitleAt(2, "im Ofen: " + imOfen.size());
			}
		});
	}



	public void setDataPackungen(final List<ILebkuchen> packungen) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				modelPackungen.setDataVector(lebkuchenMapToDataArray(packungen), lebkuchenColumnames);
				modelPackungen.fireTableDataChanged();
				tabs.setTitleAt(3, "verpackte Lebkuchen : " + packungen.size());
			}
		});
	}

}
