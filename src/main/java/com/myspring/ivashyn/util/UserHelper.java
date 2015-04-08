package com.myspring.ivashyn.util;

import com.myspring.ivashyn.db.dao.RoleDao;
import com.myspring.ivashyn.db.dao.UserDao;
import com.myspring.ivashyn.db.entity.Role;
import com.myspring.ivashyn.db.entity.User;
import com.myspring.ivashyn.db.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by: Dima Ivashyn
 * Date: 19.03.15
 * Time: 18:45
 */
@Service
public class UserHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHelper.class);

    public static final SimpleDateFormat DATE_FORMATTER = new
            SimpleDateFormat("YYYY-MM-dd");
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    public HttpServletRequest saveDataOnError(HttpServletRequest req) {
        req.setAttribute("login", req.getParameter("login"));
        req.setAttribute("password", req.getParameter("password"));
        req.setAttribute("email", req.getParameter("email"));
        req.setAttribute("firstName", req.getParameter("firstName"));
        req.setAttribute("lastName", req.getParameter("lastName"));
        req.setAttribute("birthday", req.getParameter("birthday"));
        req.setAttribute("role", req.getParameter("roleSelect"));
        req.setAttribute("roleNames", getAllRoles());
        return req;
    }

    public List<Role> getAllRoles() {
        List<Role> roles = null;
        try {
            roles = roleDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Can't find all roles", e);
            throw new RuntimeException(e);
        }
        return roles;
    }

    public List<User> getAllUsers() {
        List<User> userList = null;
        try {
            userList = userDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Can't find all users", e);
            throw new RuntimeException(e);
        }
        return userList;
    }

    public boolean birthdayIsValid(String birthday) {
        return getAge(java.sql.Date.valueOf(birthday)) > 0;
    }

    public static int getAge(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < calendar.get(Calendar.MONTH)) {
            age--;
        } else if (today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) < calendar.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

    public boolean loginIsUnique(String userName) {
        try {
            return userDao.findByLogin(userName) == null;
        } catch (DaoException e) {
            LOGGER.error("Can't validate login", e);
            throw new RuntimeException(e);
        }
    }

    public boolean emailIsUnique(String email) {
        try {
            return userDao.findByEmail(email) == null;
        } catch (DaoException e) {
            LOGGER.error("Can't validate email", e);
            throw new RuntimeException(e);
        }
    }

    public boolean emailIsValid(String email) {
        return email.matches("[A-Za-z0-9._%+-][A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
    }

    public boolean emailIsUniqueOnEdit(String email, Long id) {
        User user = null;
        try {
            user = userDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error("Can't find user by id", e);
        }
        if (!email.equals(user.getEmail())) {
            try {
                return userDao.findByEmail(email) == null;
            } catch (DaoException e) {
                LOGGER.error("Can't validate email", e);
                throw new RuntimeException(e);
            }
        } else {
            return true;
        }
    }
}
