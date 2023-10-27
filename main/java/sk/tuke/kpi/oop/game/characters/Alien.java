package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;
import java.util.Objects;

public class Alien extends AbstractActor implements Movable, Enemy, Alive {
    private Health health;
    private Behaviour<? super Alien> behaviour;

    public Alien() {
        this.health = new Health(150, 150);
        Animation alienAnimation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(alienAnimation);
        exhaustionEffect();
    }

    public Alien(int healthValue, Behaviour<? super Alien> behaviour) {
        this.health = new Health(healthValue);
        Animation alienAnimation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(alienAnimation);
        this.behaviour = behaviour;
        exhaustionEffect();
    }

    public void exhaustionEffect() {
        health.onExhaustion(new Health.ExhaustionEffect() {
            @Override
            public void apply() {
                alienDie();
            }
        });
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public void startedMoving(Direction direction) {
        Movable.super.startedMoving(direction);
        getAnimation().setRotation(direction.getAngle());
        getAnimation().setFrameDuration(0.1f);
    }

    @Override
    public void stoppedMoving() {
        Movable.super.stoppedMoving();
        getAnimation().setFrameDuration(0);
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    public void drain(int amount) {
        getHealth().drain(amount);
        if(getHealth().getValue() == 0) {
            alienDie();
        }
    }

    public void alienDie() {
        Objects.requireNonNull(getScene()).removeActor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(this.behaviour != null) this.behaviour.setUp(this);
        drainLive();
        /*
        new Loop<>(new ActionSequence<>(
            new Invoke<>(this::searchForAlive),
            new Wait<>(0.4f)
        )).scheduleFor(this);*/
    }

    private void drainLive() {
        new Loop<>(new Invoke<>(this::searchForAlive)).scheduleFor(this);
    }

    public void searchForAlive() {
        if(getScene() == null) return;
        List<Actor> actors = getScene().getActors();
        for(Actor actor : actors) {
            if(actor instanceof Alive && !(actor instanceof Enemy) && this.intersects(actor)) {
                ((Alive) actor).getHealth().drain(1);
            }
        }
    }
}
