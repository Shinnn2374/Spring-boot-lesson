package org.example;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory
{
    private final Map<Class<?>,Object> container = new HashMap<>();

    public void register(Class<?> type)
    {
        Object mainBean = create(type);
        container.put(type, mainBean);

        for (Field field : type.getDeclaredFields()){
            if (field.isAnnotationPresent(inject.class)){
                Object bean = container.get(field.getType());

                if (bean == null) continue;


                field.setAccessible(true);
                try {
                    field.set(mainBean, bean);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public <T> T getBean(Class<?> type)
    {
        Object bean = container.get(type);

        if (bean == null) throw new NullPointerException();

        return (T) bean;

    }


    public final Object create(Class<?> type)
    {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
