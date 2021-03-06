import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.*;

public class Main {

	public static ArrayList<Method> methods;
	private static int commandCounter = 0;
	public static final boolean PRODUCTION = true;
	
	public static void main(String[] args)
	{
		String fileName = args[0];
		String line = null;
		ArrayList<Mode> modes = new ArrayList<Mode>();
		boolean inBlock = false;
		Block block = null;
		Mode mode = null;
		boolean inComment = false;
		boolean inMode = false;
		
		try {
			defineMethods();
		} catch (XMLException e1) {
			System.out.println("Methods XML Exception.");
			e1.printStackTrace();
			System.exit(1);
		}
		
		try
		{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(fileReader);
			int id = 0;
			while((line = reader.readLine()) != null)
			{
				line = line.trim();
				
				if(line.isEmpty())
				{
					continue;
				}
				
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
				
				if(line.indexOf("//") != -1)
				{
					line = line.substring(0, line.indexOf("//"));
				}
				
				if(line.trim().length() == 0)
					continue;
				
				if(inMode)
				{
					if(line.indexOf("EndMode") != -1)
					{
						inMode = false;
						modes.add(mode);
						continue;
					}
					
					if(line.indexOf("//") != -1)
					{
						if(line.indexOf("//") == 0)
						{
							continue;
						}
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
								block.addCommand(line, ++id);
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
							mode.addStep(new Command(line, ++id));
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
			
			if(inMode)
			{
				System.out.println("Error: Mode not closed.");
				System.exit(1);
			}
			
			if(inBlock)
			{
				System.out.println("Error: Block not closed.");
				System.exit(1);
			}
			
			for(Mode element : modes)
			{
				System.out.println(element);
			}
			
			reader.close();
			
			String pathToCode = "";
			
			if(Main.PRODUCTION)
			{
				pathToCode = args[1];
			}
			else
			{
				pathToCode = "\\robot\\code";
			}
			
			Output.outputToCode(modes, pathToCode);
		}
		catch(IOException e)
		{
			System.out.println("File not found.");
		}
		catch(Exception e)
		{
			System.out.println("An unspecified error has occurred");
			e.printStackTrace();
		}
	}
	
	public static void defineMethods() throws XMLException
	{
		MethodIdentifier methodIdentifier = new MethodIdentifier("methods.xml");
		methods = methodIdentifier.parseMethods();
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
