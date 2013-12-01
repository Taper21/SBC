package alternativ.domain;

import java.rmi.server.UID;



public class Lebkuchen extends Resource{
	
	private Status status;
	private UID chargeUid;
	private UID honigUid;
	private UID mehlUid;
	private UID ei1Uid;
	private UID ei2Uid;

	public Lebkuchen(Status status, UID chargeUid, Resource honig, Resource mehl,
			Resource ei1, Resource ei2) {
		super();
		this.status = status;
		this.chargeUid = chargeUid;
		this.honigUid = honig.uid;
		this.mehlUid = mehl.uid;
		this.ei1Uid = ei1.uid;
		this.ei2Uid = ei2.uid;
	}

	public enum Status{
		GEFERTIGT,IN_OFEN,GEBACKEN,KONTROLLIERT;
	}
}
