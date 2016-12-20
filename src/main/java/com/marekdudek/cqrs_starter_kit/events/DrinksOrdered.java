package com.marekdudek.cqrs_starter_kit.events;

import com.marekdudek.cqrs_starter_kit.values.OrderedItem;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DrinksOrdered {

    private final UUID              id;
    private final List<OrderedItem> items;
}
