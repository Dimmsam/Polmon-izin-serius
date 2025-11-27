package core;

public class ShutdownHandler extends Thread {
    private GameEngine engine;

    public ShutdownHandler(GameEngine engine) {
        this.engine = engine;
    }

    @Override
    public void run() {
        engine.stop();
        engine.save();
    }
}