package ru.prplhd.tennisscoreboard.storage.db.hibernate.entity;

import java.io.Serializable;

public interface BaseEntity<ID extends Serializable> {

    void  setId(ID id);

    ID getId();
}
