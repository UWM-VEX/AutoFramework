import java.util.ArrayList;

import exceptions.MethodNotFoundException;
import exceptions.PropertyNotFoundException;

public class Block extends Step {
	private ArrayList<Command> commands;
	
	public Block()
	{
		this.commands = new ArrayList<Command>();
	}
	
	public void addCommand(String command, int id) throws MethodNotFoundException, PropertyNotFoundException
	{
		this.commands.add(new Command(command, id));
	}
	
	public String toString()
	{
		String returnText = "";
		
		for(Command command : this.commands)
		{
			returnText += command.toString() + "\n";
		}
		
		return "Block\n" + returnText + "End Block\n";
	}
	
	public String getExecution(int step)
	{
		String execution = "\t\tcase("+step+"):\n";
		
		for(Command command : this.commands)
		{
			execution += "\t\t\t" + command.getRunReference() + "\n";
		}
		
		execution += "\t\t\tautonomousInfo.isFinished = ";
		
		boolean isFirst = true;
		
		for(Command command : this.commands)
		{
			if( ! isFirst)
			{
				execution += " && " + command.getDoneReference();
			}
			else
			{
				execution += command.getDoneReference();
				isFirst = false;
			}
		}
		
		execution += ";\n\t\t\tbreak;";
		
		return execution;
	}
}
