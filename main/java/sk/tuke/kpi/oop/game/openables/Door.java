package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private Animation closeAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
    private Animation openAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);;
    private boolean isOpen;
    private MapTile mapTileBottom;
    private MapTile mapTileTop;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
    private Orientation orientation;

    public Door() {
        setAnimation(closeAnimation);
        this.isOpen = false;
        this.mapTileBottom = null;
        this.mapTileTop = null;
    }

    public Door(String name, Orientation orientation) {
        super(name);
        this.orientation = orientation;
        if (orientation.equals(Orientation.HORIZONTAL)) {
            this.closeAnimation = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            this.openAnimation = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE);
            setAnimation(closeAnimation);
        } else {
            setAnimation(closeAnimation);
        }
        this.isOpen = false;
        this.mapTileBottom = null;
        this.mapTileTop = null;
    }

    public void doorCollision() {
        mapTileBottom = Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16);
        if(orientation.equals(Orientation.VERTICAL)) {
            mapTileTop = getScene().getMap().getTile(this.getPosX() / 16, (this.getPosY() + 16) / 16);
        } else {
            mapTileTop = getScene().getMap().getTile((this.getPosX() + 16) / 16, this.getPosY() / 16);
        }
    }

    public void doorState() {
        doorCollision();
        if(mapTileBottom != null && mapTileTop != null) {
            if (!mapTileBottom.isWall() && !mapTileTop.isWall() && !isOpen()) {
                mapTileBottom.setType(MapTile.Type.WALL);
                mapTileTop.setType(MapTile.Type.WALL);
            } else {
                mapTileBottom.setType(MapTile.Type.CLEAR);
                mapTileTop.setType(MapTile.Type.CLEAR);
            }
        }
    }

    @Override
    public void open() {
        if(isOpen()) return;
        setAnimation(openAnimation);
        getAnimation().resetToFirstFrame();
        this.isOpen = true;
        doorState();
        Objects.requireNonNull(getScene()).getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        if(!isOpen()) return;
        setAnimation(closeAnimation);
        getAnimation().resetToFirstFrame();
        this.isOpen = false;
        doorState();
        Objects.requireNonNull(getScene()).getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public void useWith(Actor actor) {
        if(actor == null) return;
        if(!isOpen()) {
            this.open();
        }else {
            close();
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        doorState();
    }
}
