package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer {
    private Animation onLightAnimation;
    private Animation offLightAnimation;
    private boolean condition;
    private boolean powered;

    public Light() {
        this.condition = false;
        this.powered = false;
        this.onLightAnimation = new Animation("sprites/light_on.png");
        this.offLightAnimation = new Animation("sprites/light_off.png");
        setAnimation(offLightAnimation);
    }
    public void turnOn() {
        this.condition = true;
        update();
    }
    public void turnOff() {
        this.condition = false;
        update();
    }
    public boolean isOn() {
        return this.condition;
    }

    public void toggle() {
        if(isOn()) {
            turnOff();
        }else {
            turnOn();
        }
    }

    public void update() {
        if(this.powered){
            if(isOn()) setAnimation(onLightAnimation);
            else setAnimation(offLightAnimation);
        }else {
            setAnimation(offLightAnimation);
        }
    }

    public void setPowered(boolean stat) {
        this.powered = stat;
        update();
    }
}
