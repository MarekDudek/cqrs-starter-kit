package com.marekdudek.cqrs_starter_kit.aggregates;

import com.marekdudek.cqrs_starter_kit.commands.OpenTab;
import com.marekdudek.cqrs_starter_kit.commands.PlaceOrder;
import com.marekdudek.cqrs_starter_kit.errors.TabNotOpen;
import com.marekdudek.cqrs_starter_kit.events.DrinksOrdered;
import com.marekdudek.cqrs_starter_kit.events.FoodOrdered;
import com.marekdudek.cqrs_starter_kit.events.TabOpened;
import com.marekdudek.cqrs_starter_kit.values.OrderedItem;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TabTest {

    /**
     * System under test.
     */
    private Tab tab;

    // fields
    private UUID id = UUID.randomUUID();
    private String waiter = "John";
    private int table = 2;

    // values
    private OrderedItem drink1 = new OrderedItem(3, "a beer", true, new BigDecimal(12));
    private OrderedItem drink2 = new OrderedItem(4, "wine", true, new BigDecimal(15));
    private OrderedItem food1 = new OrderedItem(5, "stake", false, new BigDecimal(17));
    private OrderedItem food2 = new OrderedItem(6, "salad", false, new BigDecimal(19));

    // commands
    private OpenTab openTab = new OpenTab(id, table, waiter);

    @Before
    public void setUp() {
        tab = new Tab();
    }

    @Test
    public void can_open_a_new_tab() {
        // when
        Iterable<?> events = tab.handle(openTab);
        // then
        Object event = events.iterator().next();
        assertThat(event, is(equalTo(new TabOpened(id, table, waiter))));
    }

    @Test(expected = TabNotOpen.class)
    public void cannot_order_with_unopened_tab() {
        // when
        PlaceOrder placeOrder = new PlaceOrder(id, singletonList(drink1));
        tab.handle(placeOrder);
        // then throws exception
    }

    @Test
    public void can_place_drinks_order() {
        // given
        Iterable<?> openEvents = tab.handle(openTab);
        tab.apply((TabOpened) openEvents.iterator().next());
        // when
        PlaceOrder placeOrder = new PlaceOrder(id, asList(drink1, drink2));
        Iterable<?> orderEvents = tab.handle(placeOrder);
        // then
        assertThat(orderEvents.iterator().next(), is(equalTo(new DrinksOrdered(id, asList(drink1, drink2)))));
    }

    @Test
    public void can_place_food_order() {
        // given
        Iterable<?> openEvents = tab.handle(openTab);
        tab.apply((TabOpened) openEvents.iterator().next());
        // when
        PlaceOrder placeOrder = new PlaceOrder(id, asList(food1, food2));
        Iterable<?> events = tab.handle(placeOrder);
        // then
        assertThat(events.iterator().next(), is(equalTo(new FoodOrdered(id, asList(food1, food2)))));
    }

    @Test
    public void can_place_drink_and_food_order() {
        // given
        Iterable<?> openEvents = tab.handle(openTab);
        tab.apply((TabOpened) openEvents.iterator().next());
        // when
        PlaceOrder placeOrder = new PlaceOrder(id, asList(food1, drink1));
        Iterable<?> orderEvents = tab.handle(placeOrder);
        // then
        Iterator<?> iterator = orderEvents.iterator();
        Object drinkEvent = iterator.next();
        assertThat(drinkEvent, is(equalTo(new DrinksOrdered(id, singletonList(drink1)))));
        Object foodEvent = iterator.next();
        assertThat(foodEvent, is(equalTo(new FoodOrdered(id, singletonList(food1)))));
    }

}
