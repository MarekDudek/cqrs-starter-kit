package com.marekdudek.cqrs_starter_kit.events;

import com.marekdudek.cqrs_starter_kit.values.OrderedItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DrinksOrdered {

    UUID              id;
    List<OrderedItem> items;
}
