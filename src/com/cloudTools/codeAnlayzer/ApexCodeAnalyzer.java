package com.cloudTools.codeAnlayzer;

import java.io.FileWriter;

import com.sforce.soap.tooling.Method;
import com.sforce.soap.tooling.sobject.ApexClass;

public class ApexCodeAnalyzer {
	/*
	 * Analyze too many methods
	 */
	static void checkTooManyMethod(String className, String classBody, Method[] methodArray, FileWriter fileWriter) throws Exception
	{
		Integer methodCount = 0;
		methodCount = methodArray.length;
		if(methodCount > ProjectConstants.MAX_METHOD_COUNT)
		{
			fileWriter.append(className+", Contains "+ methodCount+" methods try refactoring the class \n");
		}
	}
	
	/*
	 * Analyze too many lines of code
	 */
	static void checkTooManyLineCode(ApexClass apCl, FileWriter fileWriter) throws Exception
	{
		Integer lineOfCode = 0;
		lineOfCode = apCl.getLengthWithoutComments();
		if(lineOfCode > ProjectConstants.MAX_LINE_COUNT)
		{
			fileWriter.append(apCl.getName()+", Contains "+ lineOfCode+" lines of code try refactoring the class \n");
		}
	}
	
	/*
	 * Analyze too many parameter
	 */
	static void checkTooManyParameters(String className,Method[] methodArray, FileWriter fileWriter) throws Exception
	{
		Integer parameterCount = 0;
		//Iterate through methods and check parameter
		for(Method methodInstance: methodArray)
		{
			parameterCount = methodInstance.getParameters().length;
			if(parameterCount > ProjectConstants.MAX_PARAMETER_COUNT)
			{
				fileWriter.append(className+", Contains method with "+ parameterCount + " parameters try refactoring the method \n");
			}
		}
				
	}
	
	/*
	 * Analyze unnecessary comparison
	 */
	static void checkUnnecessaryComparison(String className, String classBody, FileWriter fileWriter) throws Exception
	{
		
		Integer lineNumber = 0;
		String[] classLines = classBody.split("\\r?\\n");
		for(String clLine: classLines)
		{
			lineNumber++;
			if(clLine.contains("== false") || clLine.contains("!= false") || clLine.contains("== true") || clLine.contains("!= true"))
			{
				fileWriter.append(className+" "+", Contains line # "+lineNumber+" with unnecessary comparison operation \n");
			}
		}
		
	}
	
	/*
	 * Find empty catch block 
	 */
	static void checkEmptyCatchBlock(String className, String classBody, FileWriter fileWriter) throws Exception
	{
		Integer lineNumber = 0;
		String[] classLines = classBody.split("\\r?\\n");
		for(String clsLine: classLines)
		{
			lineNumber++;
			if(clsLine.contains("catch") || clsLine.contains("Catch"))
			{
				fileWriter.append(className+" , Contains catch block at "+lineNumber+"# \n");
			}
		}
	}
	
	/*
	 * Check for custom labels in apex code
	 */
	static void checkInvalidCustomLabelUsage(String className, String classBody, FileWriter fileWriter) throws Exception
	{
		if(classBody.contains("== Label.") | classBody.contains("==Label.") | classBody.contains("!=Label.") | classBody.contains("!= Label."))
		{
			System.out.println(className + " contains invalid custom label");
			fileWriter.append(""+className+", Class contains Custom Label in logical expression \n");
		}
	}
	
	/*
	 * Search for content
	 */
	static void checkForClassContent(String className, String classBody, String content)
	{
		if(classBody.contains(content))
		{
			System.out.println("Found: "+className);
		}
	}

}
