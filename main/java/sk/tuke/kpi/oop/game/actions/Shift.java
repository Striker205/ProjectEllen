package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Shift<keeper extends Keeper> extends AbstractAction<keeper> {

    @Override
    public void execute(float deltaTime) {
        if(getActor() == null || getActor().getScene() == null) {
            setDone(true);
            return;
        }
        if(!isDone()) {
            try {
                getActor().getBackpack().shift();
            }catch (Exception exception) {
                getActor().getScene().getGame().getOverlay().drawText(exception.getMessage(), 0,40).showFor(2);
            }
            setDone(true);
        }
    }
}
