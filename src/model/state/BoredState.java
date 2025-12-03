package model.state;

import model.Monster;

public class BoredState implements PolmonState {
    @Override
    public void onEnter(Monster ctx) {
        System.out.println("State: Bosan");
    }

    @Override
    public void onTick(Monster ctx) {
        ctx.modifyHappiness(-3);
        ctx.modifyHunger(1);
    }

    @Override
    public void feed(Monster ctx) {
        ctx.modifyHunger(-10);
        ctx.updateLastFedTimestamp();
        System.out.println("Makan tanpa semangat");
    }

    @Override
    public void play(Monster ctx) {
        if (ctx.getEnergy() < 20) {
            System.out.println("Terlalu lelah");
            return;
        }

        ctx.modifyHappiness(30);
        ctx.modifyEnergy(-20);

        if (ctx.getHappiness() > 50) {
            ctx.setState(new NormalState());
        }
    }

    @Override
    public void sleep(Monster ctx) {
        ctx.setState(new SleepState());
    }

    @Override
    public void wakeUp(Monster ctx) {
        System.out.println("Masih bosan");
    }
}