import java.util.ArrayList;
import java.util.Arrays;

import exceptions.InvalidTypeException;

public class Property {
	private ArrayList<String> aliases;
	private String type;
	private String cName;
	
	public Property(String type, String cName, String... aliases) throws InvalidTypeException
	{
		if( ! validateType(type))
			throw new InvalidTypeException();
		
		this.setcName(cName);
		this.type = type;
		this.aliases = new ArrayList<String>(Arrays.asList(aliases));
	}
		
	private boolean validateType(String type)
	{
		return type.equals("char") || type.equals("unsigned char") || type.equals("signed char")
				|| type.equals("short") || type.equals("short int") || type.equals("signed short") 
				|| type.equals("signed short int") || type.equals("unsigned short") 
				|| type.equals("unsigned short int") || type.equals("int") || type.equals("signed") 
				|| type.equals("signed int") || type.equals("unsigned") || type.equals("unsigned int") 
				|| type.equals("long") || type.equals("long int") || type.equals("signed long") 
				|| type.equals("signed long int") || type.equals("unsigned long") 
				|| type.equals("unsigned long int") || type.equals("long long") || type.equals("long long int")
				|| type.equals("signed long long") || type.equals("signed long long int") 
				|| type.equals("unsigned long long") || type.equals("unsigned long long int") 
				|| type.equals("float") || type.equals("double") || type.equals("long double");
	}
	
	public boolean isReference(String entry)
	{
		for(String alias : this.aliases)
		{
			if(alias.equals(entry))
				return true;
		}
		
		return false;
	}

	public String getcName()
	{
		return cName;
	}

	public void setcName(String cName)
	{
		this.cName = cName;
	}
}
