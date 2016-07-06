import java.util.ArrayList;

import exceptions.MethodNotFoundException;
import exceptions.PropertyNotFoundException;

public class Block extends Step {
	private ArrayList<Command> commands;
	
	public void addCommand(String command) throws MethodNotFoundException, PropertyNotFoundException
	{
		this.commands.add(new Command(command));
	}
	
	public String toString()
	{
		String returnText = "";
		
		for(Command command : this.commands)
		{
			returnText += command.toString() + "\n";
		}
		
		return returnText;
	}
}
