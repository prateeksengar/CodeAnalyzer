package com.cloudTools.codeAnlayzer;

import java.io.FileWriter;

import com.sforce.soap.tooling.Method;
import com.sforce.soap.tooling.Position;
import com.sforce.soap.tooling.Symbol;
import com.sforce.soap.tooling.SymbolTable;
import com.sforce.soap.tooling.VisibilitySymbol;
import com.sforce.soap.tooling.sobject.ApexClass;

public class ApexClassAnalyzer {
	
	public static void scanApexClass(ApexClass apCl, String type, FileWriter fileWriter)
	{
		SymbolTable apClSymTable = apCl.getSymbolTable();
		Method[] methList = apClSymTable.getMethods();
		
		if(type != null)
		{
			if(type.equalsIgnoreCase("Regular"))
			{
				try
				{
				//verify for invalid use of custom label
				checkInvalidCustomLabelUsage(apCl.getName(), apCl.getBody(), fileWriter);
				
				//verify for naming convention
				//method name
				NamingConventionAnalyzer.checkMethodName(apCl.getName(), methList, fileWriter);
				//class name
				NamingConventionAnalyzer.checkClassName(apCl.getName(), fileWriter);
				//variable name
				
				
				
				//analyze variables
				//analyzeVariables(apCl.getName(), apClSymTable);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
				if(type.equalsIgnoreCase("Search"))
				{
					//search for string in class
					checkForClassContent(apCl.getName(),apCl.getBody(), "EXception EInvalid");
				}
		}
		
	}
	
	static void analyzeVariables(String className, SymbolTable apClSymTable)
	{
		System.out.println("Global Variables");
		for(VisibilitySymbol vs: apClSymTable.getProperties())
		{
			System.out.println(vs.getName()+" : "+vs.getType());
			System.out.println(vs.getLocation().getColumn() +" : "+vs.getLocation().getLine());
		}
		
		System.out.println("Local Variables");
		
		Symbol[] variableList =  apClSymTable.getVariables();
		for(Symbol sym: variableList)
		{
			System.out.println(sym.getName()+" : "+sym.getType());
			Position pos = sym.getLocation();
			System.out.println(pos.getColumn() +" : "+pos.getLine());
		}
	}
	
	static void checkInvalidCustomLabelUsage(String className, String classBody, FileWriter fileWriter) throws Exception
	{
		if(classBody.contains("== Label.") | classBody.contains("==Label.") | classBody.contains("!=Label.") | classBody.contains("!= Label."))
		{
			System.out.println(className + " contains invalid custom label");
			fileWriter.append(""+className+", Class contains Custom Label in logical expression \n");
		}
	}
	
	static void checkForClassContent(String className, String classBody, String content)
	{
		if(classBody.contains(content))
		{
			System.out.println("Found: "+className);
		}
	}
	
	

}
