package com.cloudTools.codeAnlayzer;

import com.sforce.soap.tooling.Method;
import com.sforce.soap.tooling.Symbol;

public class NamingConventionAnalyzer {

	/*
	 * Verify naming convention for class name
	 * 1: UpperCamelCase should be used
	 * 2: Name should start with capital
	 * 3: Underscores should be avoided
	 * 4: Test class should be suffixed with _Test
	 */
	static void checkClassName(String className)
	{
		String namingPattern = "[A-Z]+[a-z0-9]+[A-Za-z0-9]*";
		
		if(!className.endsWith("_Test"))
		{
			if(className.startsWith("_"))
			{
				System.out.println("Name starts with _ : "+ className);
			}
			else
				if(className.contains("_") && !className.endsWith("_Test"))
				{
					System.out.println("Name should not contain _ : "+ className);
				}
				else
					if(!className.matches(namingPattern))
					{
						System.out.println("Name should be upperCamelCase : "+ className);
					}
					else
						if(className.startsWith("Test") || className.startsWith("test"))
						{
							System.out.println("Name should not start with Test : "+ className);
						}
		}
	}
	
	/*
	 * verify naming convention for visualforce pages
	 * 1: underscore should not be used
	 * 2: UpperCamelCase should be used
	 */
	static void checkPageName(String pgName)
	{
		
	}
	
	/*
	 * verify naming convention for variables
	 * 1: Single variable names should be avoided
	 */
	static void checkVariableName(Symbol[] variableList, String clName)
	{
		
	}
	
	/*
	 * Verify naming convention for methods
	 * 1: Method that returns Boolean should start with is or has
	 * 2: Method name should be in lowerCamelCase format
	 */
	static void checkMethodName(String className, Method[] methodArray)
	{
		for(Method methodInstance: methodArray)
		{
			//System.out.println("Analyzing Method Name : "+methodInstance.getName()+"...");
			
			//if method return boolean the method name should reflect same
			if(methodInstance.getReturnType().equalsIgnoreCase("Boolean"))
			{
				if(!methodInstance.getName().startsWith("is") && !methodInstance.getName().startsWith("has"))
				{
					System.out.println(methodInstance.getName() + " method name should start with is or has");
				}
			}
			
			//all method name should be in lowerCamelCase
			String namingPattern = "[a-z]+[A-Z0-9][a-z0-9]+[A-Za-z0-9]*";
			if(!methodInstance.getName().matches(namingPattern))
			{
				System.out.println(methodInstance.getName() + " method name should be camel case");
			}
			
		}
	}
	
	
}
