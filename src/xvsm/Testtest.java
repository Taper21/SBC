package xvsm;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsCore;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.NotificationManager;
import org.mozartspaces.notifications.Operation;

public class Testtest implements NotificationListener{

	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws MzsCoreException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws MzsCoreException, URISyntaxException, InterruptedException {
//		MzsCore serverCore = DefaultMzsCore.newInstance();
//		MzsCore core = DefaultMzsCore.newInstance(0);
//		Capi capi = new Capi(core);
//		URI uri = new URI("xvsm://localhost:9876");
//		ContainerReference container = capi.createContainer("Test", uri, MzsConstants.Container.UNBOUNDED, null, null);
//		NotificationManager nm = new NotificationManager(core);
//		nm.createNotification(container, new Testtest(), Operation.WRITE);
//		capi.write(new Entry("Test"), container);
//		capi = new Capi(serverCore);
//		
//		container = capi.lookupContainer("Test",uri,5000,null);
//		System.out.println("UND GEHT: " + capi.read(container).get(0));
		
//		while(true){
//			List<Lebkuchen> ofen = Space.getCapi().read(Space.createOrLookUpContainer(Standort.OFEN),FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL),MzsConstants.RequestTimeout.INFINITE,null);
//			List<Lebkuchen> gefertigt = Space.getCapi().read(Space.createOrLookUpContainer(Standort.LEBKUCHEN_GEFERTIGT),FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL),MzsConstants.RequestTimeout.INFINITE,null);
//			List<Lebkuchen> gebacken = Space.getCapi().read(Space.createOrLookUpContainer(Standort.GEBACKEN),FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL),MzsConstants.RequestTimeout.INFINITE,null);
//			System.out.println("Ofen:");
//			for(Lebkuchen l: ofen){
//				System.out.println(l);
//			}
//			System.out.println("Gefertigt:");
//			for(Lebkuchen l: gefertigt){
//				System.out.println(l);
//			}
//			System.out.println("Gebacken:");
//			for(Lebkuchen l: gebacken){
//				System.out.println(l);
//			}
//			Thread.sleep(2500);
//		}
		
		Space.setPort(Integer.parseInt(args[0]));
		Space.connectOrCreateSpace();
		Space.shutdownSpace();
	}

	@Override
	public void entryOperationFinished(Notification source,
			Operation operation, List<? extends Serializable> entries) {
		System.out.println("Zutat geschrieben in Space !!!!");
		
	}

}
