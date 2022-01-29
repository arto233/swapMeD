package pl.swapmed.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.util.Objects;

public class MonthValidator implements ConstraintValidator<Month,String> {

    @Override
    public void initialize(Month constraintAnnotation) {
    }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.equals("Styczeń") || s.equals("Luty") || s.equals("Marzec") ||
                s.equals("Kwiecień") || s.equals("Maj") || s.equals("Czerwiec") ||
                s.equals("Lipiec") || s.equals("Sierpień") || s.equals("Wrzesień") ||
                s.equals("Październik") || s.equals("Listopad") || s.equals("Grudzień")) {
            return true;

        }
        return false;
    }
}

