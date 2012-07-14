package launch;

import java.io.File;

import org.apache.catalina.startup.Tomcat;

public class Main {

    public static void main(String[] args) throws Exception {

        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        //tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        File file = new File("currentDirectory ");
        System.out.println("Current directory file: " + file.getAbsolutePath());
        
        File webappFile = new File("web/target/tomcat-web");
        System.out.println("WebApp file = " + webappFile.getAbsolutePath());
        tomcat.addWebapp("/", webappFile.getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();  
    }
}