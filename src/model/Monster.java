package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import model.state.*;

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
    private int energy;
    private long lastFedTimestamp;
    private long lastCareTimestamp;
    private int hunger; // 0-100
    private int ageSeconds;
    private EvolutionStage stage;
    private SpeciesType species;

    private PolmonState currentState;


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
        this.hunger = 0;
        this.energy = 100;
        this.lastFedTimestamp = lastFedTimestamp;
        this.lastCareTimestamp = lastCareTimestamp;
        this.hunger = 0;
        this.ageSeconds = 0;
        this.stage = EvolutionStage.EGG;
        this.species = SpeciesType.fromId(ID);
        this.currentState = new NormalState();
        this.name = MonsterDatabase.getName(this.species, this.stage);
    }

    // --- New Modifiers ---
    public void modifyHealth(int val) {
        this.hp = Math.max(0, Math.min(maxHP, this.hp + val));
    }
    public void modifyEnergy(int val) {
        this.energy = Math.max(0, Math.min(100, this.energy + val));
    }
    public void modifyHunger(int val) {
        this.hunger = Math.max(0, Math.min(100, this.hunger + val));
    }
    public void modifyHappiness(int val) {
        this.happiness = Math.max(0, Math.min(100, this.happiness + val));
    }
    public void setStage(EvolutionStage stage) { this.stage = stage;}

    public void setState(PolmonState state) {
        this.currentState = state;
        this.currentState.onEnter(this);
    }
    public void feed() { currentState.feed(this);}
    public void play() { currentState.play(this);}
    public void sleep() { currentState.sleep(this);}
    public void wakeUp() { currentState.wakeUp(this);}

    public void updateLogic() {
        if (currentState instanceof DeadState) return;

        addAgeSeconds(1);
        if (currentState != null) currentState.onTick(this);

        // --- Logika Degradasi Pasif ---
        if (stage != EvolutionStage.EGG) {
            if (!(currentState instanceof SleepState)) {
                modifyHunger(2);       // Lapar nambah
                modifyHappiness(-1);   // Bahagia turun (lama2 bosan)
                modifyEnergy(-1);      // Energy turun (lama2 ngantuk)
            }
        }

        // --- Cek Transisi Status Otomatis ---
        if (hp <= 0) {
            setState(new DeadState());
        }
        else if (hunger >= 80 && !(currentState instanceof HungryState)) {
            // Jika lapar > 80, otomatis jadi Sakit
            setState(new HungryState());
        }

        // --- Cek Evolusi ---
        if (EvolutionManager.canEvolve(this)) {
            EvolutionManager.evolve(this);
            this.name = MonsterDatabase.getName(this.species, this.stage);
            this.hp = maxHP;
            this.happiness = 100;
            this.energy = 100;
        }
    }
    // Method baru untuk mengatur prioritas State
    private void checkStatusTransitions() {
        // 1. Prioritas Tertinggi: MATI
        if (hp <= 0) {
            if (!(currentState instanceof DeadState)) setState(new DeadState());
            return;
        }

        // 2. Jika sedang Tidur, jangan diganggu status lain (kecuali bangun manual)
        if (currentState instanceof SleepState) {
            // Opsional: Bangun otomatis jika energy penuh
            if (energy >= 100) {
                System.out.println("Energi penuh! Bangun otomatis.");
                setState(new NormalState());
            }
            return;
        }

        // 3. Cek Status Buruk (Prioritas: Lapar > Bosan > Ngantuk)
        if (hunger >= 80) {
            if (!(currentState instanceof HungryState)) setState(new HungryState());
        }
        else if (happiness <= 20) {
            if (!(currentState instanceof BoredState)) setState(new BoredState());
        }
        else if (energy <= 10) {
            // Otomatis ketiduran kalau energi habis banget
            System.out.println("Pingsan karena kelelahan...");
            setState(new SleepState());
        }
        // 4. Jika semua aman, kembali ke Normal
        else {
            if (!(currentState instanceof NormalState)) setState(new NormalState());
        }
    }

    public String getStateName() {
        return (currentState != null) ? currentState.getClass().getSimpleName() : "None";
    }

    public int getHunger() { return hunger; }
    public int getAgeSeconds() { return ageSeconds; }
    public EvolutionStage getStage() { return stage; }
    public SpeciesType getSpecies() { return species; }
    public void addAgeSeconds(int sec) { this.ageSeconds += sec; }
    public void setName(String name) { this.name = name; }
    public void setHp(int hp) { this.hp = hp; }
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
    public int getEnergy() { return energy; }


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