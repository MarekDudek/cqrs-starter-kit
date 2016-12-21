package com.marekdudek.cqrs_starter_kit.values;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderedItem {

    int        menuNumber;
    String     description;
    boolean    isDrink;
    BigDecimal price;
}
