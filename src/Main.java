import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.*;

public class Main {

	public static ArrayList<Method> methods;
	public static ArrayList<Command> commands;
	private static int commandCounter = 0;
	
	public static void main(String[] args)
	{
		String fileName = args[0];
		String line = null;
		ArrayList<Step> steps = new ArrayList<Step>();
		boolean inBlock = false;
		Block block = null;
		
		try
		{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(fileReader);
			while((line = reader.readLine()) != null)
			{
				if(line.indexOf('{') != -1)
				{
					inBlock = true;
					block = new Block();
				}
				else if(inBlock)
				{
					if(line.indexOf('}') != -1)
					{
						inBlock = false;
						steps.add(block);
					}
					else
					{
						try
						{
							block.addCommand(line);
						}
						catch (MethodNotFoundException e)
						{
							System.out.println("Undefined Method: " + line);
							System.exit(1);
						}
						catch (PropertyNotFoundException e)
						{
							System.out.println("undefined Property: " + line);
							System.exit(1);
						}
					}
				}
				else
				{
					try
					{
						steps.add(new Command(line));
					}
					catch (MethodNotFoundException e)
					{
						System.out.println("Undefined Method: " + line);
						System.exit(1);
					}
					catch (PropertyNotFoundException e)
					{
						System.out.println("undefined Property: " + line);
						System.exit(1);
					}
				}
			}
			
			for(Step element : steps)
			{
				System.out.println(element);
			}
			
			reader.close();
		}
		catch(IOException e)
		{
			System.out.println("File not found.");
		}
	}

	public static Method identifyMethod(String text) throws MethodNotFoundException
	{
		for(Method method : methods)
		{
			if(text.equals(method.getJavaCommand()))
				return method;
		}
		
		throw new MethodNotFoundException();
	}
	
	public static String getCommandCText()
	{
		return "command" + ++commandCounter;
	}
}
