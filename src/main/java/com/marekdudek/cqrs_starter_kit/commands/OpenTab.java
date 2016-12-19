package com.marekdudek.cqrs_starter_kit.commands;

import lombok.Data;

import java.util.UUID;

@Data
public final class OpenTab {

    private final UUID   id;
    private final int    tableNumber;
    private final String waiter;
}
