package com.kshah.parkinglotmanager.repositories;

import com.kshah.parkinglotmanager.model.database.DBGate;
import org.springframework.data.repository.CrudRepository;

public interface GateRepository extends CrudRepository<DBGate, Long> {

}
