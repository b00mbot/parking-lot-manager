package com.kshah.parkinglotmanager.model.database;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "PARKING_CAPACITY")
@Getter
@ToString
public class DBCapacity {

    public final static Integer ID = 1;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "currentCapacity", nullable = false)
    private Integer currentCapacity;

    @Column(name = "maxCapacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "lastModified")
    private Date lastModified;

    public DBCapacity() {
    }
}
