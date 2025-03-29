package com.example.webFluxExample.model;

import com.example.webFluxExample.entity.SubItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubItemModel {
    private String name;
    private BigDecimal price;

    public static SubItemModel from(SubItem subItem) {
        return new SubItemModel(subItem.getName(), subItem.getPrice());
    }
}
