package com.kshah.parkinglotmanager.services;

import com.kshah.parkinglotmanager.model.api.Capacity;
import com.kshah.parkinglotmanager.model.database.DBCapacity;
import com.kshah.parkinglotmanager.repositories.CapacityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CapacityServiceImpl implements CapacityService {

    private CapacityRepository repository;

    @Autowired
    public CapacityServiceImpl(CapacityRepository capacityRepository) {
        this.repository = capacityRepository;
    }

    @Override
    public Capacity getCapacity() {
        DBCapacity dbCapacity = null;
        dbCapacity = repository.findOne(DBCapacity.ID);

        Capacity capacity = new Capacity();
        capacity.setCurrent(dbCapacity.getCurrentCapacity());
        capacity.setMax(dbCapacity.getMaxCapacity());
        capacity.setLastModified(dbCapacity.getLastModified().toInstant());
        return capacity;
    }

}
