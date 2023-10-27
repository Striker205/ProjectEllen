package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.Objects;

public class Ventilator extends AbstractActor implements Repairable {
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);

    public Ventilator() {
        Animation ventilatorAnimation = new Animation("sprites/ventilator.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(ventilatorAnimation);
        getAnimation().stop();
    }

    @Override
    public boolean repair() {
        getAnimation().play();
        Objects.requireNonNull(getScene()).getMessageBus().publish(VENTILATOR_REPAIRED, this);
        return true;
    }
}
