package com.myspring.ivashyn.web.form;

import com.myspring.ivashyn.util.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by: Dima Ivashyn
 * Date: 26.03.15
 */
@Service
public class UserFormValidator implements Validator {

    @Autowired
    private UserHelper userHelper;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserForm.class.isAssignableFrom(clazz);
    }

    @Override
    public synchronized void validate(Object o, Errors errors) {
        UserForm userForm = (UserForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "login" +
                ".empty", "Login must not be empty.");
        String login = userForm.getLogin();
        Long id = userForm.getId();
        if (login.length() > 254) {
            errors.rejectValue("login", "login.tooLong", "Login must not more" +
                    " than 254 characters.");
        } else if (id == null){
            if (!userHelper.loginIsUnique(userForm.getLogin())) {
                errors.rejectValue("login", "login.isBusy", "Login is busy.");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "password.empty", "Password must not be empty.");
        if (!(userForm.getPassword()).equals(userForm.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "confirmPassword" +
                    ".passwordDontMatch", "Passwords don't match.");
        }
        if (!userHelper.emailIsValid(userForm.getEmail())) {
            errors.rejectValue("email", "email.notValid", "Email address " +
                    "is not valid.");
        } else {
            if (id != null) {
                if (!userHelper.emailIsUniqueOnEdit(userForm.getEmail(),
                        userForm.getId())) {
                    errors.rejectValue("email", "email.isBusy", "Email " +
                            "address is busy.");
                }
            } else if (!userHelper.emailIsUnique(userForm.getEmail())) {
                errors.rejectValue("email", "email.isBusy", "Email " +
                        "address is busy.");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
                "firstName.empty",
                "First name must not be empty.");
        String firstName = userForm.getFirstName();
        if (firstName.length() > 254) {
            errors.rejectValue("firstName", "firstName.tooLong", "First name " +
                    "must not more than 254 characters.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
                "lastName.empty", "Last name must not be empty.");
        String lastName = userForm.getLastName();
        if (lastName.length() > 254) {
            errors.rejectValue("lastName", "lastName.tooLong", "Last name " +
                    "must not more than 254 characters.");
        }

        try {
            if (!userHelper.birthdayIsValid(userForm.getBirthday())) {
                errors.rejectValue("birthday", "birthday.notValid", "Birthday is " +

                        "not valid. Use next format: YYYY-MM-dd");
            }
        } catch (Exception e) {
            errors.rejectValue("birthday", "birthday.notValid", "Birthday is " +
                    "not valid. Use next format: YYYY-MM-dd");
        }
    }
}
