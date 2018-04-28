package com.kshah.parkinglotmanager.model.database;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name = "gateId", referencedColumnName = "id", nullable = false)
    private Gate gate;

    @Column(nullable = false)
    private String createUser;

    @CreatedDate
    @Column(nullable = false)
    private Instant createDate;

    @Column(nullable = false)
    private String updateUser;

    @Column(nullable = false)
    private Instant updateDate;

    private String updateReason;



}
