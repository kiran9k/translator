package google_translate_selenium;

import google_translate_selenium.main_entry_translate.Global;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.remote.ErrorHandler.UnknownServerException;

public class google_translate {


	public static WebDriver google_translate(WebDriver driver,String filename,String text,String lang,Logger L,Logger L1)
	{
		//implicitly waits for 5 seconds
		driver.manage().timeouts().implicitlyWait(5
				, TimeUnit.SECONDS);
        try{
        	// Go to the Google Suggest home page
        	String source=Global.prop.get(5);
        	driver.get("http://translate.google.com/#"+source+"/"+lang);   
        	if(text.length()<1)
        		return driver ;
        	// Enter the query string "Cheese"
        	//System.out.println("file read");
        	WebElement query = driver.findElement(By.id("source"));
        	query.sendKeys(text);
        	try{
        		Alert alert = driver.switchTo().alert();
        		alert.accept();
        		}
        	catch(Exception e){
        		//System.out.println("no alert box present");
        		//e.printStackTrace()
        		}
        	WebElement query1 = driver.findElement(By.id("gt-submit"));
        	
        	//Submit button disabled 
        	//query1.click();

        	
        	//System.out.println("text entered");
        	Date d=new Date();
        	long intial=d.getTime();
        	Boolean flag=false;
        	WebElement result;
        	do{
        		result = driver.findElement(By.id("result_box"));
        		flag=true;
        		d=new Date();
        		}while(result.getText().length()<20 && (d.getTime()-intial<15000) );
        	//System.out.println("result fetched");
        	
        	
        	//Second trial for not-processed files
        	//TODO Ouptut filename : input +_lang
        	if((flag && (result.getText().length()<1) )|| ((result.getText().split("\n").length != text.split("\n").length)))
        	{
        		
        		d=new Date();
        		intial=d.getTime();
        		System.out.println("length not equal ");
        		query1 = driver.findElement(By.id("gt-submit"));         	
            	query1.click();
            	do{
            		result = driver.findElement(By.id("result_box"));
            		flag=true;
            		d=new Date();
            		}while(result.getText().length()<20 && (d.getTime()-intial<10000) );
            	
        	}
        	if((result.getText().length()<1 && text.length()>0) || (result.getText().split("\n").length<text.split("\n").length))
        	{
        		//third trial
        		System.out.println("third and final trial ");
        		driver.get("http://translate.google.com/#"+source+"/"+lang); 
        		query = driver.findElement(By.id("source"));
        		query.sendKeys(text);
        		try{
        			Alert alert = driver.switchTo().alert();
             		alert.accept();
        		}
             	catch(Exception e){
             		//System.out.println("no alert box present");
             		//e.printStackTrace()
             	}
        		query1 = driver.findElement(By.id("gt-submit"));
        		query1.click();
        		d=new Date();
        		intial=d.getTime();        		
        		query1 = driver.findElement(By.id("gt-submit"));         	
            	query1.click();
            	do{
            		result = driver.findElement(By.id("result_box"));
            		flag=true;
            		d=new Date();
            		}while(result.getText().length()<20 && (d.getTime()-intial<10000) );
            	System.out.println(result.getText().split("\n").length);
            	System.out.println(text.split("\n").length);
            	if(result.getText().split("\n").length < text.split("\n").length)
            	{
            		L.info("content translated  failed for "+lang+ " "+filename);
            		L1.info("content translated  failed for "+lang+" " +filename );
            		text="";
            		return driver;
            	}
        		
        	}
        	//text cleared 
        	text="";
        	String output=Global.prop.get(1).toString()+"/"+new File(filename).getName().replaceAll("."+Global.prop.get(2), "")+"_"+lang;
        	if(Global.prop.get(3).matches("txt"))
        		output_writer.txt_writer(result.getText(),output+".txt",L,L1);
        	else if(Global.prop.get(3).matches("xml"))
        		output_writer.xml_writer(result.getText(),output,L,L1);
        	else if(Global.prop.get(3).matches("all"))
        	{
        		try{
        			output_writer.xml_writer(result.getText(),output,L,L1);
        		}
        		catch(Exception e)
        		{
        			L.info("writing to xml failed"+filename);
        			L1.info("writing to xml failed"+filename);
        			L.info("Error :"+e.getMessage());
        			L1.info("Error :"+e.getMessage());
        			System.out.println("writng to xml filed");
        			e.printStackTrace();
        		}
        		try{
        			output_writer.txt_writer(result.getText(),output+".txt",L,L1);
        			}
        		catch(Exception e)
        		{
        			L.info("writing to txt failed"+filename);
        			L1.info("writing to txt failed"+filename);
        			L.info("Error :"+e.getMessage());
        			L1.info("Error :"+e.getMessage());
        			System.out.println("writng to txt filed");
        			e.printStackTrace();
        		}
      		}
        	else
        		System.out.println("Incorrect output file type in config ");
        	//result.clear();
        	driver.manage().deleteAllCookies();
        }
        catch(UnhandledAlertException e)
        {
        	L.info("Error :file not processed "+filename);
        	System.out.println("file not processed "+filename);
        	L.info("Error : "+e.getMessage());
        	L1.info("Error :file not processed "+filename);
        	L1.info("Error : "+e.getMessage());
        	e.printStackTrace();
        }
        catch(NoSuchElementException e)
        {
        	L.info("Error :file not processed "+filename);
        	System.out.println("file not processed "+filename);
        	L.info("Error : "+e.getMessage());
        	L1.info("Error :file not processed "+filename);
        	L1.info("Error : "+e.getMessage());
        	e.printStackTrace();
        }
        catch(UnknownServerException e)
        {
        	L.info("Error :file not processed "+filename);
        	L1.info("Error :file not processed "+filename);
        	System.out.println("file not processed "+filename);
        	L.info("Error : "+e.getMessage());
        	L1.info("Error : "+e.getMessage());
        	e.printStackTrace();
        }
        catch(UnreachableBrowserException e)
        {
        	L.info("Error :file not processed "+filename);
        	L1.info("Error :file not processed "+filename);
        	System.out.println("Browser closed :file not processed "+filename);
        	L.info("Error : "+e.getMessage());
        	L1.info("Error : "+e.getMessage());
        	e.printStackTrace();
        }
    	//System.out.println(result.getText());    
    	return driver ;
	}
}
