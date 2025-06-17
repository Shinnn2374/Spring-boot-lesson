package com.example.simple_web;

import lombok.Data;

@Data
public class Task {

    private Long id;

    private String title;

    private String description;

    private int priority;
}
