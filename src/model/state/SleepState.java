package model.state;

import model.Monster;

public class SleepState implements PolmonState {
    @Override
    public void onEnter(Monster ctx) {
        System.out.println("State: Tidur");
    }

    @Override
    public void onTick(Monster ctx) {
        ctx.modifyHealth(5);
        ctx.modifyEnergy(10); // [UPDATE] Tidur memulihkan Energy dengan cepat
    }

    @Override
    public void feed(Monster ctx) {
        System.out.println("Zzz... (Tidur)");
    }

    @Override
    public void play(Monster ctx) {
        System.out.println("Zzz... (Tidur)");
    }

    @Override
    public void sleep(Monster ctx) {
        System.out.println("Sudah tidur.");
    }

    @Override
    public void wakeUp(Monster ctx) {
        System.out.println("Bangun!");
        ctx.setState(new NormalState());
    }


}