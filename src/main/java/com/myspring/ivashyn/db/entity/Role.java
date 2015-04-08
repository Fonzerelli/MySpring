package com.myspring.ivashyn.db.entity;

import javax.persistence.*;

/**
 * Created by: Dima Ivashyn
 * Date: 13.03.15
 * Time: 12:48
 */
@Entity
@Table(name = "ROLE")
public class Role extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
