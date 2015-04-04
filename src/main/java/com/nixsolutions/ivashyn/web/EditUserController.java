package com.nixsolutions.ivashyn.web;

import com.nixsolutions.ivashyn.db.dao.RoleDao;
import com.nixsolutions.ivashyn.db.dao.UserDao;
import com.nixsolutions.ivashyn.db.entity.User;
import com.nixsolutions.ivashyn.db.exception.DaoException;
import com.nixsolutions.ivashyn.util.HashUtil;
import com.nixsolutions.ivashyn.util.UserHelper;
import com.nixsolutions.ivashyn.web.form.UserForm;
import com.nixsolutions.ivashyn.web.form.UserFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

/**
 * Created by: Dima Ivashyn
 * Date: 19.03.15
 * Time: 13:46
 */
@Controller
public class EditUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditUserController.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserFormValidator userFormValidator;

    @Autowired
    private RoleDao roleDao;

    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
        protected ModelAndView editUser(@RequestParam(value = "id") String id) {
        User user = null;
        try {
            user = userDao.findById(Long.parseLong(id));
        } catch (DaoException e) {
            LOGGER.error("Can't find user by id", e);
            throw new RuntimeException(e);
        }

        ModelAndView modelAndView = new ModelAndView("EditUserPage");
        if (user != null) {
            modelAndView.addObject("editedUser", new UserForm(user));
            modelAndView.addObject("roles", userHelper.getAllRoles());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public ModelAndView saveUser(@ModelAttribute("editedUser") UserForm
                                             userForm, BindingResult result) {
        userFormValidator.validate(userForm, result);
        ModelAndView modelAndView = new ModelAndView("EditUserPage");
        if (result.hasErrors()) {
            modelAndView.addObject("editedUser", userForm);
            modelAndView.addObject("roles", userHelper.getAllRoles());
            return modelAndView;
        }
        User user = null;
        try {
            user = userDao.findById(userForm.getId());
        } catch (DaoException e) {
            LOGGER.error("Can't find user by id", e);
            return modelAndView;
        }
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
            userDao.update(user);
        } catch (DaoException e) {
            LOGGER.error("Can't update user", e);
            return modelAndView;
        }
        return new ModelAndView("forward:/editAllUsers");
    }
}
