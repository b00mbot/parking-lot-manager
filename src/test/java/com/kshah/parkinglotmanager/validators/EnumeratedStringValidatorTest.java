package com.kshah.parkinglotmanager.validators;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnumeratedStringValidatorTest {

    private EnumeratedStringValidator validator;

    private static enum TestEnum {
        HELLO,
        GOODBYE
    }

    @EnumeratedString(enumClass = TestEnum.class, message = "Test message")
    public String testValue;

    @Before
    public void setUp() throws Exception {
        validator = new EnumeratedStringValidator();
        validator.initialize(getClass().getField("testValue").getAnnotation(EnumeratedString.class));
    }

    @Test
    public void testIsValid_Null() {
        assertTrue(validator.isValid(null, null));
    }


    @Test
    public void testIsValid_ValidString() {
        assertTrue(validator.isValid("HELLO", null));
    }

    @Test
    public void testIsValid_InvalidString() {
        assertFalse(validator.isValid("INVALID", null));
    }


}