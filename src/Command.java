import java.util.ArrayList;
import java.util.Arrays;

import exceptions.MethodNotFoundException;
import exceptions.PropertyNotFoundException;

public class Command extends Step {
	private Method method;
	private ArrayList<String> entries;
	private String constructor;
	private ArrayList<String> additionalProperties;
	
	public Command(String text) throws MethodNotFoundException, PropertyNotFoundException
	{
		this.entries = new ArrayList<String>(Arrays.asList(text.split(" ")));
		
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
}
