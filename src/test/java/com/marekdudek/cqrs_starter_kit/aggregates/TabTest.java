package com.marekdudek.cqrs_starter_kit.aggregates;

import com.marekdudek.cqrs_starter_kit.commands.OpenTab;
import com.marekdudek.cqrs_starter_kit.events.TabOpened;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TabTest {

    @Test
    public void opening_tab() {
        // given
        final UUID   id     = UUID.randomUUID();
        final int    table  = 2;
        final String waiter = "John";
        // when
        final Tab         tab     = new Tab();
        final OpenTab     command = new OpenTab(id, table, waiter);
        final Iterable<?> events  = tab.handle(command);
        //
        assertThat(events.iterator().next(), is(equalTo(new TabOpened(id, table, waiter))));
    }
}
