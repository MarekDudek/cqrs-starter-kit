package com.marekdudek.cqrs_starter_kit.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TabOpened {

    UUID   id;
    int    tableNumber;
    String waiter;
}
