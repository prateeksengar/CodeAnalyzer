package com.cloudTools.codeAnlayzer;

import java.io.FileWriter;
import java.io.StringReader;
import java.util.ArrayList;

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
		
		//find out all methods of a class and get the line number
		for(Method methodInstance: methodArray)
		{
			//add to list
			methodPosList.add(methodInstance.getLocation().getLine());
		}
		
		//get list of methods
		int methodCount = methodPosList.size();
		System.out.println("contains "+methodCount+ "methods");
		
		//get lines of class
		String[] classLines = classBody.split("\\r?\\n");
		System.out.println("class contains "+classLines.length +" lines");
		
		
		int counter = 0;
		//iterate for all methods
		for(int y=0; y< methodCount-1; y++)
		{
			System.out.println("Scanning Method "+y);
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
				if(codeLine.contains("//"))
				{
					containsComment = true;
				}
				
			}
			if(!containsComment)
			{
				System.out.println(className+" contains method"+y+" which does not contains comment");
			}
			counter = counter + 1;
		}
		
	}
}
