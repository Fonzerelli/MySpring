package com.myspring.ivashyn.web;

import com.myspring.ivashyn.db.dao.RoleDao;
import com.myspring.ivashyn.db.dao.UserDao;
import com.myspring.ivashyn.db.entity.Role;
import com.myspring.ivashyn.db.entity.User;
import com.myspring.ivashyn.db.exception.DaoException;
import com.myspring.ivashyn.util.restPojo.RoleList;
import com.myspring.ivashyn.util.restPojo.UserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ivashyn on 4/14/15.
 */

@Controller
@RequestMapping("/rest")
public class RestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/getAllRoles",
            method = RequestMethod.GET,
            headers="Accept=application/xml, application/json")
    public @ResponseBody
    RoleList getAllRoles() {
        LOGGER.debug("Provider has received request to get all roles");

        RoleList result = new RoleList();
        try {
            result.setData(roleDao.findAll());
        } catch (DaoException e) {
            LOGGER.error("Can't get all roles", e);
        }

        return result;
    }

    @RequestMapping(value = "/getRoleById/{id}",
            method = RequestMethod.GET,
            headers="Accept=application/xml, application/json")
    public @ResponseBody
    Role getRoleById(@PathVariable("id") Long id) {

        Role role = null;
        try {
            role = roleDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error("Can't get role by id", e);
        }

        return role;
    }

    @RequestMapping(value = "/getUserById/{id}",
            method = RequestMethod.GET,
            headers="Accept=application/xml, application/json")
    public @ResponseBody
    User getUserById(@PathVariable("id") Long id) {

        User user = null;
        try {
            user = userDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error("Can't get user by id", e);
        }

        return user;
    }

    @RequestMapping(value = "/getAllUsers",
            method = RequestMethod.GET,
            headers="Accept=application/xml, application/json")
    public @ResponseBody
    UserList getAllUsers() {
        LOGGER.debug("Provider has received request to get all users");

        UserList result = new UserList();
        try {
            result.setData(userDao.findAll());
        } catch (DaoException e) {
            LOGGER.error("Can't get all users", e);
        }

        return result;
    }
}
