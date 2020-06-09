package fr.plb.springsecuritydemo.service.dto.validation;

import org.hibernate.validator.internal.constraintvalidators.hv.UniqueElementsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {
    String message() default "Ce nom d'utilisateur existe déjà";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
