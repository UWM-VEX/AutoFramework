import java.util.ArrayList;

public class BlockDoneCriteria extends DoneCriteria {
	private ArrayList<String> criterias = new ArrayList<String>();
	
	void addCommand(String command)
	{
		String criteria = "";
		
		if(command.indexOf("&&") != -1)
		{
			criteria = "&&";
		}
		else if(command.indexOf("||") != -1)
		{
			criteria = "||";
		}
		
		this.criterias.add(criteria);
	}
	
	String getNextCriteria()
	{
		return this.criterias.remove(0);
	}
}
