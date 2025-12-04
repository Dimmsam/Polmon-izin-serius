package utils;

import model.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.File;

public class SaveLoadTest {

    private static final String TEST_SAVE_FILE = "test_polmon.xml";
    private Monster monster;

    @Before
    public void setUp() {
        long now = System.currentTimeMillis();
        monster = new Monster(0, "01/01/2024", "TestMonster",
                now, now, 8, 10, 1, 3, 75, 90, 100);

        monster.setStage(EvolutionStage.BABY);
        monster.setName("Kitty");
        monster.setAgeSeconds(200);
        monster.modifyHunger(30);
    }

    @After
    public void tearDown() {
        File saveFile = new File("./saves/" + TEST_SAVE_FILE);
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }

    @Test
    public void testSaveMonster() {
        MonsterWriter.write(monster, new File("./"), TEST_SAVE_FILE);

        File saveFile = new File("./saves/" + TEST_SAVE_FILE);
        assertTrue(saveFile.exists());
    }

    @Test
    public void testLoadMonster() {
        MonsterWriter.write(monster, new File("./"), TEST_SAVE_FILE);

        Monster loaded = MonsterReader.read(new File("./"), TEST_SAVE_FILE);

        assertNotNull(loaded);
        assertEquals(monster.getID(), loaded.getID());
        assertEquals(monster.getName(), loaded.getName());
        assertEquals(monster.getStage(), loaded.getStage());
        assertEquals(monster.getAgeSeconds(), loaded.getAgeSeconds());
    }

    @Test
    public void testSaveLoadAllAttributes() {
        MonsterWriter.write(monster, new File("./"), TEST_SAVE_FILE);
        Monster loaded = MonsterReader.read(new File("./"), TEST_SAVE_FILE);

        assertEquals(monster.getHP(), loaded.getHP());
        assertEquals(monster.getEnergy(), loaded.getEnergy());
        assertEquals(monster.getHappiness(), loaded.getHappiness());
        assertEquals(monster.getHunger(), loaded.getHunger());
        assertEquals(monster.getSpecies(), loaded.getSpecies());
    }

    @Test
    public void testLoadNonExistentFile() {
        Monster loaded = MonsterReader.read(new File("./"), "nonexistent.xml");
        assertNull(loaded);
    }
}