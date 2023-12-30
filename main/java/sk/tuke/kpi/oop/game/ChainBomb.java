package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

import java.awt.geom.*;
import java.util.List;
import java.util.Objects;



public class ChainBomb extends TimeBomb {
    private Rectangle2D rectangle2D;

    public ChainBomb(float time) {
        super(time);
    }

    public void explosion() {
        super.explosion();
        chainBomb();
    }

    private void chainBomb() {
        List<Actor> actors = getActor();

        for (Actor actor : actors) {
            if ((actor.getName().equals("ChainBomb")) && (!((ChainBomb) actor).isActivated())) {
                ((ChainBomb)actor).rectangle2D = getRectangle(actor);

                if(getEllipse().intersects(((ChainBomb)actor).rectangle2D)) {
                    ((ChainBomb) actor).activate();
                }
            }
        }
    }

    private Ellipse2D getEllipse() {
        return new Ellipse2D.Float((this.getPosX()-50), (this.getPosY()-50), 102, 102);
    }
    private Rectangle2D getRectangle(Actor actor) {
        return new Rectangle2D.Float((actor.getPosX()-8), (actor.getPosY()-8), 16, 16);
    }
    private List<Actor> getActor() {
        return Objects.requireNonNull(getScene()).getActors();
    }
}
