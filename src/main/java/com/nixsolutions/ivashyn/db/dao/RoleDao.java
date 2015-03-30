package com.nixsolutions.ivashyn.db.dao;

import com.nixsolutions.ivashyn.db.entity.Role;
import com.nixsolutions.ivashyn.db.exception.DaoException;

import java.util.List;

/**
 * Created by: Dima Ivashyn
 * Date: 13.03.15
 * Time: 12:49
 */
public interface RoleDao extends GenericDao<Role> {

    public Role findByName(String name) throws DaoException;
    public List<String> findAllRoleNames() throws DaoException;
}
