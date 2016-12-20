package com.marekdudek.cqrs_starter_kit.values;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class OrderedItem {

    int        menuNumber;
    String     description;
    boolean    isDrink;
    BigDecimal price;
}
