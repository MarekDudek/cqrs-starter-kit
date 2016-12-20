package com.marekdudek.cqrs_starter_kit.values;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderedItem {

    private final int        menuNumber;
    private final String     description;
    private final boolean    isDrink;
    private final BigDecimal price;
}
