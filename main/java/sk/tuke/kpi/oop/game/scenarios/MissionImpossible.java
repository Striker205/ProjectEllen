package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class MissionImpossible implements SceneListener {

    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if(name == null) return null;
            switch (name){
                case "ellen": return new Ripley();
                case "energy": return new Energy();
                case "access card": return new AccessCard();
                case "door": return new LockedDoor();
                case "locker": return new Locker();
                case "ventilator": return new Ventilator();
                default: break;
            }
            return null;
        }
    }
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ripley = getRipley(scene);
        if(ripley == null) return;
        Energy energy = scene.getFirstActorByType(Energy.class);
        if(energy == null) return;

        new When<>(
            () -> ripley.intersects(energy),
            new Use<>(energy)
        ).scheduleFor(ripley);

        contaminationRoom(scene);

        scene.getGame().pushActorContainer(ripley.getBackpack());
        playerMovement(scene);
        scene.follow(ripley);
    }

    public void contaminationRoom(Scene scene) {
        Ripley ripley = getRipley(scene);
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, x -> ripley.energyDecreasing());
        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, x -> ripley.stopEnergyDecreasing());
    }

    private Ripley getRipley(Scene scene) {
        return scene.getFirstActorByType(Ripley.class);
    }

    public void playerMovement(Scene scene) {
        if(scene == null) return;
        Ripley ripley = getRipley(scene);
        if(ripley == null) return;

        MovableController movableController = new MovableController(ripley);
        Disposable moveController = scene.getInput().registerListener(movableController);

        KeeperController keeperController = new KeeperController(ripley);
        Disposable playerController = scene.getInput().registerListener(keeperController);

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, x -> {
            moveController.dispose();
            playerController.dispose();
        });
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ripley = getRipley(scene);
        if(ripley == null) return;

        ripley.showRipleyState();
    }
}
