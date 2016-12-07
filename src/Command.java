import java.util.ArrayList;
import java.util.Arrays;

import exceptions.MethodNotFoundException;
import exceptions.PropertyNotFoundException;

public class Command extends Step {
	private Method method;
	private ArrayList<String> entries;
	private String constructor;
	private ArrayList<String> additionalProperties;
	private String name;
	
	public Command(String text, int id) throws MethodNotFoundException, PropertyNotFoundException
	{
		this.entries = new ArrayList<String>(Arrays.asList(text.split("\\("))); // Regex for (
		this.name = "command" + id;
		
		if(this.entries.size() > 1)
		{
			this.method = Main.identifyMethod(entries.get(0).trim());
			this.entries.remove(0);
		}
		else
		{
			System.out.println("Exception at command: " + entries.get(0));
			System.out.println("Commands must have parenthesis.");
			throw new MethodNotFoundException();
		}
		
		super.doneCriteria = new CommandDoneCriteria();
		this.constructor = method.buildInstantiation(entries);
		//this.additionalProperties = method.buildAdditionalProperties(entries);
	}
	
	public String toString()
	{
		String returnText = this.constructor;
		
		for(String property : this.additionalProperties)
		{
			returnText += "\n" + property;
		}
		
		return returnText;
	}
	
	public Method getMethod()
	{
		return this.method;
	}
	
	public String getDeclaration()
	{
		if(this.method.isPointer())
		{
			return this.method.getDeclaration() + " * " + this.name + ";";
		}
		else
		{
			return this.method.getDeclaration() + " " + this.name + ";";
		}		
	}
	
	public String getInstantiation()
	{
		return this.name + " = " + this.constructor + ";";
	}
	
	public String getRunReference()
	{
		return this.method.getRunReference(this.name);
	}
	
	public String getDoneReference()
	{
		return this.method.getDoneReference(this.name);
	}
	
	public String getExecution(int step)
	{
		return "\t\t\tcase(" + step + "):" + System.getProperty("line.separator")
			 + "\t\t\t\t" + getRunReference()  + System.getProperty("line.separator") + System.getProperty("line.separator")
		   	 + "\t\t\t\tautonomousInfo.isFinished = " + getDoneReference() + ";" + System.getProperty("line.separator")
			 + "\t\t\t\tbreak;";
	}
}
