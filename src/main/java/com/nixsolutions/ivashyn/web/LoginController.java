package com.nixsolutions.ivashyn.web;

import com.nixsolutions.ivashyn.db.dao.UserDao;
import com.nixsolutions.ivashyn.db.entity.User;
import com.nixsolutions.ivashyn.db.exception.DaoException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    private static final Log LOGGER = LogFactory.getLog(LoginController.class);

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/")
    public String getLogin() {
        return "redirect:/signIn";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String getLoginPage(@RequestParam(value = "error", required =
            false) boolean error,
                               ModelMap model) {
        if (error == true) {
            model.put("error", "You have entered an invalid username or " +
                    "password!");
        } else {
            model.put("error", "");
        }

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

