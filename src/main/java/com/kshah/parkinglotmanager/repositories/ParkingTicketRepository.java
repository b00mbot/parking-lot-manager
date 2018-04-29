package com.kshah.parkinglotmanager.repositories;

import com.kshah.parkinglotmanager.model.database.DBTicket;
import org.springframework.data.repository.CrudRepository;

public interface ParkingTicketRepository extends CrudRepository<DBTicket, Long> {

}
