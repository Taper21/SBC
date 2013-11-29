package xvsm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mozartspaces.capi3.Coordinator;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.core.AsyncCapi;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.CapiUtil;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.DefaultMzsCoreFactory;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsCore;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.config.Configuration;

import domain.Zutat;

import gui.MainFrame;

public class Space {
	
	 private static MzsCore core = null;
	 private static Capi capi = null;
	
	 public static ContainerReference lookUpOrCreateContainer(String name) throws MzsCoreException{
		 return CapiUtil.lookupOrCreateContainer(name, null,null , null, getCapi());
	 }
	 
	 private static MzsCore createSpace(){
		 MzsCore core;
		 try{
			 core = DefaultMzsCore.newInstance();
			 return core;
		 }catch(Exception e){
			 return null;
		 }
	 }
	 
	 private static MzsCore connectSpace(){
		 MzsCore core;
		 try{
			 core = DefaultMzsCore.newInstance(0);
			 return core;
		 }catch(Exception e){
			 return null;
		 }
	 }
	 
	 public static MzsCore connectOrCreateSpace(){
		 MzsCore c;
		 if(core == null){
			 c = createSpace();
			 if(c == null){
				 return connectSpace();
			 }else{
				return c;
			 }
		 }
		 return core;
	 }
	 
	 public static Capi getCapi(){
		 if(capi == null){
			 capi = new Capi(connectOrCreateSpace());
		 }
		 return capi;
	 }
	 
	 private static ContainerReference createContainer(String name){
		 URI uri;
		try {
			uri = new URI("xvsm://localhost:9876");
			return getCapi().createContainer(name, uri, MzsConstants.Container.UNBOUNDED,Arrays.asList(new FifoCoordinator()),null,null);
		} catch (Exception e) {
			return null;
		} 
	 }
	 
	 private static ContainerReference lookupContainer(String name){
		 URI uri;
		try {
			uri = new URI("xvsm://localhost:9876");
			return getCapi().lookupContainer(name, uri, 5000,null);
		} catch (Exception e) {
			return null;
		} 
	 }
	 
	 public static ContainerReference createOrLookUpContainer(Standort standort){
		 ContainerReference cref =  lookupContainer(standort.getName());
		 if(cref!=null){
			 return cref;
		 }else{
			 return createContainer(standort.getName());
		 }
	 }
	 
	 public static void shutdownSpace() throws MzsCoreException{
		 capi.shutdown(null);
		 core.shutdown(true);
	 }
	/**
	 * @param args
	 * @throws MzsCoreException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws MzsCoreException, IOException {
		 

		 //		 capi.write(container, new Entry("Hello, space!")); 
		 //		 System.out.println("Entry written"); 
		 //		 // read an entry from the container 
		
		boolean beenden = false;
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		while(!beenden){
			String command = console.readLine();
		if(command.equals("q")){
			beenden=true;
			break;
		}
		if(command.equals("s")){
		 ArrayList<Zutat> resultEntries = Space.getCapi().read(Space.createOrLookUpContainer(Standort.HONIGLAGER ),FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL),500,null); 
		 for(Zutat z : resultEntries){
		 System.out.println("Entry read: " + z.getZutatTypEnum() + "#" + z.getId()); 
		 
		 }
		}
		 
		}
		 // destroy the container 
		 capi.destroyContainer(Space.createOrLookUpContainer(Standort.HONIGLAGER), null); 
		 
		 // shutdown the core 
		 core.shutdown(true); 

	}

}
