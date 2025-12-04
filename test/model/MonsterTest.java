package model;

import model.state.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MonsterTest {

    private Monster monster;
    private long now;

    @Before
    public void setUp() {
        now = System.currentTimeMillis();
        monster = new Monster(0, "01/01/2024", "TestMonster",
                now, now, 10, 10, 1, 3, 50, 100, 100);
    }

    @Test
    public void testMonsterCreation() {
        assertEquals("Mystery Egg", monster.getName());
        assertEquals(EvolutionStage.EGG, monster.getStage());
        assertEquals(SpeciesType.KUCING, monster.getSpecies());
    }

    @Test
    public void testIsEgg() {
        assertTrue(monster.isEgg());

        monster.setStage(EvolutionStage.BABY);
        assertFalse(monster.isEgg());
    }

    @Test
    public void testModifyHealth() {
        monster.modifyHealth(5);
        assertEquals(10, monster.getHP()); // capped at maxHP

        monster.modifyHealth(-3);
        assertEquals(7, monster.getHP());

        monster.modifyHealth(-20);
        assertEquals(0, monster.getHP()); // min 0
    }

    @Test
    public void testModifyHunger() {
        assertEquals(0, monster.getHunger()); // initial

        monster.modifyHunger(50);
        assertEquals(50, monster.getHunger());

        monster.modifyHunger(60);
        assertEquals(100, monster.getHunger()); // max 100

        monster.modifyHunger(-30);
        assertEquals(70, monster.getHunger());
    }

    @Test
    public void testModifyHappiness() {
        assertEquals(50, monster.getHappiness());

        monster.modifyHappiness(30);
        assertEquals(80, monster.getHappiness());

        monster.modifyHappiness(50);
        assertEquals(100, monster.getHappiness()); // max 100

        monster.modifyHappiness(-120);
        assertEquals(0, monster.getHappiness()); // min 0
    }

    @Test
    public void testModifyEnergy() {
        assertEquals(100, monster.getEnergy());

        monster.modifyEnergy(-20);
        assertEquals(80, monster.getEnergy());

        monster.modifyEnergy(30);
        assertEquals(100, monster.getEnergy()); // capped at maxEnergy
    }

    @Test
    public void testFeedAction() {
        monster.setStage(EvolutionStage.BABY);
        monster.setState(new NormalState());

        monster.modifyHunger(50);
        int hungerBefore = monster.getHunger();

        monster.feed();

        assertTrue(monster.getHunger() < hungerBefore);
    }

    @Test
    public void testPlayAction() {
        monster.setStage(EvolutionStage.BABY);
        monster.setState(new NormalState());

        int energyBefore = monster.getEnergy();
        int happinessBefore = monster.getHappiness();

        monster.play();

        assertTrue(monster.getEnergy() < energyBefore);
        assertTrue(monster.getHappiness() > happinessBefore);
    }

    @Test
    public void testSleepTransition() {
        monster.setStage(EvolutionStage.BABY);
        monster.setState(new NormalState());

        monster.sleep();

        assertTrue(monster.getCurrentState() instanceof SleepState);
    }

    @Test
    public void testDeadStateTransition() {
        monster.setStage(EvolutionStage.BABY);
        monster.setState(new NormalState());

        monster.modifyHealth(-20);
        monster.updateLogic();

        assertTrue(monster.getCurrentState() instanceof DeadState);
    }

    @Test
    public void testHungryStateTransition() {
        monster.setStage(EvolutionStage.BABY);
        monster.setState(new NormalState());

        monster.modifyHunger(85);
        monster.updateLogic();

        assertTrue(monster.getCurrentState() instanceof HungryState);
    }

    @Test
    public void testBoredStateTransition() {
        monster.setStage(EvolutionStage.BABY);
        monster.setState(new NormalState());

        monster.modifyHappiness(-50);
        monster.updateLogic();

        assertTrue(monster.getCurrentState() instanceof BoredState);
    }

    @Test
    public void testAgeIncrement() {
        int ageBefore = monster.getAgeSeconds();

        monster.addAgeSeconds(10);

        assertEquals(ageBefore + 10, monster.getAgeSeconds());
    }

    @Test
    public void testStarve() {
        int hpBefore = monster.getHP();

        monster.starve();

        assertEquals(hpBefore - 1, monster.getHP());
    }

    @Test
    public void testEggFreezeMechanic() {
        assertTrue(monster.isEgg());

        int hungerBefore = monster.getHunger();
        int happinessBefore = monster.getHappiness();
        int hpBefore = monster.getHP();

        monster.updateLogic();

        assertEquals(hungerBefore, monster.getHunger());
        assertEquals(happinessBefore, monster.getHappiness());
        assertEquals(hpBefore, monster.getHP());
    }
}