package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Objects;

public class RandomlyMoving implements Behaviour<Movable>{
    @Override
    public void setUp(Movable actor) {
        if(actor == null) return;
        Movable movable = Objects.requireNonNull(actor.getScene()).getLastActorByType(Movable.class);
        if(movable == null) return;
        new Loop<>(new ActionSequence<>(
            new Invoke<>(() -> characterMove(movable)),
            new Wait<>(0.5f)
        )).scheduleFor(movable);
    }

    private void characterMove(Movable movable) {
        int rnd = (int)(Math.random() * 5);
        Move<Movable> characterMove = new Move<>(directionCharacter(rnd), 1.0f);
        characterMove.setActor(movable);
        characterMove.scheduleFor(movable);
    }

    private Direction directionCharacter(int rnd) {
        switch (rnd){
            case 0: return Direction.EAST;
            case 1: return Direction.WEST;
            case 2: return Direction.SOUTH;
            case 3: return Direction.NORTH;
            case 4: return Direction.NONE;
            default: break;
        }
        return null;
    }
}
