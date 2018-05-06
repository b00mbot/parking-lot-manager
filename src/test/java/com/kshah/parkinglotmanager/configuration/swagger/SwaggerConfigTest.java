package com.kshah.parkinglotmanager.configuration.swagger;

import com.google.common.base.Predicate;
import org.junit.Before;
import org.junit.Test;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SwaggerConfigTest {

    private SwaggerConfig swaggerConfig;

    @Before
    public void setUp() throws Exception {
        swaggerConfig = new SwaggerConfig();
    }


    @Test
    public void testApiDocket() {
        Docket docket = swaggerConfig.api();
        assertNotNull(docket);
        assertEquals(DocumentationType.SWAGGER_2, docket.getDocumentationType());
    }


    @Test
    public void testApiInfo() {
        ApiInfo apiInfo = swaggerConfig.apiInfo();
        assertNotNull(apiInfo);
        assertNotNull(apiInfo.getTitle());
        assertNotEquals("", apiInfo.getTitle());
        assertNotNull(apiInfo.getDescription());
        assertNotEquals("", apiInfo.getDescription());
        assertNotNull(apiInfo.getVersion());
        assertNotEquals("", apiInfo.getVersion());
    }

    @Test
    public void testPaths() {
        Predicate<String> predicate = swaggerConfig.paths();
        assertTrue(predicate.apply("/api/v1/"));
        assertTrue(predicate.apply("/api/v1/gates"));
        assertFalse(predicate.apply("/api/v1"));
        assertFalse(predicate.apply("/api/v2/gates"));
    }
}