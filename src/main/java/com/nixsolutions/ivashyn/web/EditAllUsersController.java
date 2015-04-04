package com.nixsolutions.ivashyn.web;

import com.nixsolutions.ivashyn.util.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by: Dima Ivashyn
 * Date: 23.03.15
 * Time: 12:10
 */
@Controller
public class EditAllUsersController {

    @Autowired
    private UserHelper userHelper;

    @RequestMapping(value = "/editAllUsers")
    protected String service(ModelMap modelMap) throws ServletException,
            IOException {

        modelMap.addAttribute("usersList", userHelper.getAllUsers());
        return "EditUsersPage";
    }
}
