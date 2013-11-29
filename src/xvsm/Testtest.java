package xvsm;

import java.net.URI;
import java.net.URISyntaxException;

import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsCore;
import org.mozartspaces.core.MzsCoreException;

public class Testtest {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws MzsCoreException 
	 */
	public static void main(String[] args) throws MzsCoreException, URISyntaxException {
		MzsCore serverCore = DefaultMzsCore.newInstance();
		MzsCore core = DefaultMzsCore.newInstance(0);
		Capi capi = new Capi(core);
		URI uri = new URI("xvsm://localhost:9876");
		ContainerReference container = capi.createContainer("Test", uri, MzsConstants.Container.UNBOUNDED, null, null);
		capi.write(new Entry("Test"), container);
		capi = new Capi(serverCore);
		
		container = capi.lookupContainer("Test",uri,5000,null);
		System.out.println("UND GEHT: " + capi.read(container).get(0));
	}

}
