# rest-oauth-client-1.0-maven-project
Atlassian-Java-OAuth-client Maven project means to create OAuth token of JIRA for accessing JIRA's RESTful service.

# Two major things to prepare
1. Generating Public/Private key pair (SSL/TLS)
2. Getting an access token

# Step by step
1. Generate private key using openssl
<pre><code>openssl genrsa -out dashboard_privkey.pem 2048
</code></pre>
Write down the pass phrase somewhere, you will need it when creating a public key.

2. Generate public key from the generated private key in the last step
<pre><code>openssl rsa -pubout -in dashboard_privkey.pem -out dashboard_pubkey.pem
</code></pre>

3. Convert private key format
<pre><code>openssl pkcs8 -topk8 -nocrypt -in dashboard_privkey.pem -out dashboard_privkey_pcks8.pem
</code></pre>

4. Obtain a request token from JIRA
    
    Check out this repository, import into your favouriate IDE. Find the Class JIRAOAuthClient, replace the values of CALLBACK_URI, CONSUMER_KEY and CONSUMER_PRIVATE_KEY. CONSUMER_KEY refers to the one you set up in JIRA's application links. Pay attention on the format of the CONSUMER_PRIVATE_KEY, which doesn't contain header or return(\n) mark.

    Run as application with args: 
    <pre><code>requestToken JIRA_BASE_URL</code></pre>
    Replace JIRA_BASE_URL with the URL to your JIRA instance.
    After executing this command you should see a response like

    >Token is W9QuE8hba6laXm2RcPGANVusKHnXUJcx
    Token secret is rx4T2R3ax7an3H0vJLq9XB9DOP3aiNMl
    Retrieved request token. go to http://localhost:8090/jira/plugins/servlet/oauth/authorize?oauth_token=W9QuE8hba6laXm2RcPGANVusKHnXUJcx

5. Ask the user to authorize this request token

    Go to the URL in system out and login into JIRA and approve the access. 
    Afterwards JIRA will say that you have successfully authorised the access. 
    It mentions a verification code which we need for the next step.
    
6. Swap the request token for an access token

    Run as application with args: 
    <pre><code>accessToken JIRA_BASE_URL REQUEST_TOKEN TOKEN_SECRET VERIFIER</code></pre>
    Replace JIRA_BASE_URL, REQUEST_TOKEN, TOKEN_SECRET and VERIFIER with the correct values.
    In the response you should see
    
    >Access token is : QddAGsDSS0FkXCb1zRCCzzeShZRnUXYH
    
    This access token will allow you to make authenticated requests to JIRA.
    
7. Now you can request JIRA with access token

    Run as application with args: 
    <pre><code>request ACCESS_TOKEN JIRA_REST_URL</code></pre>
    Replace ACCESS_TOKEN, JIRA_REST_URL and ISSUE_KEY with the correct values.
    JIRA_REST_URL, e.g. http://localhost:8090/jira/rest/api/2/issue/HSP-1
    This will return the issue JSON object for the issue with the key "HSP-1"
    You should see a response like:
    <pre><code>{
    "expand": "html,names,schema",
    "id": "10000",
    "self": "http://localhost:8090/jira/rest/api/2/issue/HSP-1",
    "key": "HSP-1",
    "fields": {
    "summary": "Bug due two weeks ago",
    "issuetype": {
    "self": "http://localhost:8090/jira/rest/api/2/issuetype/1",
    "id": "1",
    "description": "A problem which impairs or prevents the functions of the product.",
    "iconUrl": "http://localhost:8090/jira/images/icons/bug.gif",
    "name": "Bug",
    "subtask": false
    },
    "status": {
    "self": "http://localhost:8090/jira/rest/api/2/status/5",
    "iconUrl": "http://localhost:8090/jira/images/icons/status_resolved.gif",
    "name": "Resolved",
    "id": "5"
    },
    "labels": [(0)],
    "votes": {
    "self": "http://localhost:8090/jira/rest/api/2/issue/HSP-1/votes",
    "votes": 0,
    "hasVoted": false
    },
    "assignee": {
    "self": "http://localhost:8090/jira/rest/api/2/user?username=admin",
    "name": "admin",
    "emailAddress": "admin@example.com",
    "avatarUrls": {
    "16x16": "http://localhost:8090/jira/secure/useravatar?size=small&avatarId=10062",
    "48x48": "http://localhost:8090/jira/secure/useravatar?avatarId=10062"
    },
    "displayName": "Administrator",
    "active": true
    },
    "resolution": {
    "self": "http://localhost:8090/jira/rest/api/2/resolution/1",
    "id": "1",
    "description": "A fix for this issue is checked into the tree and tested.",
    "name": "Fixed"
    },
    "fixVersions": [(0)],
    "security": null,
    "resolutiondate": "2011-09-26T15:44:39.220+1000",
    "sub-tasks": [(0)],
    "reporter": {
    "self": "http://localhost:8090/jira/rest/api/2/user?username=admin",
    "name": "admin",
    "emailAddress": "admin@example.com",
    "avatarUrls": {
    "16x16": "http://localhost:8090/jira/secure/useravatar?size=small&avatarId=10062",
    "48x48": "http://localhost:8090/jira/secure/useravatar?avatarId=10062"
    },
    "displayName": "Administrator",
    "active": true
    },
    "project": {
    "self": "http://localhost:8090/jira/rest/api/2/project/HSP",
    "id": "10000",
    "key": "HSP",
    "name": "homosapien",
    "roles": {},
    "avatarUrls": {
    "16x16": "http://localhost:8090/jira/secure/projectavatar?size=small&pid=10000&avatarId=10011",
    "48x48": "http://localhost:8090/jira/secure/projectavatar?pid=10000&avatarId=10011"
    }
    },
    "versions": [(0)],
    "environment": null,
    "created": "2011-09-26T15:44:23.888+1000",
    "updated": "2011-09-26T15:44:39.295+1000",
    "priority": {
    "self": "http://localhost:8090/jira/rest/api/2/priority/5",
    "iconUrl": "http://localhost:8090/jira/images/icons/priority_trivial.gif",
    "name": "Trivial",
    "id": "5"
    },
    "description": null,
    "duedate": "2011-09-25",
    "components": [(1)
    {
    "self": "http://localhost:8090/jira/rest/api/2/component/10000",
    "id": "10000",
    "name": "New Component 1"
    }
    ],
    "comment": {
    "startAt": 0,
    "maxResults": 0,
    "total": 0,
    "comments": [(0)]
    },
    "watches": {
    "self": "http://localhost:8090/jira/rest/api/2/issue/HSP-1/watchers",
    "watchCount": 0,
    "isWatching": false
    }
    },
    "transitions": "http://localhost:8090/jira/rest/api/2/issue/HSP-1/transitions",
    "editmeta": "TODO",
    "changelog": "TODO"
    }</code></pre>
    
Source code: https://developer.atlassian.com/jiradev/jira-apis/jira-rest-apis/jira-rest-api-tutorials/jira-rest-api-example-oauth-authentication
