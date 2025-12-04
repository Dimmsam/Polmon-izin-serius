package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class MonsterDatabaseTest {

    @Test
    public void testGetNameForEgg() {
        String name = MonsterDatabase.getName(SpeciesType.KUCING, EvolutionStage.EGG);
        assertEquals("Mystery Egg", name);

        name = MonsterDatabase.getName(SpeciesType.SLIME, EvolutionStage.EGG);
        assertEquals("Mystery Egg", name);
    }

    @Test
    public void testGetNameForKucing() {
        assertEquals("Kitty", MonsterDatabase.getName(SpeciesType.KUCING, EvolutionStage.BABY));
        assertEquals("Catmon", MonsterDatabase.getName(SpeciesType.KUCING, EvolutionStage.CHILD));
        assertEquals("Meowster", MonsterDatabase.getName(SpeciesType.KUCING, EvolutionStage.ADULT));
    }

    @Test
    public void testGetNameForSlime() {
        assertEquals("Slimelet", MonsterDatabase.getName(SpeciesType.SLIME, EvolutionStage.BABY));
        assertEquals("Slimeon", MonsterDatabase.getName(SpeciesType.SLIME, EvolutionStage.CHILD));
        assertEquals("Slimeking", MonsterDatabase.getName(SpeciesType.SLIME, EvolutionStage.ADULT));
    }
}