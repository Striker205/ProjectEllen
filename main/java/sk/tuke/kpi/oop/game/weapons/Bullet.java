package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;

import java.util.List;

public class Bullet extends AbstractActor implements Fireable {

    public Bullet() {
        Animation bulletAnimation = new Animation("sprites/bullet.png", 16 ,16);
        setAnimation(bulletAnimation);
    }

    @Override
    public int getSpeed() {
        return 4;
    }

    @Override
    public void startedMoving(Direction direction) {
        if(direction == null) return;
        getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::fireBullet)).scheduleFor(this);
    }

    public void fireBullet() {
        if(getScene() == null) return;
        List<Actor> actorsList = getScene().getActors();

        for(Actor actor : actorsList) {
            if(this.intersects(actor) && actor instanceof Alive) {
                ((Alive) actor).getHealth().drain(50);
                getScene().removeActor(this);
            }
        }
    }
    public void collidedWithWall() {
        if(getScene() == null) return;
        getScene().removeActor(this);
    }

}
