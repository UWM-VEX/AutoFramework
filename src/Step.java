
public abstract class Step {
	protected DoneCriteria doneCriteria;
	private String declarationText;
	private String instantiationText;
	private String referenceText;
	private int timeout = -1;
	private int minTime = -1;
	
	public abstract String toString();
}
