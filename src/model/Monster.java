package model;

import java.io.Serializable;

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
}