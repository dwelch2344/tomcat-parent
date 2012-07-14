package launch;

import java.io.File;

import org.apache.catalina.startup.Tomcat;

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
        String message = String.format( "Deploying on port %s the following directory: %s", webPort, webappFile.getAbsolutePath() );
        System.out.println( message);
        
        tomcat.addWebapp("/", webappFile.getAbsolutePath());
        
        
        // TODO wire up a Mongo session?
        
        
        tomcat.start();
        tomcat.getServer().await();  
    }
}