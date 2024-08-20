package org.example;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
public class ObjectFactoryHolder
{
    private final ObjectFactory<PrototypeComponent> PrototypeComponentobjectFactory;

    private final ObjectFactory<FirstSingleton> FirstSingletonobjectFactory;

    public ObjectFactoryHolder(ObjectFactory<PrototypeComponent> prototypeComponentobjectFactory, ObjectFactory<FirstSingleton> firstSingletonobjectFactory) {
        PrototypeComponentobjectFactory = prototypeComponentobjectFactory;
        FirstSingletonobjectFactory = firstSingletonobjectFactory;
    }

    public FirstSingleton getFirstSingleton()
    {
        return FirstSingletonobjectFactory.getObject();
    }

    public PrototypeComponent getPrototype()
    {
        return PrototypeComponentobjectFactory.getObject();
    }
}
