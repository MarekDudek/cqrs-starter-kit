package com.marekdudek.cqrs_starter_kit.commands;

import com.marekdudek.cqrs_starter_kit.values.OrderedItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PlaceOrder {

    UUID              id;
    List<OrderedItem> items;
}
