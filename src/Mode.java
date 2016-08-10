import java.util.ArrayList;

public class Mode {
	private ArrayList<Step> steps;
	private String name;
	
	public Mode(String header) throws Exception
	{
		this.steps = new ArrayList<Step>();
		String[] headerEntries = header.split(" ");
		try
		{
			this.name = headerEntries[1];
		}
		catch (Exception e)
		{
			System.out.println("Error: Mode header must contain a name.");
			throw new Exception();
		}
	}
	
	public void addStep(Step step)
	{
		this.steps.add(step);
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String toString()
	{
		return this.name;
	}
	
	public String getDeclarations()
	{
		String declarations = "";
		
		for(Step step : this.steps)
		{
			declarations += step.getDeclaration() + "\n";
		}
		
		return declarations;
	}
	
	public String getInstantiations()
	{
		String instantiations = "if(autonomousSelection == " + this.name + ")\n{\n";
		
		for(Step step : this.steps)
		{
			instantiations += "\t" + step.getInstantiation() + "\n";
		}
		
		instantiations += "}";
		
		return instantiations;
	}
	
	public String getExecutions()
	{
		String executions = "\tcase(" + this.name + "):\n"
				+ "\t\tswitch(autonomousInfo.step)\n"
				+ "{\n";
		
		int i = 1;
		
		for(Step step : this.steps)
		{
			executions += step.getExecution(i++) + "\n";
		}
		
		executions += "\n\n\t\t\tdefault:\n"
				+ "\t\t\t\tisAuto = 0;\n"
				+ "\t\t\t\tbreak;\n"
				+ "\t\t\t}\n"
				+ "\t\tbreak;";
		
		return executions;
	}
}
