package com.marekdudek.cqrs_starter_kit.aggregates;

import com.marekdudek.cqrs_starter_kit.commands.OpenTab;
import com.marekdudek.cqrs_starter_kit.commands.PlaceOrder;
import com.marekdudek.cqrs_starter_kit.errors.TabNotOpen;
import com.marekdudek.cqrs_starter_kit.events.DrinksOrdered;
import com.marekdudek.cqrs_starter_kit.events.FoodOrdered;
import com.marekdudek.cqrs_starter_kit.events.TabOpened;
import com.marekdudek.cqrs_starter_kit.values.OrderedItem;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

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

        List<Object> events = newArrayList();

        List<OrderedItem> drinks = command.getItems().stream().filter(OrderedItem::isDrink).collect(toList());
        if (isNotEmpty(drinks)) {
            DrinksOrdered drinksEvent = new DrinksOrdered(command.getId(), drinks);
            events.add(drinksEvent);
        }

        List<OrderedItem> food = command.getItems().stream().filter(i -> isFalse(i.isDrink())).collect(toList());
        if (isNotEmpty(food)) {
            FoodOrdered foodEvent = new FoodOrdered(command.getId(), food);
            events.add(foodEvent);
        }

        return events;
    }
}
