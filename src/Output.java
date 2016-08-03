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
	public static void writeAutoH(ArrayList<Mode> modes) throws Exception
	{
		FileReader fileReader;
		
		try
		{
			fileReader = new FileReader("\\include\\Auto.h");
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error: Cannot find Auto.h file.");
			throw e;
		}
		
		BufferedReader reader = new BufferedReader(fileReader);
		String line;
		PrintWriter writer = new PrintWriter("\\include\\Auto.h.temp");
		
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
					writer.println("#define " + mode + i++);
				}
			}
		}
		
		writer.close();
		File oldFile = new File("\\include\\Auto.h");
		Path oldFilePath = oldFile.toPath();
		Files.delete(oldFilePath);
		
		File tempFile = new File("\\include\\Auto.h.temp");
		File newFile = new File("\\include\\Auto.h");
		
		if( ! tempFile.renameTo(newFile))
		{
			throw new Exception("An error occurred while rewriting Auto.h");
		}
	}
	
	public static void writeInitC(ArrayList<Mode> modes) throws Exception
	{
		FileReader fileReader;
		
		try
		{
			fileReader = new FileReader("\\src\\init.c");
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error: Cannot find init.c file.");
			throw e;
		}
		
		BufferedReader reader = new BufferedReader(fileReader);
		String line;
		PrintWriter writer = new PrintWriter("\\include\\init.c.temp");
		
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
		
		writer.close();
		File oldFile = new File("\\src\\init.c");
		Path oldFilePath = oldFile.toPath();
		Files.delete(oldFilePath);
		
		File tempFile = new File("\\src\\init.c.temp");
		File newFile = new File("\\src\\init.c");
		
		if( ! tempFile.renameTo(newFile))
		{
			throw new Exception("An error occurred while rewriting init.c");
		}
	}
	
	public static void writeAutoC(ArrayList<Mode> modes) throws Exception
	{
		FileReader fileReader;
		
		try
		{
			fileReader = new FileReader("\\src\\auto.c");
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error: Cannot find auto.c file.");
			throw e;
		}
		
		BufferedReader reader = new BufferedReader(fileReader);
		String line;
		PrintWriter writer = new PrintWriter("\\src\\auto.c.temp");
		
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
					writer.println("#define " + mode + i++);
				}
			}
		}
		
		writer.close();
		File oldFile = new File("\\src\\auto.c");
		Path oldFilePath = oldFile.toPath();
		Files.delete(oldFilePath);
		
		File tempFile = new File("\\src\\auto.c.temp");
		File newFile = new File("\\src\\auto.c");
		
		if( ! tempFile.renameTo(newFile))
		{
			throw new Exception("An error occurred while rewriting auto.c");
		}
	}
}
