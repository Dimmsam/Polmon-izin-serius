package model.state;

import model.Monster;

public class DeadState implements PolmonState {
    @Override
    public void onEnter(Monster ctx) {
        System.out.println("State: Mati");
    }

    @Override
    public void onTick(Monster ctx) {
        // Tidak ada efek, sudah mati
    }

    @Override
    public void feed(Monster ctx) {
        System.out.println("RIP.");
    }

    @Override
    public void play(Monster ctx) {
        System.out.println("RIP.");
    }

    @Override
    public void sleep(Monster ctx) {
        System.out.println("RIP.");
    }

    @Override
    public void wakeUp(Monster ctx) {
        System.out.println("RIP.");
    }
}