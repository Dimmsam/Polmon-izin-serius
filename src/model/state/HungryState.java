package model.state;
import model.Monster;

public class HungryState implements PolmonState {
    @Override
    public void onEnter(Monster ctx) {
        System.out.println("State: HUNGRY (Lapar Berat!)");
    }

    @Override
    public void onTick(Monster ctx) {
        // Kalau lapar dibiarkan, HP berkurang
        ctx.modifyHealth(-5);
    }

    @Override
    public void feed(Monster ctx) {
        System.out.println("Nyam nyam! Akhirnya makan.");
        ctx.modifyHunger(-30); // Makan mengurangi lapar banyak
        ctx.modifyEnergy(5);

        // Cek transisi balik ke Normal ada di Monster.updateLogic()
    }

    @Override
    public void play(Monster ctx) {
        System.out.println("Gak mau main! Aku lapar!");
        ctx.modifyHappiness(-5); // Maksa main pas lapar bikin sedih
    }

    @Override
    public void sleep(Monster ctx) {
        System.out.println("Perut keroncongan, susah tidur...");
        ctx.setState(new SleepState());
    }

    @Override
    public void wakeUp(Monster ctx) { System.out.println("Masih lapar..."); }
}