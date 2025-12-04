package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.state.*;
import utils.event.EventManager;
import utils.event.GameEvent;

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
    private int maxEnergy;
    private long lastFedTimestamp;
    private long lastCareTimestamp;
    private int hunger;
    private int ageSeconds;
    private EvolutionStage stage;
    private SpeciesType species;
    private PolmonState currentState;
    private transient EventManager<GameEvent<EvolutionStage>> evolutionEvents;
    private transient EventManager<GameEvent<PolmonState>> stateChangeEvents;
    private transient EventManager<GameEvent<Integer>> healthChangeEvents;

    public Monster(int ID, String birthday, String name,
                   long lastFedTimestamp, long lastCareTimestamp,
                   int hp, int maxHP, int minDamage, int maxDamage,
                   int happiness, int energy, int maxEnergy) {
        this.ID = ID;
        this.birthday = birthday;
        this.name = name;
        this.hp = hp;
        this.maxHP = maxHP;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.happiness = happiness;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.lastFedTimestamp = lastFedTimestamp;
        this.lastCareTimestamp = lastCareTimestamp;
        this.hunger = 0;
        this.ageSeconds = 0;
        this.stage = EvolutionStage.EGG;
        this.species = SpeciesType.fromId(ID);
        this.currentState = new NormalState();
        this.name = MonsterDatabase.getName(this.species, this.stage);
        this.evolutionEvents = new EventManager<>();
        this.stateChangeEvents = new EventManager<>();
        this.healthChangeEvents = new EventManager<>();
    }

    public void modifyHealth(int val) {
        int oldHP = this.hp;
        this.hp = Math.max(0, Math.min(maxHP, this.hp + val));

        if (healthChangeEvents != null && oldHP != this.hp) {
            healthChangeEvents.notifyListeners(new GameEvent<>("HP_CHANGE", this.hp));
        }
    }

    public void modifyHunger(int val) {
        this.hunger = Math.max(0, Math.min(100, this.hunger + val));
    }

    public void modifyHappiness(int val) {
        this.happiness = Math.max(0, Math.min(100, this.happiness + val));
    }

    public void modifyEnergy(int val) {
        this.energy = Math.max(0, Math.min(maxEnergy, this.energy + val));
    }

    public void setAgeSeconds(int ageSeconds) {
        this.ageSeconds = ageSeconds;
    }

    public void setStage(EvolutionStage stage) {
        this.stage = stage;

        if (evolutionEvents != null) {
            evolutionEvents.notifyListeners(new GameEvent<>("EVOLUTION", stage));
        }
    }

    public void setState(PolmonState state) {
        this.currentState = state;
        this.currentState.onEnter(this);

        if (stateChangeEvents != null) {
            stateChangeEvents.notifyListeners(new GameEvent<>("STATE_CHANGE", state));
        }
    }

    public void feed() {
        currentState.feed(this);
    }

    public void play() {
        currentState.play(this);
    }

    public void sleep() {
        currentState.sleep(this);
    }

    public void wakeUp() {
        currentState.wakeUp(this);
    }

    public boolean isEgg() {
        return stage == EvolutionStage.EGG;
    }

    public void updateLogic() {
        if (currentState instanceof DeadState) return;

        if (isEgg()) {
            addAgeSeconds(1);
            if (EvolutionManager.canEvolve(this)) {
                EvolutionManager.evolve(this);
            }
            return;
        }

        addAgeSeconds(1);

        if (currentState != null) {
            currentState.onTick(this);
        }

        // Check Dead first
        if (hp <= 0) {
            setState(new DeadState());
            return;  // ← TAMBAH RETURN
        }

        // Check Hungry
        if (hunger >= 80 && !(currentState instanceof HungryState)) {
            setState(new HungryState());
        }
        // Check Bored
        else if (happiness <= 20 && !(currentState instanceof BoredState)) {
            setState(new BoredState());
        }
        // Back to Normal
        else if (hunger < 50 && happiness > 30 &&
                !(currentState instanceof NormalState) &&
                !(currentState instanceof SleepState)) {
            setState(new NormalState());
        }

        // Check Evolution
        if (EvolutionManager.canEvolve(this)) {
            EvolutionManager.evolve(this);
        }
    }

    public String getStateName() {
        return (currentState != null) ? currentState.getClass().getSimpleName() : "None";
    }

    public int getHunger() { return hunger; }
    public int getEnergy() { return energy; }
    public int getMaxEnergy() { return maxEnergy; }
    public int getAgeSeconds() { return ageSeconds; }
    public EvolutionStage getStage() { return stage; }
    public SpeciesType getSpecies() { return species; }
    public void addAgeSeconds(int sec) { this.ageSeconds += sec; }
    public void setName(String name) { this.name = name; }
    public void setHp(int hp) { this.hp = hp; }
    public int getID() { return ID; }
    public String getName() { return name; }
    public String getBirthday() { return birthday; }
    public PolmonState getCurrentState() { return currentState;}
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

    public EventManager<GameEvent<EvolutionStage>> getEvolutionEvents() {
        if (evolutionEvents == null) {
            evolutionEvents = new EventManager<>();
        }
        return evolutionEvents;
    }

    public EventManager<GameEvent<PolmonState>> getStateChangeEvents() {
        if (stateChangeEvents == null) {
            stateChangeEvents = new EventManager<>();
        }
        return stateChangeEvents;
    }

    public EventManager<GameEvent<Integer>> getHealthChangeEvents() {
        if (healthChangeEvents == null) {
            healthChangeEvents = new EventManager<>();
        }
        return healthChangeEvents;
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

    public void updateLastFedTimestamp() {
        this.lastFedTimestamp = new Date().getTime();
    }

    public void updateLastCareTimestamp() {
        this.lastCareTimestamp = new Date().getTime();
    }

    @Override
    public String toString() {
        return name + " (HP:" + hp + "/" + maxHP +
                ", Energy:" + energy + "/" + maxEnergy +
                ", Happiness:" + happiness + ")";
    }
}