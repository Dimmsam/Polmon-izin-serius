package model.state;

import model.Monster;

public class NormalState implements PolmonState {
    @Override
    public void onEnter(Monster ctx) {
        System.out.println("State: Normal");
    }

    @Override
    public void onTick(Monster ctx) {
        // Lapar bertambah, happiness berkurang seiring waktu
        ctx.modifyHunger(2);
        ctx.modifyHappiness(-1);
    }

    @Override
    public void feed(Monster ctx) {
        System.out.println("Nyam nyam!");
        ctx.modifyHunger(-20);
        ctx.modifyHealth(5);
    }

    @Override
    public void play(Monster ctx) {
        System.out.println("Main bola!");
        ctx.modifyHappiness(15);
        ctx.modifyHunger(10);
    }

    @Override
    public void sleep(Monster ctx) {
        ctx.setState(new SleepState());
    }

    @Override
    public void wakeUp(Monster ctx) {
        System.out.println("Sudah bangun kok.");
    }
}