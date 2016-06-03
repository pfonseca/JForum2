package net.jforum.sso;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import net.jforum.ControllerUtils;
import net.jforum.JForumExecutionContext;
import net.jforum.context.RequestContext;
import net.jforum.entities.UserSession;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

public class AmbrosiaUserSSO implements SSO {
	
    static final Logger  logger       = Logger.getLogger(AmbrosiaUserSSO.class.getName());
     
    public String authenticateUser(RequestContext request) {
       
        Cookie myCookie = ControllerUtils.getCookie("JforumSSO"); // my app login cookie
         
        if (myCookie != null) {
            
        	String login = myCookie.getValue();
        	
            return login;
            
        } else {
            return null;
        }
    }
     
    public boolean isSessionValid(UserSession userSession, RequestContext request){
    	
        String      remoteUser  = null;
        Cookie SSOCookie = ControllerUtils.getCookie("JforumSSO");
        if (SSOCookie != null) remoteUser = SSOCookie.getValue(); //jforum username
         
        // user has since logged out
        if(remoteUser == null && userSession.getUserId() != SystemGlobals.getIntValue(ConfigKeys.ANONYMOUS_USER_ID)) {
            return false;
             
            // user has since logged in
        } else if(remoteUser != null && userSession.getUserId() == SystemGlobals.getIntValue(ConfigKeys.ANONYMOUS_USER_ID)) {
            return false;
        }
            // user has changed user
//        } else if(remoteUser != null && !remoteUser.equals(userSession.getUsername())) {
//            return false;
//        }
        
        return true; // incorrect in cvs RemoteUserSSO.java
    }
}