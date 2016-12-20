package com.marekdudek.cqrs_starter_kit.aggregates;

import com.marekdudek.cqrs_starter_kit.commands.OpenTab;
import com.marekdudek.cqrs_starter_kit.commands.PlaceOrder;
import com.marekdudek.cqrs_starter_kit.errors.TabNotOpen;
import com.marekdudek.cqrs_starter_kit.events.TabOpened;

import static com.google.common.collect.Lists.newArrayList;

public final class Tab {

    public Iterable<?> handle(final OpenTab command) {
        final TabOpened event = new TabOpened(command.getId(), command.getTableNumber(), command.getWaiter());
        return newArrayList(event);
    }

    public Iterable<?> handle(final PlaceOrder command) {
        throw new TabNotOpen();
    }
}
