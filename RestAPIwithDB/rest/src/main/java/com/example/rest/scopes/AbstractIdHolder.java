package com.example.rest.scopes;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public  abstract class AbstractIdHolder implements IdHolder
{
    private final UUID requestId;

    protected AbstractIdHolder()
    {
        this.requestId = UUID.randomUUID();
    }

    @Override
    public void logId()
    {
        log.info("{} is {}", hashCode(), requestId );
    }

    abstract String holderType();
}
