import java.util.ArrayList;

import exceptions.*;

public class Main {

	public static ArrayList<Method> methods;
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
	}

	public static Method identifyMethod(String text) throws MethodNotFoundException
	{
		for(Method method : methods)
		{
			if(text.equals(method.getJavaCommand()))
				return method;
		}
		
		throw new MethodNotFoundException();
	}
}
