package alternativ.domain;

import java.rmi.server.UID;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;

import alternativ.domain.Lebkuchen.Status;

import domain.ILebkuchen;

public class Lebkuchen extends Resource implements ILebkuchen {

	private static final long serialVersionUID = 1L;
	private Status status;
	private String chargeUid;
	private String baeckerId;
	private String logistikMitarbeiterId;
	private String qualitaetsMitarbeiterId;
	private String packungId;
	private Set<AlternativZutat> alles;
	private String ei1Uid;
	private String ei2Uid;
	private String honigUid;
	private String mehlUid;
	private String nuesseUid;
	private String schokoladeUid;
	private String auftragUid;

	public Lebkuchen(Status gefertigt, Set<AlternativZutat> alles, String baeckerId) {
		status = gefertigt;
		this.alles = alles;
		this.baeckerId = baeckerId;
		boolean hastSchon = false;
		for (AlternativZutat x : alles) {
			switch (x.getZutatTypEnum()) {
			case EI:
				if (ei1Uid == null) {
					ei1Uid = x.getUID();
				} else {
					ei2Uid = x.getUID();
				}
				break;
			case HONIG:
				honigUid = x.getUID();
				break;
			case MEHL:
				mehlUid = x.getUID();
				break;
			case NUESSE:
				if(hastSchon){
					throw new IllegalArgumentException("Zuviele Zutaten");
				}
				hastSchon = true;
				nuesseUid = x.getUID();
				break;
			case SCHOKOLADE:
				if(hastSchon){
					throw new IllegalArgumentException("Zuviele Zutaten");
				}
				hastSchon = true;
				
				schokoladeUid = x.getUID();
				break;
			}
		}
	}

	public enum Status {
		GEFERTIGT, IN_OFEN, GEBACKEN, KONTROLLIERT, WEGGESCHMISSEN, GEGESSEN, WARTE_AUF_VERPACKUNG, VERPACKT;
	}

	@Override
	public String getId() {
		return getUID().toString();
	}

	@Override
	public String getStatus() {
		return status.name();
	}

	@Override
	public String getHonigId() {
		return honigUid.toString();
	}

	@Override
	public String getMehlId() {
		return mehlUid.toString();
	}

	@Override
	public String getEi1Id() {
		return ei1Uid.toString();
	}

	@Override
	public String getEi2Id() {
		return ei2Uid.toString();
	}

	@Override
	public String getChargeId() {
		return chargeUid.toString();
	}

	public void setChargeId(String chargeUid) {
		this.chargeUid = chargeUid;
	}

	@Override
	public String getBaeckerId() {
		return baeckerId;
	}

	@Override
	public String getLogistikMitarbeiterId() {
		return logistikMitarbeiterId;
	}

	@Override
	public String getQualitaetMitarbeiterId() {
		return qualitaetsMitarbeiterId;
	}

	public void setLogistikMitarbeiterId(String id) {
		this.logistikMitarbeiterId = id;
	}

	public void setQualitaetsMitarbeiterId(String id) {
		this.qualitaetsMitarbeiterId = id;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setVerpackungId(String packungId) {
		this.packungId = packungId;
	}

	@Override
	public String getVerpackungId() {
		return packungId;
	}

	@Override
	public String getSchokoId() {
		return schokoladeUid;
	}

	@Override
	public String getNussId() {
		return nuesseUid;
	}

	@Override
	public String getAuftragsId() {
		return getAuftragUid();
	}

	public Type getType() {
		if(StringUtils.isEmpty(getSchokoId())){
			if(StringUtils.isEmpty(getNussId())){
				return Type.Normal;
			}
			return Type.Nuss;
		}
		return Type.Schoko;
	}
	
	public String getAuftragUid() {
		return auftragUid;
	}

	public void setAuftragUid(String auftragUid) {
		this.auftragUid = auftragUid;
	}

	public enum Type {
		Schoko, Nuss, Normal;
	}
}
