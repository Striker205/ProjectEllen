package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.Objects;

public class Move<actor extends Movable> implements Action<actor> {

    private Direction direction;
    private float duration;
    private actor actor;
    private Boolean isDone;
    private boolean onlyOnce;

    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;
        this.isDone = false;
        this.onlyOnce = false;
    }

    public Move(Direction direction) {
        this.direction = direction;
        this.isDone = false;
    }

    @Override
    public @Nullable actor getActor() {
        return actor;
    }

    @Override
    public void setActor(@Nullable actor actor) {
        this.actor = actor;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void execute(float deltaTime) {
        if(getActor() == null) return;
        this.duration -= deltaTime;

        if(!isDone()) {
            moveOnce();

            if(this.duration >= 1e-5){
                getActor().setPosition(getActor().getPosX()+getActor().getSpeed()*this.direction.getDx(),getActor().getPosY()+getActor().getSpeed()*this.direction.getDy());
                collisionWithWall();
            }else{
                stop();
            }
        }
    }

    private void moveOnce() {
        if(getActor() != null && !this.onlyOnce) {
            getActor().startedMoving(this.direction);
            this.onlyOnce = true;
        }
    }

    private void collisionWithWall() {
        if(getActor() != null && Objects.requireNonNull(getActor().getScene()).getMap().intersectsWithWall(actor)) {
            getActor().setPosition(getActor().getPosX()-getActor().getSpeed()*this.direction.getDx(), getActor().getPosY()-getActor().getSpeed()*this.direction.getDy());
            this.actor.collidedWithWall();
        }
    }

    @Override
    public void reset() {
        this.duration = 0;
        this.isDone = false;
    }

    public void stop() {
        this.isDone = true;
        if(getActor() != null) getActor().stoppedMoving();
    }
}
