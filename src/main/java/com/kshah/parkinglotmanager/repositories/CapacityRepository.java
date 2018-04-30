package com.kshah.parkinglotmanager.repositories;

import com.kshah.parkinglotmanager.model.database.DBCapacity;
import org.springframework.data.repository.CrudRepository;

public interface CapacityRepository extends CrudRepository<DBCapacity, Integer> {
}
