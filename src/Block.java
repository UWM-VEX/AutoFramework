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
		String execution = "\t\t\tcase("+step+"):" + System.getProperty("line.separator");
		
		for(Command command : this.commands)
		{
			execution += "\t\t\t\t" + command.getRunReference() + System.getProperty("line.separator");
		}
		
		execution += "\t\t\t\tautonomousInfo.isFinished = ";
		
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
		
		execution += ";" + System.getProperty("line.separator") + "\t\t\t\tbreak;";
		
		return execution;
	}
}
