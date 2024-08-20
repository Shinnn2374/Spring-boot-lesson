package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Worker
{
    private final LogicClass simpleLogic;

    private final LogicClass dataSimpleLogic;

    private final DataComponent dataComponent;

    @Autowired
    public Worker(LogicClass simpleLogic, LogicClass dataSimpleLogic, DataComponent dataComponent) {
        this.simpleLogic = simpleLogic;
        this.dataSimpleLogic = dataSimpleLogic;
        this.dataComponent = dataComponent;
    }


    public void call()
    {
        simpleLogic.simpleLogic();
        simpleLogic.printLogicData();
        dataComponent.someWork();
    }
}
