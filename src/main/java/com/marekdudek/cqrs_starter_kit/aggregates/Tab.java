package com.marekdudek.cqrs_starter_kit.aggregates;

import com.marekdudek.cqrs_starter_kit.commands.OpenTab;
import com.marekdudek.cqrs_starter_kit.commands.PlaceOrder;
import com.marekdudek.cqrs_starter_kit.errors.TabNotOpen;
import com.marekdudek.cqrs_starter_kit.events.TabOpened;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

class Tab {

    boolean open = false;

    Iterable<?> handle(OpenTab command) {
        TabOpened event = new TabOpened(command.getId(), command.getTableNumber(), command.getWaiter());
        return singletonList(event);
    }

    void apply(TabOpened event) {
        open = true;
    }

    Iterable<?> handle(PlaceOrder command) {
        if (!open)
            throw new TabNotOpen();
        return emptyList();
    }
}
