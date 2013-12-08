package alternativ.domain;

import java.rmi.server.UID;

import domain.ILebkuchen;



public class Lebkuchen extends Resource implements ILebkuchen{
	
	private static final long serialVersionUID = 1L;
	private Status status;
	private UID chargeUid;
	private UID honigUid;
	private UID mehlUid;
	private UID ei1Uid;
	private UID ei2Uid;
	private String baeckerId;
	private String logistikMitarbeiterId;
	private String qualitaetsMitarbeiterId;

	public Lebkuchen(Status status, UID chargeUid, Resource honig, Resource mehl,
			Resource ei1, Resource ei2, String baeckerId) {
		super();
		this.status = status;
		this.chargeUid = chargeUid;
		this.honigUid = honig.uid;
		this.mehlUid = mehl.uid;
		this.ei1Uid = ei1.uid;
		this.ei2Uid = ei2.uid;
		this.baeckerId = baeckerId;
	}

	public enum Status{
		GEFERTIGT,IN_OFEN,GEBACKEN,KONTROLLIERT,WEGGESCHMISSEN, GEGESSEN;
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
}
