package launch;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.Manager;
import org.apache.catalina.startup.Tomcat;

import com.dawsonsystems.session.MongoManager;
import com.dawsonsystems.session.MongoSessionTrackerValve;
import com.mongodb.ServerAddress;

public class Main {

    public static void main(String[] args) throws Exception {

        Tomcat tomcat = new Tomcat();

        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        tomcat.setPort(Integer.valueOf(webPort));
        
        // TODO make this project specific
        File webappFile = new File("web/target/tomcat-web");
        if( !webappFile.exists() ){
        	System.out.println(" Running locally... " );
        	webappFile = new File("../web/target/tomcat-web");
        }
        String message = String.format( "Deploying on port %s the following directory: %s", webPort, webappFile.getAbsolutePath() );
        System.out.println( message );
        
        // TODO wire up a Mongo session?
        Context context = tomcat.addWebapp("/", webappFile.getAbsolutePath());
        Manager manager = getManager();
        if(manager != null){
        	context.getPipeline().addValve( new MongoSessionTrackerValve() );
        	context.setManager(manager);
        	System.out.println( "Designated Tomcat Manager set");
        }
        
        
        
        tomcat.start();
        tomcat.getServer().await();  
    }
    
    private static Manager getManager(){
    	try{
	    	// String url = "mongodb://test:password@ds033267.mongolab.com:33267/session-test";
	    	ServerAddress address = new ServerAddress("ds033267.mongolab.com", 33267);
	    	MongoManager manager = new MongoManager(address, "session-test", "test", "password");
	    	manager.setMaxInactiveInterval(10000);
	    	System.out.println( "Established MongoManager to " + address);
	    	return manager;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
}