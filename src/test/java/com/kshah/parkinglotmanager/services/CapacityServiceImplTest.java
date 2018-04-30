package com.kshah.parkinglotmanager.services;

import com.kshah.parkinglotmanager.model.api.Capacity;
import com.kshah.parkinglotmanager.model.database.DBCapacity;
import com.kshah.parkinglotmanager.repositories.CapacityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class CapacityServiceImplTest {

    @Mock
    private CapacityRepository capacityRepository;

    @Mock
    private DBCapacity dbCapacity;

    private CapacityServiceImpl capacityService;


    @Before
    public void setUp() throws Exception {
        capacityService = new CapacityServiceImpl(capacityRepository);
    }

    @Test
    public void testGetCapacity() throws Exception {

        Integer currentCapacity = 0;
        Integer maxCapacity = 10;
        Date date = new Date();

        Mockito.when(dbCapacity.getId()).thenReturn(1);
        Mockito.when(dbCapacity.getCurrentCapacity()).thenReturn(currentCapacity);
        Mockito.when(dbCapacity.getMaxCapacity()).thenReturn(maxCapacity);
        Mockito.when(dbCapacity.getLastModified()).thenReturn(date);
        Mockito.when(capacityRepository.findOne(Mockito.anyInt())).thenReturn(dbCapacity);

        Capacity capacity = capacityService.getCapacity();

        assertNotNull(capacity);
        assertEquals(currentCapacity, capacity.getCurrent());
        assertEquals(maxCapacity, capacity.getMax());
        assertEquals(date.toInstant(), capacity.getLastModified());
    }
}