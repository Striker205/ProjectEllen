package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler {

    public SmartCooler(Reactor reactor) {
        super(reactor);
    }

    private void smartCoolReactor() {
        Reactor reactor = getReactor();
        if(reactor == null) return;

        if(reactor.getTemperature() >= 2500) {
            turnOn();
        }else if(reactor.getTemperature() <= 1500){
            turnOff();
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::smartCoolReactor)).scheduleFor(this);
    }
}
