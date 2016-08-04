package com.cloudTools.codeAnlayzer;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import com.sforce.soap.tooling.QueryResult;
import com.sforce.soap.tooling.ToolingConnection;
import com.sforce.soap.tooling.sobject.ApexClass;
import com.sforce.soap.tooling.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class InitiateAnalyzer {
	
	private ToolingConnection toolingConnection;
	private static final String OUTPUT_LOCATION = "C:\\Users\\prate\\Documents\\codeAnalysis.csv";
	private static final String FILE_HEADER = "Component Name, Observation \n";
	
	FileWriter fileWriter = null;

	//constructor
	public InitiateAnalyzer()
	{

	}
	
	public static void main(String[] args) {
		InitiateAnalyzer instance = new InitiateAnalyzer();
		instance.execute();
		
	}
	
	private void execute()
	{
		try
		{
			this.toolingConnection = ToolingLoginUtil.login();
			
			fileWriter = new FileWriter(OUTPUT_LOCATION);
			fileWriter.append(FILE_HEADER);
			
			System.out.println("Connection established \n");
			
			System.out.println("1: Analyze All Class");
			System.out.println("2: Analyze Selective Class");
			System.out.println("3: Analyze All Triggers");
			System.out.println("4: Analyze Selective Triggers");
			System.out.println("5: Analyze All Visualforce pages");
			System.out.println("6: Analyze Selective Visualforce pages");
			System.out.println("7: Search for String");
			
			//ask for users choice
			String userInp = getUsersInput();
			while(userInp != null && !userInp.equals("99"))
			{
				if(userInp.equals("1"))
				{
					System.out.println("-- Analyzing all Apex Classes --");
					getAllApexClass(fileWriter);
					fileWriter.flush();
					fileWriter.close();
					break;
				}
				else
					if(userInp.equals("2"))
					{
						System.out.println("Enter Class Name: ");
						String className = getUsersInput();
						getSelectiveApexClass(className, fileWriter);
						fileWriter.flush();
						fileWriter.close();
						break;
					}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private String getUsersInput()
	{
		String userInput = "";
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			userInput = reader.readLine();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return userInput;
	}

	
	private void getAllApexClass(FileWriter fileWriter) throws ConnectionException
	{
		QueryResult qr = toolingConnection.query("select Id,Name,Body,SymbolTable from ApexClass where NamespacePrefix = null order by Name limit 20");
		Boolean done = false;
		
		if(qr.getSize() > 0)
		{
			System.out.println("Total apex classes found : "+qr.getSize());
			while(! done)
			{
				for(SObject sObj : qr.getRecords())
				{
					ApexClass apexCl = (ApexClass)sObj;
					ApexClassAnalyzer.scanApexClass(apexCl,"Regular", fileWriter);
				}
				if (qr.isDone())
				{
					done = true;
				}
				else
				{
					qr = toolingConnection.queryMore(qr.getQueryLocator());
				}
			}
			
		}	
	}
	
	private void getSelectiveApexClass(String className, FileWriter fileWriter) throws ConnectionException
	{
		QueryResult qr = toolingConnection.query("select Id,Name,Body,SymbolTable from ApexClass where NamespacePrefix = null and Name = '"+className+"'");
		
		if(qr.getSize() > 0)
		{
			System.out.println("Total apex classes found : "+qr.getSize());
			for(SObject sObj : qr.getRecords())
			{
				ApexClass apexCl = (ApexClass)sObj;
				ApexClassAnalyzer.scanApexClass(apexCl,"Regular", fileWriter);
			}
			
		}	
	}
	

}
