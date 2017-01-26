import java.util.ArrayList;

import exceptions.InvalidCommandException;
import exceptions.MethodNotFoundException;
import exceptions.PropertyNotFoundException;

public class Block extends Step {
	private ArrayList<Command> commands;
	private BlockDoneCriteria doneCriteria = new BlockDoneCriteria();
	
	public Block()
	{
		this.commands = new ArrayList<Command>();
	}
	
	public void addCommand(String command, int id) throws MethodNotFoundException, PropertyNotFoundException, InvalidCommandException
	{
		this.commands.add(new Command(command, id));
		this.doneCriteria.addCommand(command);
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
		
		for(int i = 0; i < this.commands.size(); i++)
		{
			boolean isLast = i == (this.commands.size() - 1);
			
			if( ! isLast)
			{
				execution += this.commands.get(i).getDoneReference() +
						" " + this.doneCriteria.getNextCriteria() + " ";
			}
			else
			{
				execution += this.commands.get(i).getDoneReference();
			}
		}
		
		execution += ";" + System.getProperty("line.separator") + "\t\t\t\tbreak;";
		
		return execution;
	}
	
	public String getDeclaration()
	{
		String output = "";
		
		for(Command command : this.commands)
		{
			output += command.getDeclaration() + System.getProperty("line.separator");
		}
		
		return output;
	}
	
	public String getInstantiation()
	{
		String output = "";
		boolean isFirstLoop = true;
		
		for(Command command : this.commands)
		{
			if( ! isFirstLoop)
			{
				output += "\t";
			}
			
			output += command.getInstantiation() + System.getProperty("line.separator");
			
			isFirstLoop = false;
		}
		
		return output;
	}
}
