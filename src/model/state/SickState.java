package model.state;

import model.Monster;

public class SickState implements PolmonState {
    @Override
    public void onEnter(Monster ctx) {
        System.out.println("State: Sakit");
    }

    @Override
    public void onTick(Monster ctx) {
        // Darah turun drastis saat sakit
        ctx.modifyHealth(-5);
        ctx.modifyHappiness(-2);
    }

    @Override
    public void feed(Monster ctx) {
        System.out.println("Tidak nafsu makan...");
        // Tetap bisa makan sedikit tapi efeknya kecil
        ctx.modifyHunger(-5);
    }

    @Override
    public void play(Monster ctx) {
        System.out.println("Terlalu lemah...");
        ctx.modifyHealth(-5); // Main saat sakit malah mengurangi darah
    }

    @Override
    public void sleep(Monster ctx) {
        System.out.println("Istirahat menyembuhkan sakit.");
        ctx.setState(new SleepState());
    }

    @Override
    public void wakeUp(Monster ctx) {
        System.out.println("Masih sakit...");
    }
}