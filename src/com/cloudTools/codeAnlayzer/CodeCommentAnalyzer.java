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
		
		for(Method methodInstance: methodArray)
		{
			//add to list
			methodPosList.add(methodInstance.getLocation().getLine());
			//create method signature
			System.out.println(className+" : "+methodInstance.getName()+" : "+ methodInstance.getLocation().getLine());
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
			
			//iterate through lines
			System.out.println("Reading lines "+methodPosList.get(counter)+" to "+methodPosList.get(counter+1));
			for(int x=methodPosList.get(counter); x<methodPosList.get(counter+1); x++)
			{	
				System.out.println(classLines[x-1]);
				if(classLines[x-1].contains("//"))
				{
					containsComment = true;
				}
			}
			if(containsComment)
			{
				System.out.println("Method"+y+" contains comment");
			}
			counter = counter + 1;
		}
		
	}
}
