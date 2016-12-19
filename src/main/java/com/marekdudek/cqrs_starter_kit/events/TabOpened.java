package com.marekdudek.cqrs_starter_kit.events;

import lombok.Data;

import java.util.UUID;

@Data
public final class TabOpened {

    private final UUID   id;
    private final int    tableNumber;
    private final String waiter;
}
