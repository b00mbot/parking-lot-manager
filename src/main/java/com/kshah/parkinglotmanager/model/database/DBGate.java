package com.kshah.parkinglotmanager.model.database;

import com.kshah.parkinglotmanager.model.common.OperationStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "GATE")
@Getter
@Setter
@ToString
public class DBGate extends DBBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OperationStatus status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gate")
    private Set<DBTicket> tickets;

}
