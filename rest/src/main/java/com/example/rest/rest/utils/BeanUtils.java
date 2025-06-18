package com.example.rest.rest.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class BeanUtils {

    @SneakyThrows
    public void copyNonNullProperties(Object Source, Object destination){
        Class<?> sourceClass = Source.getClass();
        Field[] fields = sourceClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(Source);

            if (value != null) {
                field.set(destination, value);
            }
        }
    }
}
