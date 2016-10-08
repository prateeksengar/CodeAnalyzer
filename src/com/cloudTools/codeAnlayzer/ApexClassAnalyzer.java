package com.cloudTools.codeAnlayzer;

import java.io.FileWriter;

import com.sforce.soap.tooling.Method;
import com.sforce.soap.tooling.SymbolTable;
import com.sforce.soap.tooling.sobject.ApexClass;

public class ApexClassAnalyzer {
	
	public static void scanApexClass(ApexClass apCl, String type, FileWriter fileWriter)
	{
		try
		{
			System.out.println("---------------------------------------------------------");
			System.out.println("SCANNING "+apCl.getName());
			SymbolTable apClSymTable = apCl.getSymbolTable();
			
			Method[] methList = apClSymTable.getMethods();
			
			if(type != null)
			{
				if(type.equalsIgnoreCase("Regular"))
				{
					
					//Verify if class contains class comments
					CodeCommentAnalyzer.checkClassComment(apCl.getName(), apCl.getBody(), fileWriter);
					//Verify method comments
					CodeCommentAnalyzer.checkMethodComment(apCl.getName(), apCl.getBody(), methList, fileWriter);
					//method name
					NamingConventionAnalyzer.checkMethodName(apCl.getName(), methList, fileWriter);
					//class name
					NamingConventionAnalyzer.checkClassName(apCl.getName(), fileWriter);
					//variable name
					NamingConventionAnalyzer.checkVariableName(apCl.getName(), apClSymTable, fileWriter);
					//verify for invalid use of custom label
					ApexCodeAnalyzer.checkInvalidCustomLabelUsage(apCl.getName(), apCl.getBody(), fileWriter);
					//verify class with too many methods
					ApexCodeAnalyzer.checkTooManyMethod(apCl.getName(), apCl.getBody(), methList, fileWriter);
					//verify class with too many lines of code
					ApexCodeAnalyzer.checkTooManyLineCode(apCl, fileWriter);
					//check for empty try catch
					ApexCodeAnalyzer.checkTooManyParameters(apCl.getName(), methList, fileWriter);
					//check empty catch block
					
					//check for unnecessary comparison
					ApexCodeAnalyzer.checkUnnecessaryComparison(apCl.getName(), apCl.getBody(), fileWriter);
					
				}
				else
				if(type.equalsIgnoreCase("Search"))
				{
					//search for string in class
					ApexCodeAnalyzer.checkForClassContent(apCl.getName(),apCl.getBody(), "Ecception Invalid");
				}
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Exception :"+e.getMessage());
			
		}
		
	}
	
	
}
