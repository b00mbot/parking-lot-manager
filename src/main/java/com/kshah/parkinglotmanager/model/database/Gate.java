package com.kshah.parkinglotmanager.model.database;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationStatus status;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gate")
    private Set<Ticket> tickets;

}
