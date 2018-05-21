package pl.sda.patient_registration_app.validators;

import pl.sda.patient_registration_app.annotations.PasswordMatches;
import pl.sda.patient_registration_app.dto.NewUserRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        NewUserRegistrationDto user = (NewUserRegistrationDto) o;
        return user.getPassword().equals(user.getMatchingPassword());
    }


}