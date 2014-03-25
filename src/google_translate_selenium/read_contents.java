package google_translate_selenium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class read_contents {
	
	public static String read_from_html(String filename,Logger L,Logger L1) 
	{
		
		File input = new File(filename);
		org.jsoup.nodes.Document doc;
		String output ="";
		try {
			
			doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			Elements links = doc.getElementsByTag("body");
			output = links.text();
			L.info("read from html successfull :"+filename);
		
		} catch (IOException e) {
			L.info("Error : reading from html file :"+filename);
			L.info("Error :"+e.getMessage());
			L1.info("Error : reading from html file :"+filename);
			L1.info("Error :"+e.getMessage());
			e.printStackTrace();
		}//temporary url 
		
		return output;
		
	}
	
	public static String read_from_xml(String filename,Logger L,Logger L1)
	{
		String content="";
		 try {

			 FileInputStream file = new FileInputStream(new File(filename));
             
	            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	             
	            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
	             
	            Document xmlDocument = builder.parse(file);
	 
	            XPath xPath =  XPathFactory.newInstance().newXPath();
	 
	            //System.out.println("*************************");
	            String expression = "//body";
	            //System.out.println(expression);
	            content = xPath.compile(expression).evaluate(xmlDocument);
	            //System.out.println(content);
	            file.close();
	            L.info("read from xml successfull :"+filename);
	          
		 }
		 catch (SAXParseException err) {
			 L.info("Error : reading from xml file :"+filename);
				L.info("Error :"+err.getMessage());
				L1.info("Error : reading from xml file :"+filename);
				L1.info("Error :"+err.getMessage());
			 err.printStackTrace();
			   }catch (SAXException e) {
				   L.info("Error : reading from xml file :"+filename);
				   L.info("Error :"+e.getMessage());
					L1.info("Error : reading from xml file :"+filename);
					L1.info("Error :"+e.getMessage());
		        Exception x = e.getException ();
		        ((x == null) ? e : x).printStackTrace ();

		        }catch (Throwable t) {
		        	L.info("Error : reading from xml file :"+filename);
					L.info("Error :"+t.getMessage());
					L1.info("Error : reading from xml file :"+filename);
					L1.info("Error :"+t.getMessage());
		        	t.printStackTrace ();}
		return content;
	}
	
	
	public static String read_from_txt(String fileName,Logger L,Logger L1)
	{
		String line = "";
		String output="";	
		try
		{			
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);        
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader =new BufferedReader(fileReader);								
			while((line = bufferedReader.readLine()) != null) 
			{	
				output+=line+"\n";
			}			
			bufferedReader.close();
			L.info("read from txt successfull :"+fileName);
		}
		
        catch(FileNotFoundException ex) 
        {
        	L.info("Error : reading from txt file :"+fileName);
			L.info("Error :"+ex.getMessage());
			L1.info("Error : reading from txt file :"+fileName);
			L1.info("Error :"+ex.getMessage());
        	ex.printStackTrace();			
        }
        catch(IOException ex)
        {
        	L.info("Error : reading from txt file :"+fileName);
			L.info("Error :"+ex.getMessage());
			L1.info("Error : reading from txt file :"+fileName);
			L1.info("Error :"+ex.getMessage());
        	 ex.printStackTrace();
        }
		//System.out.println(output);
        return output;
	}
	
}
