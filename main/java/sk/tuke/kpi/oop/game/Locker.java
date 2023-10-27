package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Locker extends AbstractActor implements Usable<Actor> {
    private boolean once;

    public Locker() {
        Animation lockerAnimation = new Animation("sprites/locker.png");
        setAnimation(lockerAnimation);
        this.once = false;
    }

    public boolean isUse() {
        return this.once;
    }

    @Override
    public void useWith(Actor actor) {
        if(actor == null) return;
        if(!isUse()){
            Hammer hammer = new Hammer();
            Objects.requireNonNull(getScene()).addActor(hammer, this.getPosX(), this.getPosY());
            Objects.requireNonNull(getScene()).removeActor(this);
            this.once = true;
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
