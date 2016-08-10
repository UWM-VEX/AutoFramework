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
		this.entries = new ArrayList<String>(Arrays.asList(text.split(" ")));
		this.name = "command" + id;
		
		if(this.entries.size() > 1)
		{
			if(this.entries.get(1).equals("="))
			{
				this.method = Main.identifyMethod(entries.get(2));
				entries.remove(0);
				entries.remove(1);
				entries.remove(2);
			}
			else
			{
				this.method = Main.identifyMethod(entries.get(0));
				this.entries.remove(0);
			}
		}
		else
		{
			this.method = Main.identifyMethod(entries.get(0));
			this.entries.remove(0);
		}
		
		super.doneCriteria = new CommandDoneCriteria();
		this.constructor = method.buildConstructor(entries);
		this.additionalProperties = method.buildAdditionalProperties(entries);
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
		return this.method.getDeclaration();
	}
	
	public String getInstantiation()
	{
		return this.constructor;
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
		return "\t\t\tcase(" + step + "):\n"
			 + "\t\t\t\t" + getRunReference() + "\n\n"
		   	 + "autonomousInfo.isFinished = " + getDoneReference() + "\n"
			 + "\t\t\t\tbreak;";
	}
}
