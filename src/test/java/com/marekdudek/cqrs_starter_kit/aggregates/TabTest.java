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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TabTest {

    // given
    private final UUID id = UUID.randomUUID();

    @Test
    public void can_open_a_new_tab() {
        // given
        final String waiter = "John";
        final int    table  = 2;
        // when
        final Tab         tab     = new Tab();
        final OpenTab     command = new OpenTab(id, table, waiter);
        final Iterable<?> events  = tab.handle(command);
        // then
        assertThat(events.iterator().next(), is(equalTo(new TabOpened(id, table, waiter))));
    }

    @Test(expected = TabNotOpen.class)
    public void cannot_order_with_unopened_tab() {
        // given
        final int         menuItem1    = 3;
        final String      description1 = "a beer";
        final BigDecimal  price1       = new BigDecimal(12);
        final OrderedItem drink1       = new OrderedItem(menuItem1, description1, true, price1);
        final int         menuItem2    = 4;
        final String      description2 = "wine";
        final BigDecimal  price2       = new BigDecimal(15);
        final OrderedItem drink2       = new OrderedItem(menuItem2, description2, true, price2);
        // when
        final Tab        tab     = new Tab();
        final PlaceOrder command = new PlaceOrder(id, asList(drink1));
        tab.handle(command);
        // then throws exception
    }
}
