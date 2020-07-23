package com.yte.intern.project.manageclients.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TcKimlikNoValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TcKimlikNo { // to avoid error, we need those 3

    String message() default "Tc kimlik numarası formatı uygun değildir";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
