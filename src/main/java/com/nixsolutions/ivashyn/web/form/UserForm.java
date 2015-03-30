package com.nixsolutions.ivashyn.web.form;

import com.nixsolutions.ivashyn.db.entity.User;
import com.nixsolutions.ivashyn.util.UserHelper;

/**
 * Created by: Dima Ivashyn
 * Date: 26.03.15
 */
public class UserForm {

    private Long id;

    private String login;

    private String password;

    private String confirmPassword;

    private String email;

    private String firstName;

    private String lastName;

    private String birthday;

    private long roleId;

    public UserForm() {
    }

    public UserForm(User user) {
        id = user.getId();
        login = user.getLogin();
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        birthday = UserHelper.DATE_FORMATTER.format(user.getBirthday());
        roleId = user.getRole().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
