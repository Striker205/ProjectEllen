package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

public class Fire<armed extends Armed> extends AbstractAction<armed> {

    @Override
    public void execute(float deltaTime) {
        if(getActor() == null || getActor().getScene() == null) {
            setDone(true);
            return;
        }

        if(!isDone()){
            Fireable gun = getActor().getFirearm().fire();
            if(gun == null) {
                setDone(true);
                return;
            }

            int playerX = Direction.fromAngle(getActor().getAnimation().getRotation()).getDx();
            int playerY = Direction.fromAngle(getActor().getAnimation().getRotation()).getDy();

            int positionBulletX = getActor().getPosX() + 8 + (gun.getWidth()+4)*playerX;
            int positionBulletY = getActor().getPosY() + 8 + (gun.getHeight()+4)*playerY;

            getActor().getScene().addActor(gun, positionBulletX, positionBulletY);
            Move<Fireable> move = new Move<>(Direction.fromAngle(getActor().getAnimation().getRotation()), Float.MAX_VALUE);
            move.scheduleFor(gun);

            setDone(true);
        }
    }
}
