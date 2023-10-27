package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.List;
import java.util.Objects;

public class KeeperController implements KeyboardListener {

    private Keeper keeper;

    public KeeperController(Keeper actor) {
        this.keeper = actor;
    }


    @Override
    public void keyPressed(Input.Key key) {
        if(key == null) return;
        if(key.equals(Input.Key.ENTER)) {
            new Take<>().scheduleFor(keeper);
        }
        if(key.equals(Input.Key.BACKSPACE)) {
            new Drop<>().scheduleFor(keeper);
        }
        if(key.equals(Input.Key.S)) {
            new Shift<>().scheduleFor(keeper);
        }
        if(key.equals(Input.Key.B)) {
            backpackUsableObject();
        }
        if(key.equals(Input.Key.U)) {
            usableObject();
        }
    }


    public void backpackUsableObject() {
        Collectible item = keeper.getBackpack().peek();
        if(item instanceof Usable) {
            new Use<>((Usable<?>) item).scheduleForIntersectingWith(keeper);
        }
    }

    public void usableObject() {
        List<Actor> actors = Objects.requireNonNull(keeper.getScene()).getActors();

        for(Actor usable : actors) {
            if(usable.intersects(keeper) && usable instanceof Usable) {
                new Use<>((Usable<?>) usable).scheduleForIntersectingWith(keeper);
            }
        }
    }
}
