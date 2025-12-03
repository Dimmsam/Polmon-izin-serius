package model.state;
import model.Monster;

public class BoredState implements PolmonState {
    @Override
    public void onEnter(Monster ctx) {
        System.out.println("State: BORED (Bosan)");
    }

    @Override
    public void onTick(Monster ctx) {
        // Bosan bisa mengurangi energy (malas-malasan)
        ctx.modifyEnergy(-1);
    }

    @Override
    public void feed(Monster ctx) {
        System.out.println("Makan sambil cemberut...");
        ctx.modifyHunger(-10);
    }

    @Override
    public void play(Monster ctx) {
        System.out.println("Yeay! Akhirnya main!");
        ctx.modifyHappiness(30); // Main obat bosan
        ctx.modifyEnergy(-10);   // Main kuras tenaga
        // Logic pindah ke Normal ada di Monster.updateLogic()
    }

    @Override
    public void sleep(Monster ctx) {
        System.out.println("Tidur karena bosan.");
        ctx.setState(new SleepState());
    }

    @Override
    public void wakeUp(Monster ctx) { System.out.println("Masih bosan..."); }
}