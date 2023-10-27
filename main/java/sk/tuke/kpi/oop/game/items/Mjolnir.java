package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.oop.game.Reactor;

public class Mjolnir extends Hammer{

    public Mjolnir() {
        super(4);
    }

    public void useWith(Reactor actor) {
        if(actor == null) return;
        if(actor.repair()) super.useWith(actor);
    }
}
