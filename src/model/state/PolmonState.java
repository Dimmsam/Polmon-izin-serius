package model.state;

import model.Monster;
import java.io.Serializable;

public interface PolmonState extends Serializable {
    void onEnter(Monster ctx);
    void onTick(Monster ctx);
    void feed(Monster ctx);
    void play(Monster ctx);
    void sleep(Monster ctx);
    void wakeUp(Monster ctx);
}