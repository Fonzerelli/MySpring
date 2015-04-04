package com.nixsolutions.ivashyn.db.daoImpl.hibernate;

import com.nixsolutions.ivashyn.db.dao.UserDao;
import com.nixsolutions.ivashyn.db.entity.User;
import com.nixsolutions.ivashyn.db.exception.DaoException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: Dima Ivashyn
 * Date: 20.03.15
 * Time: 13:56
 */
@Repository
@Transactional
public class HibernateUserDao extends HibernateGenericDaoImpl<User> implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUserDao.class);

    @Override
    public User findByLogin(String login) throws DaoException {
        try {
            Query query = getSession().createQuery("SELECT e FROM User as e " +
                    "where e.login = :login");
            query.setParameter("login", login);
            return (User) query.uniqueResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        try {
            Query query = getSession().createQuery("SELECT e FROM User as e " +
                    "where e.email = :email");
            query.setParameter("email", email);
            return (User) query.uniqueResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
