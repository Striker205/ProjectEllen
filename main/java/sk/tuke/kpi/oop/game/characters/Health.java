package sk.tuke.kpi.oop.game.characters;


import java.util.ArrayList;
import java.util.List;

public class Health {
    private List<ExhaustionEffect> effectList;
    private int maxHealth;
    private int initHealth;

    public Health(int initHealth, int maxHealth) {
        this.maxHealth = maxHealth;
        this.initHealth = initHealth;
        this.effectList = new ArrayList<>();
    }

    public Health(int initHealth) {
        this.maxHealth = initHealth;
        this.initHealth = initHealth;
    }

    public int getValue() {
        return this.initHealth;
    }

    public void refill(int amount) {
        if(this.maxHealth > (getValue()+amount)) {
            this.initHealth += amount;
        }else {
            restore();
        }
    }

    public void restore() {
        this.initHealth = maxHealth;
    }

    public void drain(int amount) {
        if(0 < (getValue()-amount)) {
            this.initHealth -= amount;
        }else {
            exhaust();
        }
    }

    public void exhaust() {
        if(getValue() == 0) return;
        this.initHealth = 0;
        if(effectList != null) {
            for(ExhaustionEffect effects : effectList) {
                effects.apply();
            }
        }
    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

    public void onExhaustion(ExhaustionEffect effect) {
        if(effectList == null) return;
        effectList.add(effect);
    }
}
