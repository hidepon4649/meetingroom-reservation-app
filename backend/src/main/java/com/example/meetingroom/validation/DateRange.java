package com.example.meetingroom.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface DateRange {

    String message() default "開始日時は終了日時より前でなければなりません";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String startField(); // 開始日時のフィールド名

    String endField(); // 終了日時のフィールド名
}
