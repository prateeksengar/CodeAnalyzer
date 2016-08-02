package com.cloudTools.codeAnlayzer;

import com.sforce.soap.tooling.Method;
import com.sforce.soap.tooling.Position;
import com.sforce.soap.tooling.Symbol;
import com.sforce.soap.tooling.SymbolTable;
import com.sforce.soap.tooling.VisibilitySymbol;
import com.sforce.soap.tooling.sobject.ApexClass;

public class ApexClassAnalyzer {
	
	public static void scanApexClass(ApexClass apCl)
	{
		//verify for invalid use of custom label
		//checkInvalidCustomLabelUsage(apCl.getName(), apCl.getBody());
		
		SymbolTable apClSymTable = apCl.getSymbolTable();
		Method[] methList = apClSymTable.getMethods();
		
		//verify for invalid method name
		NamingConventionAnalyzer.checkMethodName(apCl.getName(), methList);
		NamingConventionAnalyzer.checkClassName(apCl.getName());
		
		//analyze variables
		//analyzeVariables(apCl.getName(), apClSymTable);
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
	
	static void checkInvalidCustomLabelUsage(String className, String classBody)
	{
		if(classBody.contains("== Label.") | classBody.contains("==Label.") | classBody.contains("!=Label.") | classBody.contains("!= Label."))
		{
			System.out.println(className + " contains invalid custom label");
		}
	}
	
	

}
