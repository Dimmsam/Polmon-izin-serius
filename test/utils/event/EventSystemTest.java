package utils.event;

import model.*;
import model.state.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class EventSystemTest {
    private Monster monster;
    private boolean eventFired;
    private Object eventData;

    @Before
    public void setUp() {
        long now = System.currentTimeMillis();
        monster = new Monster(0, "01/01/2024", "TestMonster", now, now, 10, 10, 1, 3, 50, 100, 100);
        eventFired = false;
        eventData = null;
    }

    @Test
    public void testEvolutionEventFires() {
        monster.getEvolutionEvents().subscribe(new EventListener<GameEvent<EvolutionStage>>() {
            @Override
            public void onEvent(GameEvent<EvolutionStage> event) {
                eventFired = true;
                eventData = event.getPayload();
            }
        });
        monster.setStage(EvolutionStage.BABY);
        assertTrue(eventFired);
        assertEquals(EvolutionStage.BABY, eventData);
    }

    @Test
    public void testStateChangeEventFires() {
        monster.setStage(EvolutionStage.BABY);
        monster.getStateChangeEvents().subscribe(new EventListener<GameEvent<PolmonState>>() {
            @Override
            public void onEvent(GameEvent<PolmonState> event) {
                eventFired = true;
            }
        });
        monster.setState(new HungryState());
        assertTrue(eventFired);
    }

    @Test
    public void testHealthChangeEventFires() {
        monster.getHealthChangeEvents().subscribe(new EventListener<GameEvent<Integer>>() {
            @Override
            public void onEvent(GameEvent<Integer> event) {
                eventFired = true;
                eventData = event.getPayload();
            }
        });
        monster.modifyHealth(-3);
        assertTrue(eventFired);
        assertEquals(7, eventData);
    }

    @Test
    public void testMultipleSubscribers() {
        final int[] eventCount = {0};
        EventListener<GameEvent<EvolutionStage>> listener1 = new EventListener<GameEvent<EvolutionStage>>() {
            @Override
            public void onEvent(GameEvent<EvolutionStage> event) {
                eventCount[0]++;
            }
        };
        EventListener<GameEvent<EvolutionStage>> listener2 = new EventListener<GameEvent<EvolutionStage>>() {
            @Override
            public void onEvent(GameEvent<EvolutionStage> event) {
                eventCount[0]++;
            }
        };
        monster.getEvolutionEvents().subscribe(listener1);
        monster.getEvolutionEvents().subscribe(listener2);
        monster.setStage(EvolutionStage.BABY);
        assertEquals(2, eventCount[0]);
    }

    @Test
    public void testUnsubscribe() {
        EventListener<GameEvent<EvolutionStage>> listener = new EventListener<GameEvent<EvolutionStage>>() {
            @Override
            public void onEvent(GameEvent<EvolutionStage> event) {
                eventFired = true;
            }
        };
        monster.getEvolutionEvents().subscribe(listener);
        monster.getEvolutionEvents().unsubscribe(listener);
        monster.setStage(EvolutionStage.BABY);
        assertFalse(eventFired);
    }

    @Test
    public void testEventManagerClear() {
        EventManager<GameEvent<String>> manager = new EventManager<>();
        manager.subscribe(new EventListener<GameEvent<String>>() {
            @Override
            public void onEvent(GameEvent<String> event) {}
        });
        manager.subscribe(new EventListener<GameEvent<String>>() {
            @Override
            public void onEvent(GameEvent<String> event) {}
        });
        assertEquals(2, manager.getListenerCount());
        manager.clear();
        assertEquals(0, manager.getListenerCount());
    }
}