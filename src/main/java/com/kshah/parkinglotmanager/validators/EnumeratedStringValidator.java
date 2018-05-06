package com.kshah.parkinglotmanager.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EnumeratedStringValidator implements ConstraintValidator<EnumeratedString, String> {

    private Set<String> enumNames;


    @Override
    public void initialize(EnumeratedString enumeratedString) {
        Class<? extends Enum<?>> selectedEnum = enumeratedString.enumClass();
        Enum<?>[] enums = selectedEnum.getEnumConstants();
        String[] names = new String[enums.length];
        for(int i = 0; i < enums.length; i++) {
            names[i] = enums[i].name();
        }
        enumNames = new HashSet<String>(Arrays.asList(names));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null) {
            return true;
        } else {
            return enumNames.contains(value);
        }
    }
}
