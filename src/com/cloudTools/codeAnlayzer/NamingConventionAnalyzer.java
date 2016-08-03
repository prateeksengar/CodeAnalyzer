package com.cloudTools.codeAnlayzer;

import java.io.FileWriter;

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
	static void checkClassName(String className, FileWriter fileWriter) throws Exception
	{
		String namingPattern = "[A-Z]+[a-z0-9]+[A-Za-z0-9]*";
		
		if(!className.endsWith("_Test"))
		{
			if(className.startsWith("_"))
			{
				System.out.println("Name starts with _ : "+ className);
				fileWriter.append(""+className+", Class Name starts with _ \n");
			}
			if(className.contains("_") && !className.endsWith("_Test"))
			{
				System.out.println("Name should not contain _ : "+ className);
				fileWriter.append(""+className+", Class Name contains _ \n");
			}
			if(!className.matches(namingPattern))
			{
				System.out.println("Name should be upperCamelCase : "+ className);
				fileWriter.append(""+className+", Class Name should be in UpperCamelCase \n");
			}
			if(className.startsWith("Test") || className.startsWith("test"))
			{
				System.out.println("Name should not start with Test : "+ className);
				fileWriter.append(""+className+", Class Name should not start with Test \n");
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
	static void checkMethodName(String className, Method[] methodArray, FileWriter fileWriter) throws Exception
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
					fileWriter.append(""+className+" : "+methodInstance.getName()+", Method Name should start with is or has \n");
				}
			}
			
			//all method name should be in lowerCamelCase
			String namingPattern = "[a-z]+[A-Z0-9][a-z0-9]+[A-Za-z0-9]*";
			if(!methodInstance.getName().matches(namingPattern))
			{
				System.out.println(methodInstance.getName() + " method name should be camel case");
				fileWriter.append(""+className+" : "+methodInstance.getName()+", Method Name should be in lowerCamelCase \n");
			}
			
		}
	}
	
	
}
