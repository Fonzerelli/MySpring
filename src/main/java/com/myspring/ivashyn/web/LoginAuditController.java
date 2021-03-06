package com.myspring.ivashyn.web;

import com.myspring.ivashyn.mongo.LoginAuditDocument;
import com.myspring.ivashyn.mongo.dao.LoginAuditDocumentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by Dmytro Ivashyn
 * Date: 4/7/15
 */
@Controller
public class LoginAuditController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuditController.class);

    @Autowired
    private LoginAuditDocumentDao loginAuditDocumentDao;

    @RequestMapping(value = "/getAllLoginAudit")
    protected String getAllLoginAudit(ModelMap modelMap) throws ServletException,
            IOException {

        modelMap.addAttribute("loginAuditList", loginAuditDocumentDao.findAllSorted());
        return "LoginAuditPage";
    }

    @RequestMapping(value = "/deleteLoginAudit")
    protected String deleteUser(@RequestParam(value = "id") String id) {

        try {
            LoginAuditDocument loginAuditDocument = loginAuditDocumentDao.findById(id);
            if (loginAuditDocument != null) {
                loginAuditDocumentDao.delete(loginAuditDocument);
            }
        } catch (Exception e) {
            LOGGER.error("Can't remove login audit", e);
        }
        return "forward:/getAllLoginAudit";
    }
}
