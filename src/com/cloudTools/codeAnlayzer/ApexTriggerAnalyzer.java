package com.cloudTools.codeAnlayzer;

import java.io.FileWriter;

import com.sforce.soap.tooling.sobject.ApexTrigger;

public class ApexTriggerAnalyzer {
	public static void scanApexTrigger(ApexTrigger apTrg, FileWriter fileWriter)
	{
		try
		{
			System.out.println("Apex Name "+apTrg.getName()+" Entity id "+apTrg.getEntityDefinitionId());
			//verify trigger naming convention
			NamingConventionAnalyzer.checkTriggerName(apTrg.getName(),fileWriter);
			//verify trigger comments
			CodeCommentAnalyzer.checkTriggerComment(apTrg.getName(), apTrg.getBody(), fileWriter);
			//verify number of lines
			ApexCodeAnalyzer.checkTooManyLineTrg(apTrg, fileWriter);
			//verify number of trigger on object
		}
		catch(Exception e)
		{
			
		}
	}
}
