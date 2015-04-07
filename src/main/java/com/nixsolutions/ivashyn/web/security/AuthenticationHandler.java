package com.nixsolutions.ivashyn.web.security;

import com.nixsolutions.ivashyn.mongo.LoginAuditDocument;
import com.nixsolutions.ivashyn.mongo.dao.LoginAuditDocumentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


public class AuthenticationHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationHandler.class);

    @Autowired
    protected LoginAuditDocumentDao loginAuditDocumentDao;

    private String successURL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        try {
            String sessionId = httpServletRequest.getSession().getId();
//            String browserInfo = httpServletRequest.getSession().getAttribute("browserInfo").toString();
            String browserInfo = httpServletRequest.getParameter("browserVersion");

            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            User user = (User) authentication.getPrincipal();

            String remoteIp = details.getRemoteAddress();

            LoginAuditDocument currentSessionInfo = new LoginAuditDocument();
            currentSessionInfo.setLoginTime(new Date());
            currentSessionInfo.setSessionId(sessionId);
            currentSessionInfo.setUsername(user.getUsername());
            currentSessionInfo.setRemoteIP(remoteIp);
            currentSessionInfo.setBrowserInfo(browserInfo);

            loginAuditDocumentDao.save(currentSessionInfo);

        } catch (Exception e) {
            LOGGER.error("Can't add information about session expiration", e);
        }

        httpServletResponse.sendRedirect(successURL);
    }

    public String getSuccessURL() {
        return successURL;
    }

    public void setSuccessURL(String successURL) {
        this.successURL = successURL;
    }
}
