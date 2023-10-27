package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class AccessCard extends AbstractActor implements Collectible, Usable<LockedDoor> {

    public AccessCard() {
        Animation keyAnimation = new Animation("sprites/key.png");
        setAnimation(keyAnimation);
    }

    @Override
    public void useWith(LockedDoor actor) {
        if(actor == null) return;
        actor.unlock();
    }

    @Override
    public Class<LockedDoor> getUsingActorClass() {
        return LockedDoor.class;
    }
}

