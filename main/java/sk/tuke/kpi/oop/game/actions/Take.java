package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.List;

public class Take<keeper extends Keeper> extends AbstractAction<keeper> {

    @Override
    public void execute(float deltaTime) {
        if(getActor() == null || getActor().getScene() == null) {
            setDone(true);
            return;
        }

        if(!isDone()) {
            List<Actor> actors = getActor().getScene().getActors();

            for(Actor actor : actors) {
                if(actor instanceof Collectible && getActor().intersects(actor)) {
                    try {
                        getActor().getBackpack().add((Collectible) actor);
                        getActor().getScene().removeActor(actor);
                    }catch (Exception exception) {
                        getActor().getScene().getGame().getOverlay().drawText(exception.getMessage(), 0, 40).showFor(2);
                    }
                }
                setDone(true);
            }
        }
    }
}
