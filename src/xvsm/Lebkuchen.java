package xvsm;

import java.io.Serializable;

import org.mozartspaces.capi3.Index;
import org.mozartspaces.capi3.Queryable;

import domain.ILebkuchen;

@Queryable
public class Lebkuchen implements Serializable,ILebkuchen{
	
	private ZutatXVSMImpl honig;
	private ZutatXVSMImpl mehl;
	private ZutatXVSMImpl ei1;
	private ZutatXVSMImpl ei2;
	
	@Index
	private LebkuchenSorte sorte;
	
	private ZutatXVSMImpl schoko;
	private ZutatXVSMImpl nuss;
	private String status;
	
	private static long idCounter = 0;
	
	private long id;
	
	@Index
	private Long chargeID;
	
	@Index
	private String baeckerID;
	
	private String kontrolleurID;
	
	private String logistikID;
	
	private Long verpackungsID;
	
	private Long auftragsID;
	
	public Lebkuchen(ZutatXVSMImpl honig, ZutatXVSMImpl mehl, ZutatXVSMImpl ei1, ZutatXVSMImpl ei2, long chargeID, String baeckerID){
		this.honig=honig;
		this.mehl=mehl;
		this.ei1=ei1;
		this.ei2=ei2;
		this.chargeID =chargeID;
		this.baeckerID = baeckerID;
		this.id = idCounter++;
	}
	
	//For LindaSelector
	public Lebkuchen(long chargeID, String baeckerID){
		this.chargeID = chargeID;
		this.baeckerID = baeckerID;
	}
	
	//For LindaSelector
		public Lebkuchen(LebkuchenSorte sorte){
			this.sorte=sorte;
		}
	
	public ZutatXVSMImpl getHonigZutat(){
		return honig;
	}
	
	public ZutatXVSMImpl getMehlZutat(){
		return mehl;
	}
	
	public ZutatXVSMImpl getEi1Zutat(){
		return ei1;
	}
	
	public ZutatXVSMImpl getEi2Zutat(){
		return ei2;
	}
	
	public String toString(){
		return "Lebkuchen Charge#" + chargeID + " Honig#"+ honig.getId() +" Mehl#" + mehl.getId() + " Ei#" + ei1.getId() +" Ei#" + ei2.getId(); 
	}

	@Override
	public String getId() {
		return this.id+"";
	}

	@Override
	public String getStatus() {
		return status==null? "":status;
	}
	
	public void setStatus(String status){
		this.status=status;
	}

	@Override
	public String getHonigId() {
		return this.getHonigZutat().getId();
	}

	@Override
	public String getMehlId() {
		return this.getMehlZutat().getId();
	}

	@Override
	public String getEi1Id() {
		return this.getEi1Zutat().getId();
	}

	@Override
	public String getEi2Id() {
		return this.getEi2Zutat().getId();
	}

	@Override
	public String getChargeId() {
		return this.chargeID+"";
	}

	@Override
	public String getBaeckerId() {
		return this.baeckerID;
	}

	@Override
	public String getLogistikMitarbeiterId() {
		return logistikID==null?"-":logistikID;
	}

	@Override
	public String getQualitaetMitarbeiterId() {
		return kontrolleurID==null?"-":kontrolleurID;
	}
	
	public void setLogistik(String id){
		this.logistikID = id;
	}
	
	public void setKontrolleurID(String id){
		this.kontrolleurID = id;
	}
	
	public void setVerpackungsID(Long id){
		this.verpackungsID=id;
	}

	@Override
	public String getVerpackungId() {
		return verpackungsID==null ? "-" : this.verpackungsID+"";
	}

	@Override
	public String getSchokoId() {
		return schoko==null ? "-" : schoko.getId();
	}

	@Override
	public String getNussId() {
		return nuss == null ? "-" : nuss.getId();
	}
	
	public void setSchoko(ZutatXVSMImpl schoko){
		this.schoko = schoko;
	}
	
	public void setNuss(ZutatXVSMImpl nuss){
		this.nuss = nuss;
	}
	
	public void setSorte(LebkuchenSorte sorte){
		this.sorte =sorte;
	}
	
	public void setAuftragsId(Long auftragsId){
		this.auftragsID = auftragsId;
	}

	@Override
	public String getAuftragsId() {	
		return auftragsID==null?"-":auftragsID.toString();
	}

}
