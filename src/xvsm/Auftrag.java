package xvsm;

import java.io.Serializable;

import org.mozartspaces.capi3.Index;
import org.mozartspaces.capi3.Queryable;

import domain.IAuftrag;

@Queryable
public class Auftrag implements Serializable, IAuftrag {
	
	@Index
	private Long id;
	private Integer gesamtPackungsAnzahl=0;
	private Integer normaleLebkuchenAnzahl;
	private Integer nussLebkuchenAnzahl;
	private Integer schokoLebkuchenAnzahl;
	private AuftragStatus auftragStatus;
	private Integer erledigt;
	
	public Auftrag(Long id, Integer gesamtPackungsAnzahl, Integer normaleLebkuchenAnzahl,Integer nussLebkuchenAnzahl,Integer schokoLebkuchenAnzahl){
		this.id = id;
		this.gesamtPackungsAnzahl = gesamtPackungsAnzahl;
		this.normaleLebkuchenAnzahl =normaleLebkuchenAnzahl;
		this.nussLebkuchenAnzahl = nussLebkuchenAnzahl;
		this.schokoLebkuchenAnzahl = schokoLebkuchenAnzahl;
		this.auftragStatus = AuftragStatus.UNFERTIG;
		this.erledigt=0;
	}
	
	//For Linda
	public Auftrag(Long id){
		this.id = id;
	}

	@Override
	public String getID() {
		return id==null?"-":id.toString();
	}
	
	public Long getLongID() {
		return id;
	}
	
	public void setStatus(AuftragStatus status){
		this.auftragStatus = status;;
	}

	@Override
	public String getStatus() {
		if(auftragStatus.equals(AuftragStatus.FERTIG)){
			if(erledigt<0){
				return "Verschoben" + erledigt;
			}else{
				return "Erledigt";
			}
		}else{
			if(erledigt==0){
				return "Unbearbeitet";
			}else{
				return "In Bearbeitung";
			}
		}
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

	@Override
	public String getErledigtePackungen() {
		return erledigt.toString();
	}
	
	public AuftragStatus addErledigt(){
		erledigt = erledigt+1;
		if(erledigt>=gesamtPackungsAnzahl){
			this.auftragStatus=AuftragStatus.FERTIG;
		}else{
			this.auftragStatus=AuftragStatus.UNFERTIG;
		}
		return this.auftragStatus;
	}
	
	
	//For Linda
	public void setErledigt(Integer erledigt){
		this.erledigt = erledigt;
	}

}
