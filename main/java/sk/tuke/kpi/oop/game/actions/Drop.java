package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop<keeper extends Keeper> extends AbstractAction<keeper> {

    @Override
    public void execute(float deltaTime) {
        if(getActor() == null || getActor().getBackpack().peek() == null || getActor().getScene() == null){
            setDone(true);
            return;
        }

        if(!isDone()) {
            try {
                Collectible item = getActor().getBackpack().peek();
                if(item != null) {
                    getActor().getScene().addActor(item, getActor().getPosX() + (getActor().getWidth() / 2) - item.getWidth() / 2, getActor().getPosY() + (getActor().getHeight() / 2) - item.getHeight() / 2);
                    getActor().getBackpack().remove(item);
                }
            }catch (Exception exception) {
                getActor().getScene().getGame().getOverlay().drawText(exception.getMessage(), 0,40).showFor(2);
            }
            setDone(true);
        }
    }
}
