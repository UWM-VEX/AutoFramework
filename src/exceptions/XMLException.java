package exceptions;

public class XMLException extends Exception{
	public XMLException(String message)
	{
		super();
		System.out.println("XML Exception: " + message);
	}
	
	public XMLException()
	{
		super();
	}
}
