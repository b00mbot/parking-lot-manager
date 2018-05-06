package com.kshah.parkinglotmanager.configuration.jpa;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JpaAuditorAwareImplTest {

    private JpaAuditorAwareImpl jpaAuditorAware;

    @Before
    public void setUp() throws Exception {
        jpaAuditorAware = new JpaAuditorAwareImpl();
    }

    @Test
    public void testGetCurrentAuditor() {
        assertEquals("SYSTEM", jpaAuditorAware.getCurrentAuditor());
    }
}