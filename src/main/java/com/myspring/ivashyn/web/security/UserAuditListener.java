package com.myspring.ivashyn.web.security;

import com.myspring.ivashyn.mongo.LoginAuditDocument;
import com.myspring.ivashyn.mongo.dao.LoginAuditDocumentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

/**
 * Created by Dmytro Ivashyn
 * Date: 4/6/15
 */
public class UserAuditListener implements HttpSessionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuditListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        try {

            HttpSession session = httpSessionEvent.getSession();
            ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
            LoginAuditDocumentDao loginAuditDao = ctx.getBean(LoginAuditDocumentDao.class);

            String sessionId = httpSessionEvent.getSession().getId();

            LoginAuditDocument currentSessionInfo = loginAuditDao.findBySessionId(sessionId);

            if (currentSessionInfo != null) {
                currentSessionInfo.setLogoutTime(new Date());
                loginAuditDao.save(currentSessionInfo);
            }

        } catch (Exception e) {
            LOGGER.error("Can't add information about session expiration", e);
        }
    }
}
