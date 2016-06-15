import java.util.ArrayList;
import java.util.Arrays;

import exceptions.PropertyNotFoundException;

public abstract class Method {
	private ArrayList<Property> properties;
	private String javaCommand;
	private String cVariable;
	private String declaration;
	private String instantiation;
	private String reference;
	private String doneReference;
	
	public Method(String javaCommand, String cVariable, String declaration, String instantiation, String reference, String doneReference, Property... properties)
	{
		this.javaCommand = javaCommand;
		this.cVariable = cVariable;
		this.declaration = declaration;
		this.instantiation = instantiation;
		this.reference = reference;
		this.doneReference = doneReference;
		this.properties = new ArrayList<Property>(Arrays.asList(properties));
	}
	
	public void build(String text) throws PropertyNotFoundException
	{
		String[] entries = text.split(" ");
		Property currentProperty = null;
		
		for(String entry : entries)
		{
			if(currentProperty == null)
			{
				currentProperty = findProperty(entry);
			}
			else
			{
				currentProperty.setValue(entry);
			}
		}
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
}
