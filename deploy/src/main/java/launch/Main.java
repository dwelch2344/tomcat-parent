package launch;

import java.io.File;
import java.util.logging.Logger;

import org.apache.catalina.Context;
import org.apache.catalina.Manager;
import org.apache.catalina.startup.Tomcat;

import co.ntier.mongo.tomcat.MongoSessionManager;
import co.ntier.mongo.tomcat.MongoSessionTrackerValve;

import com.mongodb.ServerAddress;

public class Main {

	private static final Logger logger = Logger.getLogger( Main.class.getName() );
	
	// see https://github.com/dawsonsystems/Mongo-Tomcat-Sessions
	
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
        	logger.info(" Running locally... " );
        	webappFile = new File("../web/target/tomcat-web");
        }
        String message = String.format( "Deploying on port %s the following directory: %s", webPort, webappFile.getAbsolutePath() );
        logger.info( message );
        
        Context context = tomcat.addWebapp("/", webappFile.getAbsolutePath());
        
     // Wire up the custom session manager if MONGO_SESSION_URL is available
        Manager manager = getManager();
        if(manager != null){
        	context.getPipeline().addValve( new MongoSessionTrackerValve() );
        	context.setManager(manager);
        	logger.info( "Designated Tomcat Manager set");
        }
        
        tomcat.start();
        tomcat.getServer().await();  
    }
    
    private static Manager getManager(){
    	
    	try{
    		MongoConnectionDetails conn = parseConnection();
    		if( conn != null ){
	    		// just an FYI: this throws a MongoException w/ the message "unauthorized" OR "can't find a master" if you have the wrong details here
		    	ServerAddress address = new ServerAddress(conn.host, conn.port);
		    	MongoSessionManager manager = new MongoSessionManager(address, conn.database, conn.user, conn.password);
		    	logger.info( "Established MongoManager to " + address);
		    	return manager;
    		}
    	}catch(Exception e){
    		logger.warning("Failed establishing Mongo connection");
    		throw new RuntimeException("Failed loading Mongo connection", e);
    	}
    	return null;
    }
    
    private static MongoConnectionDetails parseConnection(){
    	
    	String url = System.getenv("MONGO_SESSION_URL");
    	if( url != null){
	    	MongoConnectionDetails details = new MongoConnectionDetails();
			String[] parts = url.split(":");
			details.host = parts[0];
			details.port = parts.length > 1 ? Integer.valueOf(parts[1]) : 27017;
			details.database = System.getenv("MONGO_SESSION_DB");
			details.user = System.getenv("MONGO_SESSION_USER");
			details.password = System.getenv("MONGO_SESSION_PASS");
			
	    	return details;
    	}
    	
    	return null;
    }
    
    private static class MongoConnectionDetails{
    	public String user, password, host, database; 
    	public int port;
    }
}