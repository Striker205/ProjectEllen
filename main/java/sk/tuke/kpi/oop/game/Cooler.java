package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;


public class Cooler extends AbstractActor implements Switchable {
    private Animation fanAnimation;
    private Reactor reactor;
    private boolean stat;

    public Cooler(Reactor reactor) {
        this.reactor = reactor;
        this.stat = false;
        this.fanAnimation = new Animation("sprites/fan.png", 32, 32, 0, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(fanAnimation);
    }
    public void turnOn() {
        this.stat = true;
        fanAnimation.setFrameDuration(0.2f);
    }
    public void turnOff() {
        this.stat = false;
        fanAnimation.setFrameDuration(0);
    }
    public boolean isOn() {
       return this.stat;
    }

    public Reactor getReactor() {
        return reactor;
    }

    private void coolReactor() {
        if(reactor == null) return;

        if(isOn()) {
            this.reactor.decreaseTemperature(1);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
