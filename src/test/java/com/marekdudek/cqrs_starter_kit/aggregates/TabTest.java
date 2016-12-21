package com.marekdudek.cqrs_starter_kit.aggregates;

import com.marekdudek.cqrs_starter_kit.commands.OpenTab;
import com.marekdudek.cqrs_starter_kit.commands.PlaceOrder;
import com.marekdudek.cqrs_starter_kit.errors.TabNotOpen;
import com.marekdudek.cqrs_starter_kit.events.DrinksOrdered;
import com.marekdudek.cqrs_starter_kit.events.FoodOrdered;
import com.marekdudek.cqrs_starter_kit.events.TabOpened;
import com.marekdudek.cqrs_starter_kit.values.OrderedItem;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TabTest {

    // given
    UUID   id     = UUID.randomUUID();
    String waiter = "John";
    int    table  = 2;

    OrderedItem drink1 = new OrderedItem(3, "a beer", true, new BigDecimal(12));
    OrderedItem drink2 = new OrderedItem(4, "wine", true, new BigDecimal(15));

    OrderedItem food1 = new OrderedItem(5, "stake", false, new BigDecimal(17));
    OrderedItem food2 = new OrderedItem(6, "salad", false, new BigDecimal(19));

    @Test
    public void can_open_a_new_tab() {
        // given
        Tab         tab     = new Tab();
        OpenTab     command = new OpenTab(id, table, waiter);
        Iterable<?> events  = tab.handle(command);
        // then
        assertThat(events.iterator().next(), is(equalTo(new TabOpened(id, table, waiter))));
    }

    @Test(expected = TabNotOpen.class)
    public void cannot_order_with_unopened_tab() {
        // when
        Tab        tab     = new Tab();
        PlaceOrder command = new PlaceOrder(id, singletonList(drink1));
        tab.handle(command);
        // then throws exception
    }

    @Test
    public void can_place_drinks_order() {
        // given
        Tab         tab            = new Tab();
        OpenTab     openTabCommand = new OpenTab(id, table, waiter);
        Iterable<?> openEvents     = tab.handle(openTabCommand);
        tab.apply((TabOpened) openEvents.iterator().next());
        // when
        PlaceOrder  command = new PlaceOrder(id, asList(drink1, drink2));
        Iterable<?> events  = tab.handle(command);
        // then
        assertThat(events.iterator().next(), is(equalTo(new DrinksOrdered(id, asList(drink1, drink2)))));
    }

    @Test
    public void can_place_food_order() {
        // given
        Tab         tab            = new Tab();
        OpenTab     openTabCommand = new OpenTab(id, table, waiter);
        Iterable<?> openEvents     = tab.handle(openTabCommand);
        tab.apply((TabOpened) openEvents.iterator().next());
        // when
        PlaceOrder  command = new PlaceOrder(id, asList(food1, food2));
        Iterable<?> events  = tab.handle(command);
        // then
        assertThat(events.iterator().next(), is(equalTo(new FoodOrdered(id, asList(food1, food2)))));
    }
}
