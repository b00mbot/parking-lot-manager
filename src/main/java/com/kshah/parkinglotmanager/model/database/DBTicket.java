package com.kshah.parkinglotmanager.model.database;

import com.kshah.parkinglotmanager.model.common.TicketStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "PARKING_TICKET")
@Getter
@Setter
@ToString
public class DBTicket extends DBBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name = "gateId", referencedColumnName = "id", nullable = false)
    private DBGate gate;

}
