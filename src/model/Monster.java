package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class Monster implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ID;
    private String name;
    private String birthday;
    private int hp;
    private int maxHP;
    private int minDamage;
    private int maxDamage;
    private int happiness;
    private long lastFedTimestamp;
    private long lastCareTimestamp;

    public Monster(int ID, String birthday, String name,
                   long lastFedTimestamp, long lastCareTimestamp,
                   int hp, int maxHP, int minDamage, int maxDamage,
                   int happiness) {
        this.ID = ID;
        this.birthday = birthday;
        this.name = name;
        this.hp = hp;
        this.maxHP = maxHP;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.happiness = happiness;
        this.lastFedTimestamp = lastFedTimestamp;
        this.lastCareTimestamp = lastCareTimestamp;
    }

    public int getID() { return ID; }
    public String getName() { return name; }
    public String getBirthday() { return birthday; }
    public int getHP() { return hp; }
    public int getMaxHP() { return maxHP; }
    public int getMinDamage() { return minDamage; }
    public int getMaxDamage() { return maxDamage; }
    public int getHappiness() { return happiness; }
    public long getLastFedTimestamp() { return lastFedTimestamp; }
    public long getLastCareTimestamp() { return lastCareTimestamp; }

    public int getHoursSinceLastFed() {
        long now = new Date().getTime();
        long diff = now - lastFedTimestamp;
        return (int)(diff / 1000 / 60 / 60);
    }

    public int getHoursSinceLastCared() {
        long now = new Date().getTime();
        long diff = now - lastCareTimestamp;
        return (int)(diff / 1000 / 60 / 60);
    }

    public int getDaysOld() {
        try {
            long birthdayMillis = new SimpleDateFormat("MM/dd/yyyy").parse(this.birthday).getTime();
            long now = new Date().getTime();
            long diff = now - birthdayMillis;
            return (int)(diff / 1000 / 60 / 60 / 24);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void starve() {
        hp--;
        if (hp < 0) hp = 0;
    }

    public void updateHP(int newHP) {
        this.hp = newHP;
        if (hp > maxHP) hp = maxHP;
        if (hp < 0) hp = 0;
    }

    @Override
    public String toString() {
        return name + " (HP:" + hp + "/" + maxHP + ", Happiness:" + happiness + ")";
    }
}