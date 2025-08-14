package com.example.meetingroom.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

    private String startField;
    private String endField;

    @Override
    public void initialize(DateRange constraintAnnotation) {
        this.startField = constraintAnnotation.startField();
        this.endField = constraintAnnotation.endField();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            Field start = obj.getClass().getDeclaredField(startField);
            Field end = obj.getClass().getDeclaredField(endField);
            start.setAccessible(true);
            end.setAccessible(true);

            LocalDateTime startValue = (LocalDateTime) start.get(obj);
            LocalDateTime endValue = (LocalDateTime) end.get(obj);

            if (startValue == null || endValue == null)
                return true; // nullは別途@NotNull

            boolean valid = startValue.isBefore(endValue);

            if (!valid) {
                // エラーを特定フィールドに紐付け
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(startField)
                        .addConstraintViolation();
            }

            return valid;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
