package google_translate_selenium;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class main_entry_translate {
	private static Logger L;
	private static Logger L1;
		public static class Global
		{
			public static ArrayList<String> prop=config_reader.get_prop();
		}
	
		
		public static ArrayList<String> file_lister(Logger L,Logger L1)
		{
			//lists all matching  file in input directory 
			File Source=new File(Global.prop.get(0).toString());
			ArrayList<String> files=new ArrayList<String>();
			if(Source.isDirectory())
			{
				for (File f:Source.listFiles())
				{
					if(f.getName().endsWith("."+Global.prop.get(2)))
						files.add(f.getAbsolutePath());
				}
			}
			else
			{
				L.info("Error : not a vailed input directory");
				L1.info("Error : not a vailed input directory");
				System .out.println("not a valid Directory");
			}
			return files;
		}
		
		public static void file_mover(String filename,boolean flag)
		{	
			/* IF FLAG =TRUE => FILE MOVED TO PORCESSED
			 * FLAG = FLASE => FILE MOVED TO NOT_PROCESSED*/
			ArrayList<String> prop=Global.prop;
			String out_file="";
			if(flag)
				out_file=prop.get(7);
			else
				out_file=prop.get(8);
			new File(out_file).mkdirs();
			File afile=new File(filename);
			out_file+="/"+afile.getName();
			File bfile=new File(out_file);	
			System.out.println(afile.renameTo(bfile));//TRUE if SUCCESSFULL
					
		}
		
		public static void main(String[] args) throws MalformedURLException {
			//Main Log file creation
			L=Logger.getLogger("log_test");		
			FileHandler fh;
			try {
				fh = new FileHandler(Global.prop.get(6)+"/MainLogFile.log",true);
				L.addHandler(fh);
				SimpleFormatter formatter = new SimpleFormatter();
				fh.setFormatter(formatter);			
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IOException e) {			
					e.printStackTrace();
			}
			L.info("Main program started");	
			
			//error log file creation
			L1=Logger.getLogger("log_test1");		
			FileHandler fh1;
			try {
				fh1 = new FileHandler(Global.prop.get(6)+"/ErrorLogFile.log",true);
				L1.addHandler(fh1);
				SimpleFormatter formatter = new SimpleFormatter();
				fh1.setFormatter(formatter);			
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IOException e) {			
					e.printStackTrace();
			}
		
			
			//lists all file in the given directory 
			ArrayList<String> files=file_lister(L,L1);	
			L.info("no of files present in input  directory :"+files.size());
			//output languages to be converted 
	    	String[] lang_types=Global.prop.get(4).split("\\+");
	    	
	    	if(files.size()>0)
	    	{	    	
	    		WebDriver driver = new FirefoxDriver();    		   
	    		for(int i=0;i<files.size();i++)//loop for no of files 
	    		{	System.out.println(files.get(i));
	    			if(files.get(i).endsWith(Global.prop.get(2)))
	    			{
	    				//read contents from file
	    				 String text="";
	    				 if(Global.prop.get(2).matches("txt"))
	    					 text=read_contents.read_from_txt(files.get(i),L,L1);
	    				 else if (Global.prop.get(2).matches("html"))
	    					 text=read_contents.read_from_html(files.get(i),L,L1);
	    				 else if (Global.prop.get(2).matches("xml"))
	    					 text=read_contents.read_from_xml(files.get(i),L,L1);
	    				 else
	    				 {
	    					 L.info("Error : incorrect input file type in config");
	    					 L1.info("Error : incorrect input file type in config");
	    					 System.out.println("incorrect input file type in config");
	    					 L.info("Error : file moved to not-processed folder :"+files.get(i));
	    					 L1.info("Error : file moved to not-processed folder :"+files.get(i));
	    					
	    					 file_mover(files.get(i),false);
	    					 continue;
	    				}
	    				if(text.length()<1)
	    				{
	    					L.info("Error : no contents present in file");
	    					 L1.info("Error :no contents present in file");
	    					 file_mover(files.get(i),false);
	    					 L.info("Error : file moved to not-processed folder :"+files.get(i));
	    					 L1.info("Error : file moved to not-processed folder :"+files.get(i));
	    					
	    				}
	    				for(int j=0;j<lang_types.length;j++)//loop for no of lang
	    				{
	    					System.out.println(lang_types[j]);
	    					driver=google_translate.google_translate(driver,files.get(i), text,lang_types[j],L,L1);					
	    					driver.manage().deleteAllCookies();
	    				}
	    				file_mover(files.get(i),true);
	    				L.info("File moved to processed folder: "+files.get(i));
	    			}	    			
	    		}
	    		L.info("translation completed");
	    		driver.quit();
	    	}	
	    	
	    }
	}

