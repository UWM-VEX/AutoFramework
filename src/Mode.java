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
			declarations += step.getDeclaration() + System.getProperty("line.separator");
		}
		
		return declarations;
	}
	
	public String getInstantiations()
	{
		String instantiations = "if(autonomousSelection == " + this.name + ")" + System.getProperty("line.separator") + "{" + System.getProperty("line.separator");
		
		for(Step step : this.steps)
		{
			instantiations += "\t" + step.getInstantiation() + System.getProperty("line.separator");
		}
		
		instantiations += "}";
		
		return instantiations;
	}
	
	public String getExecutions()
	{
		String executions = "\t\tcase(" + this.name + "):" + System.getProperty("line.separator")
				+ "\t\tswitch(autonomousInfo.step)" + System.getProperty("line.separator")
				+ "\t\t{" + System.getProperty("line.separator");
		
		int i = 1;
		
		for(Step step : this.steps)
		{
			executions += step.getExecution(i++) + System.getProperty("line.separator");
		}
		
		executions +=  System.getProperty("line.separator") + System.getProperty("line.separator") + "\t\t\tdefault:" + System.getProperty("line.separator")
				+ "\t\t\t\tisAuto = 0;" + System.getProperty("line.separator")
				+ "\t\t\t\tbreak;" + System.getProperty("line.separator")
				+ "\t\t}" + System.getProperty("line.separator")
				+ "\t\tbreak;";
		
		return executions;
	}
}
