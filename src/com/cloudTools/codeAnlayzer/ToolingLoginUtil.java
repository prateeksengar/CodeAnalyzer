package com.cloudTools.codeAnlayzer;

import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.tooling.ToolingConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class ToolingLoginUtil 
{
	public static ToolingConnection login() throws ConnectionException 
	{
		final String USERNAME = "pkumarsinghsengar@cognizant.com.full";
        final String PASSWORD = ""; 
        
        final String URL = "https://wu--full.cs2.my.salesforce.com/services/Soap/u/37.0";
        System.out.println(URL);
        final LoginResult loginResult = loginToSalesforce(USERNAME, PASSWORD, URL);
        return createToolingConnection(loginResult);
    }

    private static ToolingConnection createToolingConnection(final LoginResult loginResult) throws ConnectionException 
    {
        final ConnectorConfig config = new ConnectorConfig();
        //set tooling api endpoint
        config.setServiceEndpoint(loginResult.getServerUrl().replace('u', 'T'));
        config.setSessionId(loginResult.getSessionId());
        return new ToolingConnection(config);
    }

    private static LoginResult loginToSalesforce(final String username, final String password, final String loginUrl) throws ConnectionException 
    {
        final ConnectorConfig config = new ConnectorConfig();
        config.setAuthEndpoint(loginUrl);
        config.setServiceEndpoint(loginUrl);
        config.setManualLogin(true);
        return (new PartnerConnection(config)).login(username, password);
    }

}
