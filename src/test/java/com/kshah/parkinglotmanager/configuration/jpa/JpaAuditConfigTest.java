package com.kshah.parkinglotmanager.configuration.jpa;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class JpaAuditConfigTest {

    private JpaAuditConfig jpaAuditConfig;

    @Before
    public void setUp() throws Exception {
        jpaAuditConfig = new JpaAuditConfig();
    }


    @Test
    public void testAuditorProvider_Instance() {
        assertTrue(jpaAuditConfig.auditorProvider() instanceof JpaAuditorAwareImpl);
    }

}