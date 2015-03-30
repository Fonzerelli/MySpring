package com.nixsolutions.ivashyn.db.dao;

import com.nixsolutions.ivashyn.db.entity.BaseEntity;
import com.nixsolutions.ivashyn.db.exception.DaoException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro Ivashyn
 * Date: 3/29/15
 * Time: 9:53 PM
 */
public interface GenericDao<T extends BaseEntity> {

    public void create(T user) throws DaoException;
    public void update(T user) throws DaoException;
    public void remove(T user) throws DaoException;
    public T findById(long l) throws DaoException;
    public List<T> findAll() throws DaoException;
}
