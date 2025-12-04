package model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class EvolutionManagerTest {

    private Monster monster;

    @Before
    public void setUp() {
        long now = System.currentTimeMillis();
        monster = new Monster(0, "01/01/2024", "TestMonster",
                now, now, 10, 10, 1, 3, 50, 100, 100);
    }

    @Test
    public void testCanEvolveEggToBaby() {
        assertEquals(EvolutionStage.EGG, monster.getStage());
        assertFalse(EvolutionManager.canEvolve(monster));

        monster.setAgeSeconds(180);
        assertTrue(EvolutionManager.canEvolve(monster));
    }

    @Test
    public void testCanEvolveBabyToChild() {
        monster.setStage(EvolutionStage.BABY);
        monster.setAgeSeconds(599);

        assertFalse(EvolutionManager.canEvolve(monster));

        monster.setAgeSeconds(600);
        assertTrue(EvolutionManager.canEvolve(monster));
    }

    @Test
    public void testCanEvolveChildToAdult() {
        monster.setStage(EvolutionStage.CHILD);
        monster.setAgeSeconds(1499);

        assertFalse(EvolutionManager.canEvolve(monster));

        monster.setAgeSeconds(1500);
        assertTrue(EvolutionManager.canEvolve(monster));
    }

    @Test
    public void testCannotEvolveAdult() {
        monster.setStage(EvolutionStage.ADULT);
        monster.setAgeSeconds(10000);

        assertFalse(EvolutionManager.canEvolve(monster));
    }

    @Test
    public void testEvolveEggToBaby() {
        monster.setAgeSeconds(180);

        EvolutionManager.evolve(monster);

        assertEquals(EvolutionStage.BABY, monster.getStage());
        assertEquals("Kitty", monster.getName());
        assertEquals(10, monster.getHP());
        assertEquals(100, monster.getHappiness());
    }

    @Test
    public void testEvolveBabyToChild() {
        monster.setStage(EvolutionStage.BABY);
        monster.setName("Kitty");
        monster.setAgeSeconds(600);
        monster.modifyHealth(-5);

        EvolutionManager.evolve(monster);

        assertEquals(EvolutionStage.CHILD, monster.getStage());
        assertEquals("Catmon", monster.getName());
        assertEquals(10, monster.getHP());
    }

    @Test
    public void testEvolveChildToAdult() {
        monster.setStage(EvolutionStage.CHILD);
        monster.setName("Catmon");
        monster.setAgeSeconds(1500);

        EvolutionManager.evolve(monster);

        assertEquals(EvolutionStage.ADULT, monster.getStage());
        assertEquals("Meowster", monster.getName());
    }

    @Test
    public void testEvolveSlimeSpecies() {
        long now = System.currentTimeMillis();
        Monster slime = new Monster(1, "01/01/2024", "SlimeTest",
                now, now, 10, 10, 1, 3, 50, 100, 100);

        slime.setAgeSeconds(180);
        EvolutionManager.evolve(slime);

        assertEquals("Slimelet", slime.getName());

        slime.setAgeSeconds(600);
        EvolutionManager.evolve(slime);

        assertEquals("Slimeon", slime.getName());

        slime.setAgeSeconds(1500);
        EvolutionManager.evolve(slime);

        assertEquals("Slimeking", slime.getName());
    }
}