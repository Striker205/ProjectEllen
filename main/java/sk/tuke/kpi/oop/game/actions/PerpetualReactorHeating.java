package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {
    private int temperatureIncreases;

    public PerpetualReactorHeating(int temperatureIncreases) {
        this.temperatureIncreases = temperatureIncreases;
    }

    @Override
    public void execute(float deltaTime) {
        Reactor reactor = getActor();
        if(reactor != null) {
            reactor.increaseTemperature(this.temperatureIncreases);
        }
    }
}
