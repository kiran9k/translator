package google_translate_selenium;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class config_reader {

	public config_reader() {
		// TODO Auto-generated constructor stub
	}
	 public static ArrayList<String> get_prop()
	    {
	    	Properties prop = new Properties();
	    	ArrayList<String> s= new ArrayList<String>();
	    	try {
	               //load a properties file
	    		prop.load(new FileInputStream("config"));
	    			
	               //get the property value and print it out
	               s.add(prop.getProperty("ip_folder_location"));
	               s.add(prop.getProperty("op_folder_location"));
	               s.add(prop.getProperty("ip_file_type"));
	               s.add(prop.getProperty("op_file_type"));
	               s.add(prop.getProperty("lang"));
	               s.add(prop.getProperty("source"));
	               s.add(prop.getProperty("log_location"));
	               s.add(prop.getProperty("processed_folder"));
	               s.add(prop.getProperty("unprocessed_folder"));
	             
	              
	    	} catch (IOException ex) {
	    		System.out.println("config file not found");
	    		ex.printStackTrace();
	        }
			return s;
	    }
}
