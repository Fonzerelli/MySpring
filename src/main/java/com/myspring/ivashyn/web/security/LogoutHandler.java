package com.myspring.ivashyn.web.security;

import com.myspring.ivashyn.mongo.LoginAuditDocument;
import com.myspring.ivashyn.mongo.dao.LoginAuditDocumentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Dmytro Ivashyn
 * Date: 4/6/15
 */
public class LogoutHandler implements LogoutSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutHandler.class);

    @Autowired
    protected LoginAuditDocumentDao loginAuditDocumentDao;

    private String successURL;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        try {
            String sessionId = httpServletRequest.getSession().getId();

            LoginAuditDocument currentSessionInfo = loginAuditDocumentDao.findBySessionId(sessionId);
            currentSessionInfo.setLogoutTime(new Date());

            loginAuditDocumentDao.save(currentSessionInfo);

        } catch (Exception e) {
            LOGGER.error("Can't find and update login audit entity on logout", e);
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
