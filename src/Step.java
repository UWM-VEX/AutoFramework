
public abstract class Step {
	protected DoneCriteria doneCriteria;
	private String declarationText;
	private String instantiationText;
	private String referenceText;
	private int timeout = -1;
	private int minTime = -1;
	
	public abstract String toString();
	public String getDeclaration()
	{
		return "";
	}
	public String getInstantiation()
	{
		return "";
	}
	public abstract String getExecution(int step);
}
