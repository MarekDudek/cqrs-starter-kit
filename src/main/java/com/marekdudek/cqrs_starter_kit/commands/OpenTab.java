package com.marekdudek.cqrs_starter_kit.commands;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OpenTab {

    UUID   id;
    int    tableNumber;
    String waiter;
}
