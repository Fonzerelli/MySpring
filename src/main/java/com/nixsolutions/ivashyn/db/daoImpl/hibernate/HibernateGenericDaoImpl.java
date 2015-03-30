package com.nixsolutions.ivashyn.db.daoImpl.hibernate;

import com.nixsolutions.ivashyn.db.dao.GenericDao;
import com.nixsolutions.ivashyn.db.entity.BaseEntity;
import com.nixsolutions.ivashyn.db.exception.DaoException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by: Dima Ivashyn
 * Date: 20.03.15
 * Time: 15:25
 */
@Repository
@Transactional
public class HibernateGenericDaoImpl<T extends BaseEntity> implements GenericDao<T> {

    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(T entity) throws DaoException {
        try {
            getSession().save(entity);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(T entity) throws DaoException {
        try {
            getSession().update(entity);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void remove(T entity) throws DaoException {
        try {
            getSession().delete(entity);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public T findById(long id) throws DaoException {
        try {
            return (T) getSession().get(getEntityClass(), id);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<T> findAll() throws DaoException {
        Query query = getSession().createQuery("SELECT e FROM " + getEntityClass().getCanonicalName() + " e ");
        return query.list();
    }

    protected Class<T> getEntityClass() {
        ParameterizedType ptype = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) ptype.getActualTypeArguments()[0];
    }
}
