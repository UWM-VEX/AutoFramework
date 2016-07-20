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
	
	public String toString()
	{
		String returnText = "Mode: " + this.name + "\n";
		
		for (Step step : this.steps)
		{
			returnText += step.toString();
		}
		
		returnText += "\nEnd Mode";
		
		return returnText;
	}
}
