package pl.swapmed.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MonthValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Month {


        String message() default "{pl.swapmed.validator.Month.message}";

        Class<? extends Payload>[] payload() default {};

        Class<?>[] groups() default {};
    }

