package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible{
    public FireExtinguisher() {
        this(1);
    }
    public FireExtinguisher(int numUses) {
        super(numUses);
        Animation normalAnimation = new Animation("sprites/extinguisher.png");
        setAnimation(normalAnimation);
    }
    @Override
    public void useWith(Reactor actor) {
        if(actor == null) return;
        if(actor.extinguish()) super.useWith(actor);
    }

    @Override
    public Class<Reactor> getUsingActorClass() {
        return Reactor.class;
    }
}
