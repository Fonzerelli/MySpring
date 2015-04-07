package com.nixsolutions.ivashyn.mongo;

import org.bson.types.ObjectId;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by Dmytro Ivashyn
 * Date: 4/6/15
 */
public class LoginAuditDocument {


    @com.google.code.morphia.annotations.Id
    private ObjectId id;

    private Date created;

    private String remoteIP;

    private Date loginTime;

    private Date logoutTime;

    private String sessionId;

    private String username;

    private Date lastAccessTime;

    private Boolean expired;

    private String browserInfo;

    private String cityName;

    @Transient
    public String getSessionDuration() {

        Date logoutTime = getLogoutTime();
        String sessionDuration = "";
        long end = (logoutTime == null) ? new Date().getTime() : logoutTime.getTime();
        long start = getLoginTime().getTime();

        double duration = (end - start) / (1000 * 60);
        double hoursAndMinutes = duration / 60;

        long hours = (int) hoursAndMinutes;
        long minutes = Math.round((hoursAndMinutes - hours) * 60);
        long days = 0;

        if (hours > 23) {
            days = (int) (hours / 24);
            hours = Math.round(hours - (days * 24));
            sessionDuration += days + " d ";
        }
        if (hours > 0)
            sessionDuration += hours + " h " + minutes + " min";
        else sessionDuration = (minutes < 1) ? "1 min" : minutes + " min";

        return sessionDuration;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public String getBrowserInfo() {
        return browserInfo;
    }

    public void setBrowserInfo(String browserInfo) {
        this.browserInfo = browserInfo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
