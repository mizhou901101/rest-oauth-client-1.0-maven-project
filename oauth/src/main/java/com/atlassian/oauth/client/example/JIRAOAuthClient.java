package com.atlassian.oauth.client.example;

import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * @since v1.0
 */
public class JIRAOAuthClient
{
    private static final String CALLBACK_URI = null;
    protected static final String CONSUMER_KEY = "your-consumer-key";
    /*
     *  Private key example, replace with your real one
     *  Pay attention on the format of private key, which doesn't contain header or return(\n) mark.
     */
    protected static final String CONSUMER_PRIVATE_KEY = 
    		"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC++AzH1lAhNENy"+
			"M0q5+ssssssssssssssssdsdsdsdsddddddddddddddddddddd/rbtO7J3sFQqCh"+
			"wFVD3O+LW1giVnqyGaCgEMUlwrD+hpYYDInK2Ij6yiCDuO4c95okXrMO8+1+zmrQ"+
			"XtNAGZajxXONz+Dw1dWnjVcpfNei37NQiYjVZ/kcHQPhfVpwDlmd7ONtCknpKqib"+
			"6MHnQDmgBIEQGakz83l35A3E33h3/r6BMcQIVvueDNb8n6X+eJXxLklZ+/HZV9rX"+
			"G66ciunP3mSkB1SeTZFfw7GTNjLxs6sE21E/RKbTTyQHPyzHf4TvqhGaHnRUjnH+"+
			"jwuAjJl3AgMBAAECggEABZAKJ0QJ3327PTdbROGQG1Jg0zP9dUkV32/NiGqARBZK"+
			"QlP2RsM4dIbu1jRo5oXkevImfrwerD+NmEiW8zwbSi7l+VIVjtyKPj9ezsPGDhPz"+
			"iU3MMblu/AcpPU0EuplHxylvA899KoVuvRUWCDb2mVymCgp1He7Mo23NjSrSyS64"+
			"El3Xb3B97cnRw7eqZOiGwgXJDMeAXbBZA2IYyTuo3tfgbmuqnqKb3jPb2jix3gh9"+
			"XzuAihI1ZZ+YPAGpis6qmwbFQNdep/+x3X3bIvnVF+tShcJl73G4G2A8KlOHDcfC"+
			"53QK4nHbcKp4d2JwQNxjEaJi9D/PS+Qi6Aop4SWogQKBgQDj0SDUn+RVztgD3IDZ"+
			"Tyv+/3QJi2aI8z2IglBjVQwIEatZTlB/dDTRKmWDo1wRu0LPc1rrb5j7DAgOsEoM"+
			"uHlfwynsbzaAnpw8cKi94aMyG2PPSGCKNlmc75d26KeREkSnw6Jyi5jsBDviWi5A"+
			"308hQEdBKEAd8mI7JhvFPZNFwQKBgQDWl/YS2NwYeBTq5L6I1FKkTSI2M6LLqcuB"+
			"fmzDgWGj4JYifLXY2GOI8r5/qQRC1aEPJEBH0vniBIrnMe4h/n4mh/LkaWnrFBCF"+
			"9/yUFqKDCU81mTb9p23p1NZU3/iFiTsoX0SgJSfKBr1HmI+yNAEx7jf0+CLKSZL2"+
			"llQsePHdNwKBgCJoMVcrlubGyKU8plErve9TUYknfqLsaby2QcHe1GRbls90HoBB"+
			"YsC8cHyDLaKX7605NOAeCLLRzF27jlOBgQNPjINjAI5IZjxSn4meDHHC5T4nJj15"+
			"m+sx3GRfGv+0DJ5x05UNNFlhAlJbYGT31RGmjZxvoc9kDiX0yXjupdGBAoGAMgQr"+
			"M4teCUn7vh/glO2/8VGVFsMEP6nsp9r6qLrAMj0HYTRmAgu1b1b8ta6EMruuFWRi"+
			"oxr2aQqcAXTuxveVGY/4Oeqj6Em97Cu3sCByck46Kwjx+z8J7EVSS2gEecNZiXhi"+
			"+1yNoJXfig9x9pwOZz/ZxPwMx89MNLMnHVJ3NRMCgYEArQK1k1pYjTV52j/5b0nn"+
			"FC6pG+3YouKeRkbYl0fYk5LMAlaffxA643Zk40AbDfkCZ9Po1zU+KG3lMv824ohZ"+
			"x9R9/K+8v++V5pmXhKrzTUn6lFU0wljuQSVXFLhGq2Mc0t465HKtaZuodjFne20E"+
			"xhFugFhlupu5IhLU59uiy00=";
    

