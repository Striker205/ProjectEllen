package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Movable movable;
    private Move<Movable> move;
    private Set<Direction> directionSet;
    private Set<Input.Key> keySet;

    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );

    public MovableController(Movable movable) {
        this.movable = movable;
        this.directionSet = new HashSet<>();
        this.keySet = new HashSet<>();
    }

    private void playerMove() {
        Direction direction = null;

        for(Input.Key key : keySet) {
            direction = keyDirectionMap.get(key);
            directionSet.add(direction);
        }

        for(Direction dir : directionSet){
            direction = direction.combine(dir);
        }

        if(move != null){
            move.stop();
        }

        if(direction != null) {
            move = new Move<>(direction, Float.MAX_VALUE);
            move.scheduleFor(movable);
        }
    }

    @Override
    public void keyPressed(Input.Key key) {
        if(key == null) return;
        if(keyDirectionMap.containsKey(key)) {
            keySet.add(key);
            playerMove();
        }
    }

    @Override
    public void keyReleased(Input.Key key) {
        if(key == null) return;
        keySet.remove(key);
        directionSet.remove(keyDirectionMap.get(key));
        if(keyDirectionMap.containsKey(key)) {
            move.stop();
            playerMove();
        }
    }
}
