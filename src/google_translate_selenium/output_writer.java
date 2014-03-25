package google_translate_selenium;

import google_translate_selenium.main_entry_translate.Global;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class output_writer {

	public output_writer() {
		// TODO Auto-generated constructor stub
	}
	public static void txt_writer(String content,String filename,Logger L,Logger L1)
	{
		
		try
			{				
				
				String f=filename;				
				File file = new File(f);				
				// if file doesnt exists, then create it				
				if (!file.exists()) 
				{
					
					file.createNewFile();
					
				} 
				FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
				BufferedWriter bw = new BufferedWriter(fw);				
				bw.write(content);
				bw.close(); 	
				L.info("write to file successful "+filename);
			} 
		catch (IOException e)
			{
			L.info("Error : Writing to file unsuccesful "+filename);	
			L.info("error : "+e.getMessage());
			L1.info("Error : Writing to file unsuccesful "+filename);	
			L1.info("error : "+e.getMessage());
			e.printStackTrace();
				
			}
		
		}

	public static void xml_writer(String content,String filename,Logger L,Logger L1)
	{	File file = new File(filename+".xml");
		// if file doesnt exists, then create it		
		if (!file.exists()) 
		{
			try { 
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();		 
				Element rootElement = doc.createElement("body");
				doc.appendChild(rootElement);
				rootElement.appendChild(doc.createTextNode(content));
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(OutputKeys.METHOD,"xml");	
				StreamResult result = new StreamResult(file);
				transformer.transform(source, result);     
				System.out.println("File saved!");
				L.info("writitng to xml succesful :"+filename);
				
			} 
			catch (ParserConfigurationException pce) {
				L.info("Error : Writing to file unsuccesful "+filename);	
				L.info("error : "+pce.getMessage());
				L1.info("Error : Writing to file unsuccesful "+filename);	
				L1.info("error : "+pce.getMessage());
				pce.printStackTrace();
				} 
			catch (TransformerException tfe) {
				L.info("Error : Writing to file unsuccesful "+filename);	
				L.info("error : "+tfe.getMessage());
				L1.info("Error : Writing to file unsuccesful "+filename);	
				L1.info("error : "+tfe.getMessage());
				tfe.printStackTrace();
			}
		}
		
		else
		{	try
			{
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	        Document document = documentBuilder.parse(filename+".xml");
	        Element root = document.getDocumentElement();
	        // Root Element	  ;
	        root.appendChild(document.createTextNode(content));
	        
	        
	        DOMSource source = new DOMSource(document);
	        
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        StreamResult result = new StreamResult(file);
	        transformer.transform(source, result);
	        L.info("writitng to xml succesful :"+filename);
			}
		catch (ParserConfigurationException pce) {
			L.info("Error : Writing to file unsuccesful "+filename);	
			L.info("error : "+pce.getMessage());
			L1.info("Error : Writing to file unsuccesful "+filename);	
			L1.info("error : "+pce.getMessage());
			pce.printStackTrace();
			} 
		catch (TransformerException tfe) {
			L.info("Error : Writing to file unsuccesful "+filename);	
			L.info("error : "+tfe.getMessage());
			L1.info("Error : Writing to file unsuccesful "+filename);	
			L1.info("error : "+tfe.getMessage());
			tfe.printStackTrace();
		} catch (SAXException e) {
			L.info("Error : Writing to file unsuccesful "+filename);	
			L.info("error : "+e.getMessage());
			L1.info("Error : Writing to file unsuccesful "+filename);	
			L1.info("error : "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			L.info("Error : Writing to file unsuccesful "+filename);	
			L.info("error : "+e.getMessage());
			L1.info("Error : Writing to file unsuccesful "+filename);	
			L1.info("error : "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
	
}
