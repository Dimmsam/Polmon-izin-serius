package model.state;

import model.Monster;

public class HungryState implements PolmonState {
    @Override
    public void onEnter(Monster ctx) {
        System.out.println("State: Lapar");
    }

    @Override
    public void onTick(Monster ctx) {
        ctx.modifyHealth(-3);
        ctx.modifyHappiness(-2);
    }

    @Override
    public void feed(Monster ctx) {
        ctx.modifyHunger(-30);
        ctx.modifyHealth(10);

        if (ctx.getHunger() < 50) {
            ctx.setState(new NormalState());
        }
    }

    @Override
    public void play(Monster ctx) {
        System.out.println("Terlalu lapar untuk bermain");
    }

    @Override
    public void sleep(Monster ctx) {
        ctx.setState(new SleepState());
    }

    @Override
    public void wakeUp(Monster ctx) {
        System.out.println("Masih lapar");
    }
}