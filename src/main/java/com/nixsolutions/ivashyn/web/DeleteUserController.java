package com.nixsolutions.ivashyn.web;

import com.nixsolutions.ivashyn.db.dao.UserDao;
import com.nixsolutions.ivashyn.db.entity.User;
import com.nixsolutions.ivashyn.db.exception.DaoException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by: Dima Ivashyn
 * Date: 19.03.15
 * Time: 15:26
 */
@Controller
public class DeleteUserController {

    private static final Log LOGGER = LogFactory.getLog(DeleteUserController.class);

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/deleteUser")
    protected String deleteUser(@RequestParam(value = "id") String id) {

        try {
            User user = userDao.findById(Long.parseLong(id));
            if (user != null) {
                userDao.remove(user);
            }
        } catch (DaoException e) {
            LOGGER.error("Can't remove user", e);
            throw new RuntimeException(e);
        }

        return "forward:/editAllUsers";
    }
}
