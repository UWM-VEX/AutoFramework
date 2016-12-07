import java.util.ArrayList;
import java.util.Arrays;

import exceptions.PropertyNotFoundException;

public class Method {
	private ArrayList<Property> properties;
	private String javaCommand;
	private String declaration;
	private String instantiation;
	private String reference;
	private String doneReference;
	private boolean pointer;
	
	public Method(String javaCommand, String declaration, String instantiation, String doneReference, boolean pointer, Property... properties)
	{
		this.javaCommand = javaCommand;
		this.declaration = declaration;
		this.instantiation = instantiation;
		this.reference = "To Do";
		this.doneReference = doneReference;
		this.properties = new ArrayList<Property>(Arrays.asList(properties));
		this.pointer = pointer;
	}
	
	public String toString()
	{
		return "javaCommand: " + javaCommand + "\n"
				+ "declaration: " + declaration + "\n"
				+ "instantiation: " + instantiation + "\n"
				+ "reference: " + reference + "\n"
				+ "doneReference: " + doneReference + "\n"
				+ "pointer: " + pointer;
	}
	
	public String buildInstantiation(ArrayList<String> entries)
	{
		String contructor = this.instantiation;
		
		contructor += "(";
		
		for(String entry : entries)
		{
			contructor += entry;
			
			if(entry.endsWith(")"))
			{
				return contructor;
			}
		}
		
		return contructor + ")";
	}
	
	public ArrayList<String> buildAdditionalProperties(ArrayList<String> entries) throws PropertyNotFoundException
	{
		ArrayList<String> additionalProperties = new ArrayList<String>();
		
		//Remove mandatory arguments
		for(int i = 0; i < entries.size(); i++)
		{
			if(entries.get(0).endsWith(")"))
			{
				entries.remove(0);
				break;
			}
			else
			{
				entries.remove(0);
			}
		}
		
		boolean isProperty = true;
		Property property = null;
		
		for(String entry : entries)
		{
			if(isProperty)
			{
				property = this.findProperty(entry);
			}
			else
			{
				additionalProperties.add(property.getcName() + entry);
			}
		}
		
		return additionalProperties;
	}
	
	private Property findProperty(String entry) throws PropertyNotFoundException
	{
		for(Property property : this.properties)
		{
			if(property.isReference(entry))
				return property;
		}
		
		throw new PropertyNotFoundException();
	}
	
	public String getJavaCommand()
	{
		return this.javaCommand;
	}
	
	public String getDeclaration()
	{
		return this.declaration;
	}
	
	public String getRunReference(String instance)
	{
		return this.javaCommand + "(" + instance + ");";
	}
	
	public String getDoneReference(String instance)
	{
		if(this.pointer)
		{
			return "(*" + instance + ")" + this.doneReference;
		}
		else
		{
			return instance + this.doneReference + ";";
		}
	}
	
	public boolean isPointer()
	{
		return this.pointer;
	}
}
