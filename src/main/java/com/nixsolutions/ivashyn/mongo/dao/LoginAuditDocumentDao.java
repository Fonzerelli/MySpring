package com.nixsolutions.ivashyn.mongo.dao;

import com.google.code.morphia.dao.DAO;
import com.nixsolutions.ivashyn.mongo.LoginAuditDocument;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jnixable
 * Date: 11/18/13
 * Time: 12:44 PM
 */
public interface LoginAuditDocumentDao extends DAO<LoginAuditDocument, ObjectId> {

    LoginAuditDocument findById(String id);

    LoginAuditDocument findLast();

    List<LoginAuditDocument> findAllForLastDays(int days);

    public List<LoginAuditDocument> findAllForDate(Date date);

    public List<LoginAuditDocument> findAll();

    public LoginAuditDocument findBySessionId(String sessionId);
}
