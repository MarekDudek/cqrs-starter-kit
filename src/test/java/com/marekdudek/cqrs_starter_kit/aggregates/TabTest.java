package com.marekdudek.cqrs_starter_kit.aggregates;

import com.marekdudek.cqrs_starter_kit.commands.OpenTab;
import com.marekdudek.cqrs_starter_kit.commands.PlaceOrder;
import com.marekdudek.cqrs_starter_kit.errors.TabNotOpen;
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
    private final UUID   id     = UUID.randomUUID();
    private final String waiter = "John";
    private final int    table  = 2;

    private final OrderedItem drink1 = new OrderedItem(3, "a beer", true, new BigDecimal(12));
    private final OrderedItem drink2 = new OrderedItem(4, "wine", true, new BigDecimal(15));

    @Test
    public void can_open_a_new_tab() {
        // when
        // given
        final Tab         tab     = new Tab();
        final OpenTab     command = new OpenTab(id, table, waiter);
        final Iterable<?> events  = tab.handle(command);
        // then
        assertThat(events.iterator().next(), is(equalTo(new TabOpened(id, table, waiter))));
    }

    @Test(expected = TabNotOpen.class)
    public void cannot_order_with_unopened_tab() {
        // when
        final Tab        tab     = new Tab();
        final PlaceOrder command = new PlaceOrder(id, singletonList(drink1));
        tab.handle(command);
        // then throws exception
    }

    @Test
    public void can_place_drinks_order() {
        // given
        final Tab         tab            = new Tab();
        final OpenTab     openTabCommand = new OpenTab(id, table, waiter);
        final Iterable<?> openEvents     = tab.handle(openTabCommand);
        tab.apply((TabOpened) openEvents.iterator().next());
        // when
        final PlaceOrder  command = new PlaceOrder(id, asList(drink1, drink2));
        final Iterable<?> events  = tab.handle(command);

    }
}
