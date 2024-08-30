package com.example.LessonThree.core;

public interface BackGroundTaskExecutor
{
    void schedule(String id, Runnable task);
}
