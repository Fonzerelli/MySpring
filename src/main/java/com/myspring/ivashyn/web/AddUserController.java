package com.myspring.ivashyn.web;

import com.myspring.ivashyn.db.dao.RoleDao;
import com.myspring.ivashyn.db.dao.UserDao;
import com.myspring.ivashyn.db.entity.User;
import com.myspring.ivashyn.db.exception.DaoException;
import com.myspring.ivashyn.util.HashUtil;
import com.myspring.ivashyn.util.UserHelper;
import com.myspring.ivashyn.web.form.UserForm;
import com.myspring.ivashyn.web.form.UserFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

/**
 * Created by: Dima Ivashyn
 * Date: 19.03.15
 * Time: 17:19
 */
@Controller
public class AddUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddUserController.class);

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserFormValidator userFormValidator;

    @RequestMapping(value = "/getAddUserPage", method = RequestMethod.GET)
    public ModelAndView addUser() {
        ModelAndView modelAndView= new ModelAndView("AddUserPage");
        modelAndView.addObject("newUser", new UserForm());
        modelAndView.addObject("roles", userHelper.getAllRoles());
        return modelAndView;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ModelAndView saveUser(@ModelAttribute("newUser") UserForm
                                         userForm, BindingResult result) {
        userFormValidator.validate(userForm, result);
        ModelAndView modelAndView = new ModelAndView("AddUserPage");
        if (result.hasErrors()) {
            modelAndView.addObject("newUser", userForm);
            modelAndView.addObject("roles", userHelper.getAllRoles());
            return modelAndView;
        }
        User user = new User();
        user.setLogin(userForm.getLogin());

        user.setPassword(HashUtil.getEncryptedText(userForm.getPassword()));
        user.setEmail(userForm.getEmail());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        try {
            user.setRole(roleDao.findById(userForm.getRoleId()));
        } catch (DaoException e) {
            LOGGER.error("Can't find role by id", e);
        }
        try {
            user.setBirthday(UserHelper.DATE_FORMATTER.parse(userForm
                    .getBirthday()));
        } catch (ParseException e) {
            LOGGER.error("Can't parse date", e);
            return modelAndView;
        }
        try {
            userDao.create(user);
        } catch (DaoException e) {
            LOGGER.error("Can't update user", e);
            return modelAndView;
        }
        return new ModelAndView("forward:/editAllUsers");
    }
}
