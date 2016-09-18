package com.cloudTools.codeAnlayzer;

import java.io.FileWriter;

import com.sforce.soap.tooling.Method;
import com.sforce.soap.tooling.sobject.ApexClass;

public class ApexCodeAnalyzer {
	private static final Integer MAX_METHOD_COUNT = 10;
	private static final Integer MAX_LINE_COUNT = 650;
	private static final Integer MAX_PARAMETER_COUNT = 3;
	/*
	 * Analyze too many methods
	 */
	static void checkTooManyMethod(String className, String classBody, Method[] methodArray, FileWriter fileWriter) throws Exception
	{
		Integer methodCount = 0;
		methodCount = methodArray.length;
		if(methodCount > MAX_METHOD_COUNT)
		{
			fileWriter.append(className+", contains "+ methodCount+" methods try refactoring the class \n");
		}
	}
	
	/*
	 * Analyze too many lines of code
	 */
	static void checkTooManyLineCode(ApexClass apCl, FileWriter fileWriter) throws Exception
	{
		Integer lineOfCode = 0;
		lineOfCode = apCl.getLengthWithoutComments();
		if(lineOfCode > MAX_LINE_COUNT)
		{
			fileWriter.append(apCl.getName()+", contains "+ lineOfCode+" lines of code try refactoring the class \n");
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
			if(parameterCount > MAX_PARAMETER_COUNT)
			{
				fileWriter.append(className+", contains method with "+ parameterCount + " parameters try refactoring the method \n");
			}
		}
				
	}

}
