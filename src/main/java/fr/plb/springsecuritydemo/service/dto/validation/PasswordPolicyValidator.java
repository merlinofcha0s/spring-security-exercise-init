package fr.plb.springsecuritydemo.service.dto.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class PasswordPolicyValidator implements ConstraintValidator<PasswordPolicy, String> {

    @Override
    public boolean isValid(String rawPassword, ConstraintValidatorContext constraintValidatorContext) {
        List<Rule> passwordRules = new ArrayList<>();
        passwordRules.add(new LengthRule(10, 100));
        CharacterCharacteristicsRule passChars = new CharacterCharacteristicsRule(3,
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1));

        passwordRules.add(passChars);
        passwordRules.add(new RepeatCharactersRule(3));

        PasswordValidator validator = new PasswordValidator(passwordRules);
        PasswordData passwordData = new PasswordData(rawPassword);
        return validator.validate(passwordData).isValid();
    }
}
