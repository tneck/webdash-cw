package com.dr_alan_turing.webdash_cw.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entities                                                                    
 * 
 */
@ApiModel(description = ""
    + "Entities                                                               "
    + "")
@Entity
@Table(name = "dashboard")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dashboard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "options")
    private String options;

    @Column(name = "data_1")
    private String data1;

    @Column(name = "data_2")
    private String data2;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dashboard dashboard = (Dashboard) o;
        if(dashboard.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dashboard.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Dashboard{" +
            "id=" + id +
            ", options='" + options + "'" +
            ", data1='" + data1 + "'" +
            ", data2='" + data2 + "'" +
            '}';
    }
}
