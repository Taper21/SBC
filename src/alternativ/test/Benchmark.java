package alternativ.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import alternativ.anlagen.Logistik;
import alternativ.anlagen.Qualitaetskontrolle;
import alternativ.mitarbeiter.Baecker;
import alternativ.mitarbeiter.LogistikMitarbeiter;
import alternativ.mitarbeiter.QualitaetsKontrolleur;
import xvsm.Space;
import gui.MainFrame;

public class Benchmark {

	public static void main(String[] args) throws InterruptedException, IOException{
		MainFrame.ort=args[0];
		MainFrame.port= Integer.parseInt(args[1]);
		Space.setPort(MainFrame.port);
		new MainFrame();
		
		
		final Baecker baecker = new Baecker("1", MainFrame.ort);
		final Baecker baecker2 = new Baecker("2", MainFrame.ort);
		final QualitaetsKontrolleur qualitaetsKontrolleur = new QualitaetsKontrolleur("1","0", MainFrame.ort);
		final LogistikMitarbeiter logistikMitarbeiter = new LogistikMitarbeiter("1", MainFrame.ort);
		System.out.println("gib go zeichen");
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String s = bufferRead.readLine();
	 
		    System.out.println(s);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("start");
				baecker.start();
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				baecker2.start();
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				qualitaetsKontrolleur.start();
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				logistikMitarbeiter.start();
			}
		}).start();
		
		System.out.println("Nur noch 60 Sekunden");
		Thread.sleep(60000);
		System.out.println("AUS");
		baecker.stop();
		baecker2.stop();
		qualitaetsKontrolleur.stop();
		logistikMitarbeiter.stop();
		
	}
	
}
