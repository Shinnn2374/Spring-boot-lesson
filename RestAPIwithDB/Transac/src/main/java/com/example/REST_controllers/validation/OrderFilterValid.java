package com.example.REST_controllers.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = OrderFiltervalidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public interface OrderFilterValid
{
    String message() default "Поля плагинации должны быть указаны если вы указываете minCost или maxCost то оба поля должны быть указаны ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload default {};
}
