import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.*;

public class Main {

	public static ArrayList<Method> methods;
	private static int commandCounter = 0;
	
	public static void main(String[] args)
	{
		String fileName = args[0];
		String line = null;
		ArrayList<Step> steps = new ArrayList<Step>();
		ArrayList<Mode> modes = new ArrayList<Mode>();
		boolean inBlock = false;
		Block block = null;
		Mode mode = null;
		boolean inComment = false;
		boolean inMode = false;
		
		defineMethods();
		
		try
		{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(fileReader);
			while((line = reader.readLine()) != null)
			{
				if(inComment)
				{
					if(line.indexOf("##") != -1)
					{
						inComment = false;
					}
					
					continue;
				}
				else
				{
					if(line.indexOf("##") != -1)
					{
						inComment = true;
						continue;
					}
				}
				
				if(inMode)
				{
					if(line.indexOf("End Mode") != -1)
					{
						inMode = false;
						modes.add(mode);
						continue;
					}
					
					if(line.indexOf("//") != -1)
					{
						line = line.substring(0, line.indexOf("//"));
					}
					
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
							mode.addStep(block);
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
							mode.addStep(new Command(line));
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
					if(line.indexOf("Mode") != -1)
					{
						inMode = true;
						
						try
						{
							mode = new Mode(line);
						}
						catch (Exception e)
						{
							System.exit(1);
						}
					}
				}
			}
			
			for(Mode element : modes)
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
	
	public static void defineMethods()
	{
		try
		{
			methods = new ArrayList<Method>();
			Method driveToWP = new Method("DriveToWP", "DriveToWP", "initDriveToWP", "isFinished", new Property("double", "magnitude", "mag", "magnitude", "y"));
			methods.add(driveToWP);
		}
		catch (InvalidTypeException e)
		{
			System.out.println("Invalid Type");
			System.exit(1);
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
