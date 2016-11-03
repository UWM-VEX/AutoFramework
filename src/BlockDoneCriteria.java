import java.util.ArrayList;

public class BlockDoneCriteria extends DoneCriteria {
	private ArrayList<String> criterias = new ArrayList<String>();
	
	void addCommand(String command)
	{
		String[] words = command.split(" ");
		String criteria = "";
		
		if(words.length < 3)
		{
			criteria = "&&";
		}
		
		for(int i = 2; words.length > i; i++)
		{
			criteria += words[i];
			
			if(i != (words.length - 1))
			{
				criteria += " ";
			}
		}
		
		this.criterias.add(criteria);
	}
	
	String getNextCriteria()
	{
		return this.criterias.remove(0);
	}
}
