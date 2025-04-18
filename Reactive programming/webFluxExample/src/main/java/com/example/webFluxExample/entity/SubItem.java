package com.example.webFluxExample.entity;

import com.example.webFluxExample.model.SubItemModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubItem {
    private String name;
    private BigDecimal price;

    public static SubItem from(SubItemModel model) {
        return new SubItem(model.getName(), model.getPrice());
    }
}
