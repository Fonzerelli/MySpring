package com.myspring.ivashyn.util.restPojo;

import com.myspring.ivashyn.db.entity.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by ickis on 4/14/15.
 */

@XmlRootElement(name="users")
public class UserList {

    private List<User> data;

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
