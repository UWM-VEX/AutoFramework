
public class CommandDoneCriteria extends DoneCriteria {
	private String internalReference;
	
	public CommandDoneCriteria()
	{
		this("");
	}
	
	public CommandDoneCriteria(String internalReference)
	{
		super.cText = Main.getCommandCText() + ".isFinished";
		this.internalReference = internalReference;
	}
}
