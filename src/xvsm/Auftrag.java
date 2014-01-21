package xvsm;

import java.io.Serializable;

import domain.IAuftrag;

public class Auftrag implements Serializable, IAuftrag {
	
	private Long id;
	private Integer gesamtPackungsAnzahl;
	private Integer normaleLebkuchenAnzahl;
	private Integer nussLebkuchenAnzahl;
	private Integer schokoLebkuchenAnzahl;
	private AuftragStatus auftragStatus;
	
	public Auftrag(Long id, Integer gesamtPackungsAnzahl, Integer normaleLebkuchenAnzahl,Integer nussLebkuchenAnzahl,Integer schokoLebkuchenAnzahl){
		this.id = id;
		this.gesamtPackungsAnzahl = gesamtPackungsAnzahl;
		this.normaleLebkuchenAnzahl =normaleLebkuchenAnzahl;
		this.nussLebkuchenAnzahl = nussLebkuchenAnzahl;
		this.schokoLebkuchenAnzahl = schokoLebkuchenAnzahl;
		this.auftragStatus = AuftragStatus.UNBEARBEITET;
	}

	@Override
	public String getID() {
		return id==null?"-":id.toString();
	}

	@Override
	public String getStatus() {
		return auftragStatus==null?"-":auftragStatus.getName();
	}

	@Override
	public String getGesamtPackungszahl() {
		return gesamtPackungsAnzahl==null?"-":gesamtPackungsAnzahl.toString();
	}

	@Override
	public String getNormaleLebkuchenAnzahl() {
		return normaleLebkuchenAnzahl==null?"-":normaleLebkuchenAnzahl.toString();
	}

	@Override
	public String getNussLebkuchenAnzahl() {
		return nussLebkuchenAnzahl==null?"-":nussLebkuchenAnzahl.toString();
	}

	@Override
	public String getSchokoLebkuchenAnzahl() {
		return schokoLebkuchenAnzahl==null?"-":schokoLebkuchenAnzahl.toString();
	}

}
