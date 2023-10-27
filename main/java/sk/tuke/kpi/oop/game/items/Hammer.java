package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> implements Collectible {
    public Hammer(){
        this(1);
    }

    public Hammer(int numUses) {
        super(numUses);
        Animation hammerAnimation = new Animation("sprites/hammer.png");
        setAnimation(hammerAnimation);
    }

    public void useWith(Repairable actor) {
        if(actor == null) return;
        if(actor.repair()) super.useWith(actor);
    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
}
