package com.nixsolutions.ivashyn.mongo.dao.impl;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.nixsolutions.ivashyn.mongo.LoginAuditDocument;
import com.nixsolutions.ivashyn.mongo.dao.LoginAuditDocumentDao;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jnixable
 * Date: 11/18/13
 * Time: 12:45 PM
 */
@Repository
public class LoginAuditDocumentDaoImpl extends BasicDAO<LoginAuditDocument, ObjectId> implements LoginAuditDocumentDao {


    @Autowired
    public LoginAuditDocumentDaoImpl(Datastore datastore) {
        super(datastore);
    }

    @Override
    public LoginAuditDocument findById(String id) {
        return get(new ObjectId(id));
    }

    @Override
    public LoginAuditDocument findLast() {
        return createQuery().limit(1).order("-created").get();
    }

    @Override
    public List<LoginAuditDocument> findAllForLastDays(int days) {

        Date now = new Date();
        Date start = getStartOfDayPeriod(days);
        Query<LoginAuditDocument> query = createQuery().filter("created >=", start).filter("created <=", now);
        return query.asList();
    }

    @Override
    public List<LoginAuditDocument> findAllForDate(Date date) {

        Date start = getDayStart(date);
        Date end = getDayEnd(date);

        Query<LoginAuditDocument> query = createQuery().filter("created >=", start).filter("created <=", end);
        return query.asList();
    }

    @Override
    public List<LoginAuditDocument> findAll() {
        return find().asList();
    }

    @Override
    public LoginAuditDocument findBySessionId(String sessionId) {
        return ds.find(LoginAuditDocument.class).field("sessionId").equal(sessionId).get();
    }

    private Date getStartOfDayPeriod(int days) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1 * days);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }


    protected Date getDayStart(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    protected Date getDayEnd(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }

}
