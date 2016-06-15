import java.util.ArrayList;
import java.util.Arrays;

import exceptions.MethodNotFoundException;

public class Command extends Step {
	private Method method;
	private ArrayList<String> entries;
	
	public Command(String text) throws MethodNotFoundException
	{
		entries = new ArrayList<String>(Arrays.asList(text.split(" ")));
		this.method = Main.identifyMethod(entries.get(0));
	}
}
