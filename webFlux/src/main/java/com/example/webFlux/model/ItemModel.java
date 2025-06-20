package com.example.webFlux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemModel {

    private String id;

    private String name;

    private Integer count;

    private List<SubItemModel> subItems;
}
