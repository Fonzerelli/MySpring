package com.myspring.ivashyn.web;

import com.myspring.ivashyn.db.dao.RoleDao;
import com.myspring.ivashyn.db.dao.UserDao;
import com.myspring.ivashyn.db.entity.User;
import com.myspring.ivashyn.db.exception.DaoException;
import com.myspring.ivashyn.util.HashUtil;
import com.myspring.ivashyn.util.UserHelper;
import com.myspring.ivashyn.web.form.UserForm;
import com.myspring.ivashyn.web.form.UserFormValidator;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

/**
 * Created by Dmytro Ivashyn
 * Date: 3/30/15
 */
@Controller
public class SignUpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserFormValidator userFormValidator;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailSender mailSender;

    private String privateKey;

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView getRegisterUserPage() {
        ModelAndView modelAndView = new ModelAndView("SignUpPage");
        modelAndView.addObject("newUser", new UserForm());
        modelAndView.addObject("roles", userHelper.getAllRoles());
        return modelAndView;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ModelAndView registerUser(
            @ModelAttribute("newUser") UserForm userForm,
            HttpSession session, HttpServletRequest req,
            BindingResult result) {
        userFormValidator.validate(userForm, result);
        ModelAndView modelAndView = new ModelAndView("SignUpPage");
        if (result.hasErrors()) {
            modelAndView.addObject("newUser", userForm);
            modelAndView.addObject("roles", userHelper.getAllRoles());
            return modelAndView;
        }
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey(privateKey);
        try {
            String remoteAddr = req.getRemoteAddr();
            String challenge = req.getParameter("recaptcha_challenge_field");
            String response = req.getParameter("recaptcha_response_field");

            ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(
                    remoteAddr,
                    challenge,
                    response);


            if (!reCaptchaResponse.isValid()) {
                modelAndView.addObject("newUser", userForm);
                modelAndView.addObject("roles", userHelper.getAllRoles());
                modelAndView.addObject("captchaError", "Captcha is not valid");
                return modelAndView;
            }
        } catch (Exception e) {
            LOGGER.error("Can't validate captcha", e);
            modelAndView.addObject("newUser", userForm);
            modelAndView.addObject("roles", userHelper.getAllRoles());
            modelAndView.addObject("captchaError", "Error validating captcha");
            return modelAndView;
        }
        final User user = new User();
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
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(user.getEmail());
                    message.setSubject("Signed up successfully");
                    message.setText("Dear " + user.getLogin() + ", thank you for registration!");
                    mailSender.send(message);
                }
            });

            thread.start();
        } catch (Exception e) {
            LOGGER.error("Can't send email to " + user.getEmail());
        }

        return new ModelAndView("redirect:/signIn");
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