    public enum Command
    {
        REQUEST_TOKEN("requestToken"),
        ACCESS_TOKEN("accessToken"), REQUEST("request");

        private String name;

        Command(final String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }
    }
    
    /**
     * Three steps to get access key
     * 1. Obtain a request token
     * 		Run as application with args: requestToken JIRA_BASE_URL
     * 		Replace JIRA_BASE_URL with the URL to your JIRA instance.
     * 		After executing this command you should see a response like
     * 		Token is W9QuE8hba6laXm2RcPGANVusKHnXUJcx
     * 		Token secret is rx4T2R3ax7an3H0vJLq9XB9DOP3aiNMl
     * 		Retrieved request token. go to http://localhost:8090/jira/plugins/servlet/oauth/authorize?oauth_token=W9QuE8hba6laXm2RcPGANVusKHnXUJcx
     * 2. Ask the user to authorize this request token
     * 		Go to the URL in system out and login into JIRA and approve the access. 
     * 		Afterwards JIRA will say that you have successfully authorised the access. 
     * 		It mentions a verification code which we need for the next step.
     * 3. Swap the request token for an access token
     * 		Run as application with args: accessToken JIRA_BASE_URL REQUEST_TOKEN TOKEN_SECRET VERIFIER
     * 		Replace JIRA_BASE_URL, REQUEST_TOKEN, TOKEN_SECRET and VERIFIER with the correct values.
     * 		In the response you should see
     * 		Access token is : QddAGsDSS0FkXCb1zRCCzzeShZRnUXYH
     * 		This access token will allow you to make authenticated requests to JIRA.
     * 
     * Now you can request JIRA with access token
     * 		Run as application with args: request ACCESS_TOKEN JIRA_REST_URL
     * 		Replace ACCESS_TOKEN, JIRA_REST_URL and ISSUE_KEY with the correct values.
     * 		JIRA_REST_URL, e.g. http://localhost:8090/jira/rest/api/2/issue/HSP-1
     * 		This will return the issue JSON object for the issue with the key "HSP-1"
     */
    public static void main(String[] args)
    {
        ArrayList<String> arguments = Lists.newArrayList(args);
        if (arguments.isEmpty())
        {
            throw new IllegalArgumentException("No command specified. Use one of " + getCommandNames() );
        }
        String action = arguments.get(0);
        if (Command.REQUEST_TOKEN.getName().equals(action))
        {
            String baseUrl = arguments.get(1);
            String callBack = "oob";
            if (arguments.size() == 3)
            {
                callBack = arguments.get(2);
            }
            AtlassianOAuthClient jiraoAuthClient = new AtlassianOAuthClient(CONSUMER_KEY, CONSUMER_PRIVATE_KEY, baseUrl, callBack);
            //STEP 1: Get request token
            TokenSecretVerifierHolder requestToken = jiraoAuthClient.getRequestToken();
            String authorizeUrl = jiraoAuthClient.getAuthorizeUrlForToken(requestToken.token);
            System.out.println("Token is " + requestToken.token);
            System.out.println("Token secret is " + requestToken.secret);
            System.out.println("Retrieved request token. go to " + authorizeUrl);
        }
        else if (Command.ACCESS_TOKEN.getName().equals(action))
        {
            String baseUrl = arguments.get(1);
            AtlassianOAuthClient jiraoAuthClient = new AtlassianOAuthClient(CONSUMER_KEY, CONSUMER_PRIVATE_KEY, baseUrl, CALLBACK_URI);
            String requestToken = arguments.get(2);
            String tokenSecret = arguments.get(3);
            String verifier = arguments.get(4);
            String accessToken = jiraoAuthClient.swapRequestTokenForAccessToken(requestToken, tokenSecret, verifier);
            System.out.println("Access token is : " + accessToken);
        }
        else if (Command.REQUEST.getName().equals(action))
        {
            AtlassianOAuthClient jiraoAuthClient = new AtlassianOAuthClient(CONSUMER_KEY, CONSUMER_PRIVATE_KEY, null, CALLBACK_URI);
            String accessToken = arguments.get(1);
            String url = arguments.get(2);
            String responseAsString = jiraoAuthClient.makeAuthenticatedRequest(url, accessToken);
            System.out.println("RESPONSE IS" + responseAsString);
        }
        else
        {
            System.out.println("Command " + action + " not supported. Only " + getCommandNames() + " are supported.");
        }
    }

    private static String getCommandNames()
    {
        String names = "";
        for (Command value : Command.values())
        {
            names += value.getName() + " ";
        }
        return names;
    }
}
