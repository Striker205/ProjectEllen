package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer{
    private boolean condition_this_end;

    public Computer() {
        this.condition_this_end = false;
        Animation normalAnimation = new Animation("sprites/computer.png",80,48,0f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
    }

    public void updateAnimation() {
        if(this.condition_this_end) getAnimation().setFrameDuration(0.1f);
        else getAnimation().setFrameDuration(0f);
    }

    public int add(int a, int b) {
        if(!this.condition_this_end) return 0;
        return a+b;
    }

    public float add(float a, float b) {
        if(!this.condition_this_end) return 0;
        return a+b;
    }

    public int sub(int a, int b) {
        if(!this.condition_this_end) return 0;
        return a-b;
    }

    public float sub(float a, float b) {
        if(!this.condition_this_end) return 0;
        return a-b;
    }

    @Override
    public void setPowered(boolean stat) {
        this.condition_this_end = stat;
        updateAnimation();
    }
}
