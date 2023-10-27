package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

public class Teleport extends AbstractActor {
    private Teleport destinationTeleport;
    private boolean isTeleporteed;
    public Teleport(Teleport teleport){
        setDestination(teleport);
        Animation normalAnimation = new Animation("sprites/lift.png");
        setAnimation(normalAnimation);
        this.isTeleporteed = false;
    }

    private Ellipse2D getEllipsePlayer(Player player) {
        return new Ellipse2D.Float((player.getPosX()-8),(player.getPosY()-8),1,1);
    }

    private Rectangle2D getRectangleTeleport() {
        return new Rectangle2D.Float((this.getPosX()-24),(this.getPosY()-24),48,48);
    }
    private Player getPlayer() {
        return Objects.requireNonNull(getScene()).getFirstActorByType(Player.class);
    }


    public void teleportPlayer(Player player) {
        player.setPosition((this.getPosition().getX()+8),(this.getPosition().getY())+8);
        this.isTeleporteed = true;
    }

    public void searchForTeleport() {
        if(getDestination() == null) return;

        if(onTeleport() && !isTeleporteed) {
            getDestination().teleportPlayer(getPlayer());
        }
    }

    public void setDestination(Teleport destinationTeleport) {
        if(destinationTeleport == null) return;
        if(this != destinationTeleport) this.destinationTeleport = destinationTeleport;
    }

    public Teleport getDestination() {
        return this.destinationTeleport;
    }

    public boolean onTeleport() {
        if(getEllipsePlayer(getPlayer()).intersects(this.getRectangleTeleport())){
            return true;
        }
        this.isTeleporteed = false;
        return false;
    }

    public boolean teleported() {
        return onTeleport() || getDestination().isTeleporteed;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::searchForTeleport)).scheduleFor(this);
    }
}
