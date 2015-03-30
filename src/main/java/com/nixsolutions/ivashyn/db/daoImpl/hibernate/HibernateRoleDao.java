package com.nixsolutions.ivashyn.db.daoImpl.hibernate;

import com.nixsolutions.ivashyn.db.dao.RoleDao;
import com.nixsolutions.ivashyn.db.entity.Role;
import com.nixsolutions.ivashyn.db.exception.DaoException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by: Dima Ivashyn
 * Date: 20.03.15
 * Time: 15:27
 */
@Repository
@Transactional
public class HibernateRoleDao extends HibernateGenericDaoImpl<Role> implements RoleDao {

    private static final Log LOGGER = LogFactory.getLog(HibernateRoleDao.class);

    public HibernateRoleDao() {
    }

    @Override
    public Role findByName(String name) throws DaoException {
        try {
            Query query = getSession().createQuery("SELECT e FROM Role as e " +
                    "where e.name = :name");
            query.setParameter("name", name);
            return (Role) query.uniqueResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<String> findAllRoleNames() throws DaoException {
        try {
            Query query = getSession().createQuery("SELECT e.name FROM Role e");
            return (List<String>) query.list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
