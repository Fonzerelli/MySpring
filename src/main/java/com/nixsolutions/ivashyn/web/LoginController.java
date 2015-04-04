package com.nixsolutions.ivashyn.web;

import com.nixsolutions.ivashyn.db.dao.UserDao;
import com.nixsolutions.ivashyn.db.entity.User;
import com.nixsolutions.ivashyn.db.exception.DaoException;
import com.nixsolutions.ivashyn.util.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.*;

/**
 * Created by: Dima Ivashyn
 * Date: 18.03.15
 * Time: 10:56
 */
@Controller
public class LoginController {

    private static final String ADMIN_NAME = "Admin";
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginUtil loginUtil;

    @RequestMapping(value = "/")
    public String getLogin() {
        return "redirect:/signIn";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String getLoginPage(@RequestParam(value = "error", required =
            false) boolean error, HttpSession session,
                               ModelMap model) {

        boolean captchaError = false;
        if (session.getAttribute("captcha_error") != null) {
            captchaError = (Boolean) session.getAttribute("captcha_error");
        }
        if (error) {
            session.setAttribute("passCaptureFilter", true);
            loginUtil.incrementLoginCounter();
            if (captchaError) {
                model.put("captcha_error", "Captcha is not valid");
                model.put("error", "");
            } else {
                model.put("error", "You have entered an invalid username or password!");
                model.put("captcha_error", "");
            }
        } else {
            model.put("error", "");
            model.put("captcha_error", "");
            loginUtil.resetLoginCounter();
        }
        session.setAttribute("captchaIsNecessary", loginUtil.captchaIsNecessary());

        return "SignInPage";
    }

    @RequestMapping(value = "switch")
    public String switchAccess(HttpSession session) {
        String login = ((org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal()).getUsername();
        User user = null;
        try {
            user = userDao.findByLogin(login);
        } catch (DaoException e) {
            LOGGER.error("Can't find user by login", e);
            throw new RuntimeException(e);
        }
        session.setAttribute("currentSessionUser", user);
        if (ADMIN_NAME.equals(user.getRole().getName())) {
            return "forward:/editAllUsers";
        } else {
            return "forward:/getUserHome";
        }
    }

    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String getDeniedPage() {
        return "DeniedPage";
    }

    @RequestMapping(value = "/getUserHome", method = RequestMethod.GET)
    public String getUserHome() {
        return "UserHome";
    }
}

