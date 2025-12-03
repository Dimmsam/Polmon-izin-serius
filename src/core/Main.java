package core;

import model.Monster;
import utils.MonsterReader;
import utils.MonsterWriter;
import view.GameWindow;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Main {
    private static final String SAVE_FILE = "polmon.xml";

    public static void main(String[] args) {
        Monster monster = loadOrCreateMonster();

        GameEngine engine = GameEngine.getInstance();
        engine.initialize(monster, null);

        Runtime.getRuntime().addShutdownHook(new ShutdownHandler(engine));

        engine.start();

        GameWindow window = new GameWindow(engine);
        window.display();
    }

    public static void newGame() {
        File saveFile = new File("./saves/" + SAVE_FILE);
        if (saveFile.exists()) {
            saveFile.delete();
        }

        Monster newMonster = createNewMonster();

        GameEngine engine = GameEngine.getInstance();
        engine.stop();
        engine.initialize(newMonster, null);
        engine.start();

        GameWindow window = new GameWindow(engine);
        window.display();
    }

    private static Monster loadOrCreateMonster() {
        Monster monster = MonsterReader.read(new File("./"), SAVE_FILE);

        if (monster != null) {
            return monster;
        }

        return createNewMonster();
    }

    private static Monster createNewMonster() {
        long now = new Date().getTime();
        String birthday = new SimpleDateFormat("MM/dd/yyyy").format(now);

        Random random = new Random();
        int speciesID = random.nextInt(2);

        String[] names = {"Kucingmon", "Slimemon"};
        String name = names[speciesID];

        return new Monster(
                speciesID,
                birthday,
                name,
                now,
                now,
                10,
                10,
                1,
                3,
                100,
                100,
                100
        );
    }
}