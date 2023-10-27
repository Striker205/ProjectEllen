package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Usable;

public class Use<A extends Actor> extends AbstractAction<A> {
    private Usable<A> usableActor;

    public Use(Usable<A> usableActor) {
        this.usableActor = usableActor;
    }

    @Override
    public void execute(float deltaTime) {
        if(!isDone()) usableActor.useWith(getActor());
        setDone(true);
    }

    public Disposable scheduleForIntersectingWith(Actor mediatingActor) {
        Scene scene = mediatingActor.getScene();
        if(scene == null) return null;
        Class<A> usingActorClass = usableActor.getUsingActorClass();

        for(Actor actor : scene) {
            if(mediatingActor.intersects(actor) && usingActorClass.isInstance(actor)) {
                return this.scheduleFor(usingActorClass.cast(actor));
            }
        }

        return null;
    }
}
