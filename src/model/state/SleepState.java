package model.state;

import model.Monster;

public class SleepState implements PolmonState {
    @Override
    public void onEnter(Monster ctx) {
        System.out.println("State: Tidur");
    }

    @Override
    public void onTick(Monster ctx) {
        ctx.modifyHealth(3);
        ctx.modifyEnergy(10);
        ctx.modifyHappiness(1);
    }

    @Override
    public void feed(Monster ctx) {
        System.out.println("Zzz...");
    }

    @Override
    public void play(Monster ctx) {
        System.out.println("Zzz...");
    }

    @Override
    public void sleep(Monster ctx) {
        System.out.println("Sudah tidur");
    }

    @Override
    public void wakeUp(Monster ctx) {
        System.out.println("Bangun!");

        if (ctx.getHunger() >= 80) {
            ctx.setState(new HungryState());
        } else if (ctx.getHappiness() <= 20) {
            ctx.setState(new BoredState());
        } else {
            ctx.setState(new NormalState());
        }
    }
}