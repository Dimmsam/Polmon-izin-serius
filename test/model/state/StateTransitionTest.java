package model.state;

import model.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class StateTransitionTest {

    private Monster monster;

    @Before
    public void setUp() {
        long now = System.currentTimeMillis();
        monster = new Monster(0, "01/01/2024", "TestMonster",
                now, now, 10, 10, 1, 3, 50, 100, 100);
        monster.setStage(EvolutionStage.BABY);
    }

    @Test
    public void testNormalStateOnTick() {
        monster.setState(new NormalState());

        int hungerBefore = monster.getHunger();
        int happinessBefore = monster.getHappiness();

        monster.getCurrentState().onTick(monster);

        assertEquals(hungerBefore + 1, monster.getHunger());
        assertEquals(happinessBefore - 1, monster.getHappiness());
    }

    @Test
    public void testHungryStateOnTick() {
        monster.setState(new HungryState());

        int hpBefore = monster.getHP();
        int happinessBefore = monster.getHappiness();

        monster.getCurrentState().onTick(monster);

        assertEquals(hpBefore - 1, monster.getHP());
        assertEquals(happinessBefore - 2, monster.getHappiness());
    }

    @Test
    public void testSleepStateOnTick() {
        monster.setState(new SleepState());
        monster.modifyHealth(-5);
        monster.modifyEnergy(-50);

        int hpBefore = monster.getHP();
        int energyBefore = monster.getEnergy();
        int happinessBefore = monster.getHappiness();

        monster.getCurrentState().onTick(monster);

        assertEquals(hpBefore + 3, monster.getHP());
        assertEquals(energyBefore + 10, monster.getEnergy());
        assertEquals(happinessBefore + 1, monster.getHappiness());
    }

    @Test
    public void testBoredStateOnTick() {
        monster.setState(new BoredState());

        int hungerBefore = monster.getHunger();
        int happinessBefore = monster.getHappiness();

        monster.getCurrentState().onTick(monster);

        assertEquals(hungerBefore + 1, monster.getHunger());
        assertEquals(happinessBefore - 3, monster.getHappiness());
    }

    @Test
    public void testDeadStateOnTick() {
        monster.setState(new DeadState());

        int hpBefore = monster.getHP();
        int happinessBefore = monster.getHappiness();

        monster.getCurrentState().onTick(monster);

        assertEquals(hpBefore, monster.getHP());
        assertEquals(happinessBefore, monster.getHappiness());
    }

    @Test
    public void testSleepToNormalTransition() {
        monster.setState(new SleepState());
        monster.modifyHunger(30);
        monster.modifyHappiness(-10);

        monster.wakeUp();

        assertTrue(monster.getCurrentState() instanceof NormalState);
    }

    @Test
    public void testSleepToHungryTransition() {
        monster.setState(new SleepState());
        monster.modifyHunger(85);

        monster.wakeUp();

        assertTrue(monster.getCurrentState() instanceof HungryState);
    }

    @Test
    public void testSleepToBoredTransition() {
        monster.setState(new SleepState());
        monster.modifyHappiness(-35);

        monster.wakeUp();

        assertTrue(monster.getCurrentState() instanceof BoredState);
    }

    @Test
    public void testHungryToNormalAfterFeed() {
        monster.setState(new HungryState());
        monster.modifyHunger(85);

        monster.feed();

        assertTrue(monster.getCurrentState() instanceof NormalState);
    }

    @Test
    public void testBoredToNormalAfterPlay() {
        monster.setState(new BoredState());
        monster.modifyHappiness(-50);

        monster.play();

        assertTrue(monster.getCurrentState() instanceof NormalState);
    }
}