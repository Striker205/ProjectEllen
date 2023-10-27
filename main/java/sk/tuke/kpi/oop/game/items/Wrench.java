package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench extends BreakableTool<DefectiveLight> implements Collectible{

    public Wrench() {
        this(2);
    }

    public Wrench(int uses) {
        super(uses);
        Animation normalAnimation = new Animation("sprites/wrench.png");
        setAnimation(normalAnimation);
    }

    @Override
    public void useWith(DefectiveLight actor) {
        if(actor == null) return;
        if(actor.repair()) super.useWith(actor);
    }

    @Override
    public Class<DefectiveLight> getUsingActorClass() {
        return DefectiveLight.class;
    }
}
