package com.cloudTools.codeAnlayzer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.sforce.soap.tooling.QueryResult;
import com.sforce.soap.tooling.ToolingConnection;
import com.sforce.soap.tooling.sobject.ApexClass;
import com.sforce.soap.tooling.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class InitiateAnalyzer {
	
	private ToolingConnection toolingConnection;
	
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
			System.out.println("Connection established \n");
			
			//ask for users choice
			String userInp = getUsersInput();
			while(userInp != null && !userInp.equals("99"))
			{
				if(userInp.equals("1"))
				{
					System.out.println("--processing input--");
					System.out.println("--begin anlayze all class--");
					getAllApexClass();
					break;
				}
				else
					if(userInp.equals("2"))
					{
						System.out.println("--processing input--");
						System.out.println("--begin anlayze selective class--");
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
			System.out.println("1: Analyze All Class");
			System.out.println("2: Analyze Selective Class");
			System.out.println("3: Analyze All Triggers");
			System.out.println("4: Analyze Selective Triggers");
			System.out.println("5: Analyze All Visualforce pages");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			userInput = reader.readLine();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return userInput;
	}
	
	private void getAllApexClass() throws ConnectionException
	{
		QueryResult qr = toolingConnection.query("select Id,Name,Body,SymbolTable from ApexClass where NamespacePrefix = null order by Name");
		Boolean done = false;
		
		if(qr.getSize() > 0)
		{
			System.out.println("Total apex classes found : "+qr.getSize());
			while(! done)
			{
				for(SObject sObj : qr.getRecords())
				{
					ApexClass apexCl = (ApexClass)sObj;
					ApexClassAnalyzer.scanApexClass(apexCl,"Regular");
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
	

}
