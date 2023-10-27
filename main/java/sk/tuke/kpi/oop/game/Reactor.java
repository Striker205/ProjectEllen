package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable {
    private Animation normalAnimation;
    private Animation extinguishAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation offAnimation;
    private int temperature;
    private int damage;
    private boolean condition;
    private boolean extinguish;
    private EnergyConsumer device;
    private Set<EnergyConsumer> devices;

    public Reactor() {
        this.temperature = 0;
        this.damage = 0;
        this.condition = false;
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.extinguishAnimation = new Animation("sprites/reactor_extinguished.png");
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.2f - ((getDamage() * 1.5f) / 1000), Animation.PlayMode.LOOP_PINGPONG);
        this.offAnimation = new Animation("sprites/reactor.png");
        setAnimation(offAnimation);
        extinguish = false;
        this.devices = new HashSet<>();
    }

    public void addDevice(EnergyConsumer device) {
        if(device == null) return;
        this.device = device;
        if(isOn()) this.device.setPowered(true);
        this.devices.add(device);
    }
    public void removeDevice(EnergyConsumer device) {
        if(device == null) return;
        this.devices.remove(device);
        this.device.setPowered(false);
    }

    public int getDamage() {
        return damage;
    }
    public int getTemperature() {
        return temperature;
    }
    public boolean isOn() {
        return this.condition;
    }

    public void turnOn() {
        if(getDamage() == 100) return;
        this.condition = true;
        updateAnimation();
    }
    public void turnOff() {
        this.condition = false;
        updateAnimation();
    }

    private void updateAnimation() {

        if(isOn()) {
            if (getTemperature() > 4000 && getTemperature() < 6000) {
                setAnimation(hotAnimation);
                hotAnimation.setFrameDuration(0.2f - ((getDamage() * 1.5f) / 1000));
            }else {
                setAnimation(normalAnimation);
            }

            for(EnergyConsumer device : devices){
                device.setPowered(true);
            }
        }else {
            if(!extinguish) {
                if (getTemperature() >= 6000) {
                    setAnimation(brokenAnimation);
                } else {
                    setAnimation(offAnimation);
                }
            }else {
                setAnimation(extinguishAnimation);
            }

            for(EnergyConsumer device : devices){
                device.setPowered(false);
            }
        }
    }

    public void increaseTemperature(int increament) {
        if(increament < 0) return;

        if(isOn()) {

            float mul = 1.0f;

            if ((getDamage() >= 33) && (66 >= getDamage())) mul = 1.5f;
            else if (getDamage() > 66) mul = 2.0f;

            if(getDamage() != 100) this.temperature += Math.ceil(increament * mul);

            if (getTemperature() >= 6000) {
                this.damage = 100;
                turnOff();
            }else if(getTemperature() > 2000) {
                this.damage = (int) Math.floor((0.025f * (getTemperature() - 2000)));
            }

            updateAnimation();
        }
    }

    public void decreaseTemperature(int decrement) {
        if(decrement < 0) return;

        if((getDamage() != 100) && (isOn()) && (getTemperature() >= 0)) {
            if (getDamage() >= 50) {
                this.temperature -= decrement * 0.5f;
            } else {
                this.temperature -= decrement;
            }
        }

        if(getTemperature() < 0) this.temperature = 0;

        updateAnimation();
    }

    public boolean repair() {
        if(isOn() && getDamage() != 100) {
            this.damage = Math.max(0, (this.damage -= 50));
            int num = (getDamage() * 40) + 2000;
            this.temperature = num;
            updateAnimation();

            return true;
        }
        return false;
    }

    public boolean extinguish() {
        if(getDamage() == 100) {
            this.temperature = Math.max(0, (this.temperature -= 4000));
            setAnimation(extinguishAnimation);
            this.extinguish = true;

            return true;
        }
        return false;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }
}
