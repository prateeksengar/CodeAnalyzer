package com.cloudTools.codeAnlayzer;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sforce.soap.tooling.Method;



public class CodeCommentAnalyzer {

	
	/*
	 * Analyze Class Comment
	 * 
	 */
	static void checkClassComment(String className, String classBody, FileWriter fileWriter) throws Exception
	{
		//check if class has class level commments
		if(!classBody.trim().startsWith("/**"))
		{
			fileWriter.append(""+className+", Class doesn't contains class comments \n");
		}
	}
	
	/*
	 * Analyze method comments
	 * 
	 */
	static void checkMethodComment(String className, String classBody, Method[] methodArray, FileWriter fileWriter) throws Exception
	{
		ArrayList<Integer> methodPosList = new ArrayList<>();
		//map to store method name and start position
		Map<Integer,String> methodMap = new HashMap<Integer, String>();
		
		//find out all methods of a class and get the line number
		for(Method methodInstance: methodArray)
		{
		    //add to list
			methodPosList.add(methodInstance.getLocation().getLine());
			//add to map
			methodMap.put(methodInstance.getLocation().getLine(), methodInstance.getName());
		}
		
		//get list of methods
		int methodCount = methodPosList.size();
		//System.out.println("contains "+methodCount+ "methods");
		
		//get lines of class
		String[] classLines = classBody.split("\\r?\\n");
		//System.out.println("class contains "+classLines.length +" lines");
		
		
		int counter = 0;
		//iterate for all methods
		for(int y=0; y< methodCount-1; y++)
		{
			String methodName = "";
			if(methodMap.get(methodPosList.get(counter)) != null)
			{
				methodName = methodMap.get(methodPosList.get(counter));
			}
			
			System.out.println("Scanning Method "+methodName);
			Boolean containsComment = false;
			
			//iterate through lines and scan the method for comments
			System.out.println("Reading lines "+methodPosList.get(counter)+" to "+methodPosList.get(counter+1));
			
			//varibles for code parser
			Boolean hasMethodStarted = false;
			Boolean hasMethodEnded = false;
			int bracesCount = 0;
			
			for(int x=methodPosList.get(counter); x<methodPosList.get(counter+1); x++)
			{	
				String codeLine = classLines[x-1];
				
				//iterate through each line and count braces
				for(char c: codeLine.toCharArray())
				{
					if(hasMethodStarted && bracesCount == 0)
					{
						hasMethodEnded = true;
						//branch out to check for method comments
						//parameter startline, endline, methodname, classBody, classname, filewriter
						checkMethodBlockComment(x, methodPosList.get(counter+1),methodMap.get(methodPosList.get(counter+1)), classLines, className, fileWriter);
						break;
					}
					else
					{
						if(c == '{')
						{
							if(hasMethodStarted == false)
							{
								hasMethodStarted = true;
							}
							bracesCount++;
						}
						else if(c == '}')
							{
								bracesCount--;
							}
					}
				}
				
				//if method ends
				if(hasMethodEnded)
				{
					break;
				}
				
				//System.out.println(codeLine);
				//look if line contains comments
				if(codeLine.contains("//") && !codeLine.contains("="))
				{
					containsComment = true;
				}
				
			}
			if(!containsComment)
			{
				fileWriter.append(className+", contains method "+ methodName+" with no line comments \n");
			}
			counter = counter + 1;
		}
		
	}
	
	/*
	 * Analyze code block comments
	 * 
	 */
	static void checkMethodBlockComment(int x, int y, String methodName, String[] classLines, String className, FileWriter fileWriter) throws Exception
	{
		Boolean hasComments = false;
		for(int i=x-1; i<=y; i++)
		{
			if(classLines[i].contains("/**"))
			{
				hasComments = true;
			}
		}
		
		if(!hasComments)
		{
			fileWriter.append(className+", Contains Method: "+methodName+ " with no block comments \n");
		}
	}
}
