package model.state;

import model.Monster;

public class NormalState implements PolmonState {
    @Override
    public void onEnter(Monster ctx) {
        System.out.println("State: Normal");
    }

    @Override
    public void onTick(Monster ctx) {
        ctx.modifyHunger(1);
        ctx.modifyHappiness(-1);
    }

    @Override
    public void feed(Monster ctx) {
        ctx.modifyHunger(-20);
        ctx.modifyHealth(5);
    }

    @Override
    public void play(Monster ctx) {
        if (ctx.getEnergy() < 20) {
            System.out.println("Terlalu lelah untuk bermain");
            return;
        }
        ctx.modifyHappiness(15);
        ctx.modifyHunger(10);
        ctx.modifyEnergy(-20);
    }

    @Override
    public void sleep(Monster ctx) {
        ctx.setState(new SleepState());
    }

    @Override
    public void wakeUp(Monster ctx) {
        System.out.println("Sudah bangun");
    }
}