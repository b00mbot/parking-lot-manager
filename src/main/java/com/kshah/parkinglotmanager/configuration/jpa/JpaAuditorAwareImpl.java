package com.kshah.parkinglotmanager.configuration.jpa;

import org.springframework.data.domain.AuditorAware;


public class JpaAuditorAwareImpl implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        return "SYSTEM";
    }
}
