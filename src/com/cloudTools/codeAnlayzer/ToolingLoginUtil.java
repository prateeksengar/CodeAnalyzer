package com.cloudTools.codeAnlayzer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.tooling.ToolingConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class ToolingLoginUtil 
{
	public static ToolingConnection login() throws Exception 
	{
		Properties prop = new Properties();
		InputStream input = new FileInputStream("config.properties");
		prop.load(input);
		
		//ask for users input
		final String USERNAME = prop.getProperty("sfuser");
		final String PASSWORD = prop.getProperty("sfpassword");
        final String URL = prop.getProperty("sfurl");
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
