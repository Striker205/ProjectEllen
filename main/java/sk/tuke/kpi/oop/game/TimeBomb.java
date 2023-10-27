package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class TimeBomb extends AbstractActor {
    private float timeDetonation;
    private Animation activeBomb;
    private Animation explosionBomb;
    private boolean activation;

    public TimeBomb(float time) {
        this.timeDetonation = time;
        Animation bomb = new Animation("sprites/bomb.png");
        this.explosionBomb = new Animation("sprites/small_explosion.png", 16,16,0.2f, Animation.PlayMode.ONCE);
        this.activeBomb = new Animation("sprites/bomb_activated.png", 16,16,0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(bomb);
        this.activation = false;
    }

    public void activate() {
        this.activation = true;
        setAnimation(activeBomb);

        new ActionSequence<>(
            new Wait<>(this.timeDetonation),
            new Invoke<>(this::explosion)
        ).scheduleFor(this);

        new When<>(
            () -> (getAnimation().getCurrentFrameIndex() == (getAnimation().getFrameCount()-1) && getAnimation().getResource().equals("sprites/small_explosion.png")),
            new Invoke<>(() -> {
                Objects.requireNonNull(getScene()).removeActor(this);
            })
        ).scheduleFor(this);
    }

    public boolean isActivated() {
        return activation;
    }

    public void explosion() {
        setAnimation(explosionBomb);
    }
}
