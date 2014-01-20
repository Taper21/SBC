package alternativ.domain;

import java.rmi.server.UID;

import domain.ILebkuchen;



public class Lebkuchen extends Resource implements ILebkuchen{
	
	private static final long serialVersionUID = 1L;
	private Status status;
	private String chargeUid;
	private String honigUid;
	private String mehlUid;
	private String ei1Uid;
	private String ei2Uid;
	private String baeckerId;
	private String logistikMitarbeiterId;
	private String qualitaetsMitarbeiterId;
	private String packungId;

	public Lebkuchen(Status status, Resource honig, Resource mehl,
			Resource ei1, Resource ei2, String baeckerId) {
		super();
		this.status = status;
//		this.chargeUid = chargeUid;
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
		this.packungId= packungId;
	}
	@Override
	public String getVerpackungId() {
		return packungId;
	}

	@Override
	public String getSchokoId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNussId() {
		// TODO Auto-generated method stub
		return null;
	}
}
