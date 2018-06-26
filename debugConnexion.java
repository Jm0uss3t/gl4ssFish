import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


public class debugConnexion {

    static String log_file ="/tmp/mylog.log";
    
    public static void getPort(){
        String message = null;
        try {
            Scanner scanner = new Scanner(new FileReader(log_file));
             
             while (scanner.hasNextLine()) {
                 message = scanner.nextLine();
                 
             }
        } catch (FileNotFoundException e) {
            System.out.println("\nERROR " +e.getMessage());
            
        }
            String[] splitString = (message.split(" "));
            String ip = splitString[3].replaceAll(",", "");
            String port =splitString[5];
            System.out.println("The JMX client try to connect to ip :" +ip +" and port "+port);
            System.out.println("Please use :\tsocat TCP4-LISTEN:"+port+",bind="+ip+",su=nobody,fork TCP4:SERVER:"+port+"\nPlease ensure that it is not necessary to seting up a new interface");
    }
    
    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {
        
        Logger logger = Logger.getLogger("sun.rmi");
        logger.setLevel(Level.FINE);
        Handler fh = null;
        try {
            fh= new FileHandler(log_file);
            fh.setLevel(Level.ALL);
            SimpleFormatter t = new SimpleFormatter();
            fh.setFormatter(t);
        } catch (SecurityException | IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        logger.addHandler(fh);
         Map env = new HashMap();
        String[] creds = {"admin","admin"};
        env.put(JMXConnector.CREDENTIALS, creds);
        
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://myhost.net/jndi/rmi://myhost.net:8686/myhost.net/7676/jmxrmi");
         JMXConnector jmxc = null ;
        try
        {
             jmxc = JMXConnectorFactory.connect(url, env);
             System.out.println("Connexion OK");    
        }
        catch (ConnectException e) {
            
       //System.out.println(e.getMessage());
        getPort();  
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
}
