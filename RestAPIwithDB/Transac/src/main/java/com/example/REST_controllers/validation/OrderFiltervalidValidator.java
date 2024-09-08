package com.example.REST_controllers.validation;

import com.example.REST_controllers.web.model.OrderFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class OrderFiltervalidValidator implements ConstraintValidator<OrderFilterValid, OrderFilter>
{
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize()))
        {
            return false;
        }
        if (value.getMinCost() == null && value.getMaxCost() != null || value.getMinCost() != null && value.getMaxCost() == null)
        {
            return false;
        }
        return true;
    }
}
