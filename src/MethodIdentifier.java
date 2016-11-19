import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import exceptions.XMLException;

public class MethodIdentifier {
	
	Document doc = null;
	
	public MethodIdentifier(String xmlFile) throws XMLException
	{
		File inputFile = new File(xmlFile);
		DocumentBuilderFactory dbFactory 
        	= DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		
		try
		{
			dBuilder = dbFactory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			dBuilder = null;
			e.printStackTrace();
			throw new XMLException("Error parsing methods XML.");
		}
		
		try
		{
			this.doc = dBuilder.parse(inputFile);
			this.doc.getDocumentElement().normalize();
		}
		catch (SAXException | IOException e)
		{
			e.printStackTrace();
			throw new XMLException("Error parsing methods XML.");
		}
	}
	
	public ArrayList<Method> parseMethods() throws XMLException
	{
		NodeList nodes = this.doc.getElementsByTagName("method");
		ArrayList<Method> methods = new ArrayList<Method>();
		
		for(int i = 0; i < nodes.getLength(); i++)
		{
			Node method = nodes.item(i);
			if (method.getNodeType() == Node.ELEMENT_NODE)
			{
	               Element element = (Element) method;
	               try
	               {
		               String javaCommand = element.getElementsByTagName("javaCommand").
		            		   item(0).getTextContent();
		               String declaration = element.getElementsByTagName("declaration").
		            		   item(0).getTextContent();
		               String instantiation = element.getElementsByTagName("instantiation").
		            		   item(0).getTextContent();
		               String doneReference = element.getElementsByTagName("doneReference").
		            		   item(0).getTextContent();
		               String pointerStr = element.getElementsByTagName("pointer").
		            		   item(0).getTextContent();
		               
		               boolean pointer = true;
		               
		               if(pointerStr.toLowerCase().equals("false"))
		               {
		            	   pointer = false;
		               }
		               else if( ! pointerStr.toLowerCase().equals("true"))
		               {
		            	   pointer = true;
		            	   throw new XMLException("Error with method " + i
		            			   + ". Pointer must be either true or false.");
		               }
		               
		               methods.add(new Method(javaCommand, declaration,
		            		   instantiation, doneReference, pointer));
	               }
	               catch (NullPointerException e)
	               {
	            	   throw new XMLException("Error with method " + i
	            	   		+ "The following elements must be "
	            	   		+ "present for each method: javaCommand, declaration, "
	            	   		+ "instantiation, doneReference, pointer.");
	               }
			}
		}
		
		return methods;
	}
}
