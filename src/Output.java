import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Output {
	public static void outputToCode(ArrayList<Mode> modes) throws Exception
	{
		writeAutoH(modes);
		writeInitC(modes);
		writeAutoC(modes);
	}
	
	public static void writeAutoH(ArrayList<Mode> modes) throws Exception
	{
		System.out.println("Writing Auto.h");
		FileReader fileReader;
		
		try
		{
			fileReader = new FileReader(new File(System.getProperty("user.dir") + "\\robot\\code\\include\\", "Auto.h"));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error: Cannot find Auto.h file.");
			throw e;
		}
		
		BufferedReader reader = new BufferedReader(fileReader);
		String line;
		PrintWriter writer = new PrintWriter(new File(System.getProperty("user.dir") + "\\robot\\code\\include\\", "Auto.h.temp"));

		boolean hitDeclarations = false;
		boolean pastDeclarations = false;
		
		while((line = reader.readLine()) != null)
		{
			pastDeclarations = line.indexOf("// END OF MODES") != -1;
			
			if( ! hitDeclarations || pastDeclarations)
			{
				writer.println(line);
			}
			
			if( ! hitDeclarations && line.indexOf("// START OF MODES") != -1)
			{
				hitDeclarations = true;
				
				int i = 0;
				
				for(Mode mode : modes)
				{
					writer.println("#define " + mode + " " + i++);
				}
			}
		}
		
		reader.close();
		writer.close();
		
		System.out.println("Finished with writer");
				
		File oldFile = new File(System.getProperty("user.dir") + "\\robot\\code\\include\\", "Auto.h");
		Path oldFilePath = oldFile.toPath();
		Files.delete(oldFilePath);
		System.out.println("1");
		
		File tempFile = new File(System.getProperty("user.dir") + "\\robot\\code\\include\\", "Auto.h.temp");
		File newFile = new File(System.getProperty("user.dir") + "\\robot\\code\\include\\Auto.h");
		
		if( ! tempFile.renameTo(newFile))
		{
			throw new Exception("An error occurred while rewriting Auto.h");
		}
		
		System.out.println("Finished writing to Auto.h");
	}
	
	public static void writeInitC(ArrayList<Mode> modes) throws Exception
	{
		System.out.println("Writing to init.c");
		FileReader fileReader;
		
		try
		{
			fileReader = new FileReader(new File(System.getProperty("user.dir") + "\\robot\\code\\src\\", "init.c"));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error: Cannot find init.c file.");
			throw e;
		}
		
		BufferedReader reader = new BufferedReader(fileReader);
		String line;
		PrintWriter writer = new PrintWriter(new File(System.getProperty("user.dir") + "\\robot\\code\\src\\", "init.c.temp"));
		
		boolean hitDeclarations = false;
		boolean deletedLine = false;
		
		while((line = reader.readLine()) != null)
		{
			if( ! deletedLine)
			{
				writer.println(line);
			}
			else
			{
				deletedLine = false;
			}
			
			if( ! hitDeclarations && line.indexOf("// MODE DEFINITIONS") != -1)
			{
				hitDeclarations = true;
				deletedLine = true;
				
				int i = 0;
				
				String modesDefinition = "";
				
				for(Mode mode : modes)
				{
					modesDefinition += "\"" + mode.toString() + "\"";
					
					if(i != modes.size())
					{
						modesDefinition += ", ";
					}
				}
				
				writer.println("const char * selectionText[] = {" + modesDefinition + "};");
			}
		}
		
		reader.close();
		writer.close();
		File oldFile = new File(System.getProperty("user.dir") + "\\robot\\code\\src\\", "init.c");
		Path oldFilePath = oldFile.toPath();
		Files.delete(oldFilePath);
		
		File tempFile = new File(System.getProperty("user.dir") + "\\robot\\code\\src\\", "init.c.temp");
		File newFile = new File(System.getProperty("user.dir") + "\\robot\\code\\src\\init.c");
		
		if( ! tempFile.renameTo(newFile))
		{
			throw new Exception("An error occurred while rewriting init.c");
		}
		
		System.out.println("Finished writing to init.c");
	}
	
	public static void writeAutoC(ArrayList<Mode> modes) throws Exception
	{
		System.out.println("Writing to Auto.c");
		FileReader fileReader;
		
		try
		{
			fileReader = new FileReader(new File(System.getProperty("user.dir") + "\\robot\\code\\src\\", "auto.c"));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error: Cannot find auto.c file.");
			throw e;
		}
		
		BufferedReader reader = new BufferedReader(fileReader);
		String line;
		PrintWriter writer = new PrintWriter(new File(System.getProperty("user.dir") + "\\robot\\code\\src\\", "auto.c.temp"));
		
		boolean hitDeclarations = false;
		boolean pastDeclarations = false;
		boolean hitInstantiations = false;
		boolean pastInstantiations = false;
		boolean hitExecutions = false;
		boolean pastExecutions = false;
		
		while((line = reader.readLine()) != null)
		{
			pastDeclarations = line.indexOf("// END OF DECLARATIONS") != -1;
			pastInstantiations = line.indexOf("// END OF INSTANTIATIONS") != -1;
			pastExecutions = line.indexOf("// END OF EXECUTION") != -1;
			
			if( ! hitDeclarations || (pastDeclarations && ! hitInstantiations)
					|| (pastInstantiations && ! hitExecutions) || pastExecutions)
			{
				writer.println(line);
			}
			
			if( ! hitDeclarations && line.indexOf("// START OF DECLARATIONS") != -1)
			{
				hitDeclarations = true;
								
				for(Mode mode : modes)
				{
					writer.println(mode.getDeclarations());
				}
			}
			else if( ! hitInstantiations && line.indexOf("// START OF INSTANTIATIONS") != -1)
			{
				hitInstantiations = true;
				
				for(Mode mode : modes)
				{
					writer.println(mode.getInstantiations());
				}
			}
			else if( ! hitExecutions && line.indexOf("// START OF EXECUTIONS") != -1)
			{
				hitExecutions = true;
				
				for(Mode mode : modes)
				{
					writer.println(mode.getExecutions());
				}
			}
		}
		
		writer.close();
		reader.close();
		File oldFile = new File(System.getProperty("user.dir") + "\\robot\\code\\src\\", "auto.c");
		Path oldFilePath = oldFile.toPath();
		Files.delete(oldFilePath);
		
		File tempFile = new File(System.getProperty("user.dir") + "\\robot\\code\\src\\", "auto.c.temp");
		File newFile = new File(System.getProperty("user.dir") + "\\robot\\code\\src\\auto.c");
		
		if( ! tempFile.renameTo(newFile))
		{
			throw new Exception("An error occurred while rewriting auto.c");
		}
		System.out.println("Finished writing to auto.c");
	}
}
