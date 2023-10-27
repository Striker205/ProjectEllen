package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class EscapeRoom implements SceneListener {

    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {

            if(name == null) return null;
            switch (name){
                case "energy": return new Energy();
                case "access card": return new AccessCard();
                case "door": return new LockedDoor();
                case "locker": return new Locker();
                case "ventilator": return new Ventilator();
                //case "alien": return new Alien();
                case "front door": return new Door("front door", Door.Orientation.VERTICAL);
                case "back door": return new Door("back door", Door.Orientation.HORIZONTAL);
                case "ammo": return new Ammo();
                case "exit door": return new Door("exit door", Door.Orientation.VERTICAL);
                case "alien mother": return new AlienMother();
                case "ellen": return new Ripley();
                default: break;
            }

            if(name.equals("alien") && type != null) {
                switch (type) {
                    case "running":
                        return new Alien(150, new RandomlyMoving());
                    case "waiting2":
                        return new Alien(150, new Observing<>(
                            Door.DOOR_OPENED,
                            door -> door.getName().equals("back door"),
                            new RandomlyMoving()
                        ));
                    case "waiting1":
                        return new Alien(150, new Observing<>(
                            Door.DOOR_OPENED,
                            door -> door.getName().equals("front door"),
                            new RandomlyMoving()
                        ));
                    default:
                        return new Alien(150, null);
                }
            }

            return null;
        }
    }

    @Override
    public void sceneCreated(@NotNull Scene scene) {
/*
        scene.getMessageBus().subscribe(World.ACTOR_ADDED_TOPIC, enemy -> {
            Alien alien = scene.getLastActorByType(Alien.class);
            if(alien == null) return;
            new Loop<>(new ActionSequence<>(
                new Invoke<>(() -> alienMove(scene, alien)),
                new Wait<>(0.5f)
            )).scheduleFor(alien);
        });*/
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ripley = getRipley(scene);
        if(ripley == null) return;
        Energy energy = scene.getFirstActorByType(Energy.class);
        if(energy == null) return;
        Ammo ammo = scene.getFirstActorByType(Ammo.class);
        if(ammo == null) return;

        new When<>(
            () -> ripley.intersects(energy),
            new Use<>(energy)
        ).scheduleFor(ripley);

        new When<>(
            () -> ripley.intersects(ammo),
            new Use<>(ammo)
        ).scheduleFor(ripley);

        //contaminationRoom(scene);

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
    private Alien getAlien(Scene scene) {
        return  scene.getFirstActorByType(Alien.class);
    }

    public void playerMovement(Scene scene) {
        if(scene == null) return;
        Ripley ripley = getRipley(scene);
        if(ripley == null) return;

        MovableController movableController = new MovableController(ripley);
        Disposable moveController = scene.getInput().registerListener(movableController);

        KeeperController keeperController = new KeeperController(ripley);
        Disposable playerController = scene.getInput().registerListener(keeperController);

        ShooterController shooterController = new ShooterController(ripley);
        scene.getInput().registerListener(shooterController);

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
/*
    private void alienMove(Scene scene, Alien alien) {
        int rnd = (int)(Math.random() * 9);
        Move<Alien> alienMove = new Move<>(directionAlien(rnd), 1.0f);
        alienMove.setActor(alien);
        alienMove.scheduleFor(alien);
    }

    private Direction directionAlien(int rnd) {
        switch (rnd){
            case 0: return Direction.EAST;
            case 1: return Direction.WEST;
            case 2: return Direction.SOUTH;
            case 3: return Direction.NORTH;
            case 4: return Direction.NORTHEAST;
            case 5: return Direction.NORTHWEST;
            case 6: return Direction.SOUTHEAST;
            case 7: return Direction.SOUTHWEST;
            case 8: return Direction.NONE;
            default: break;
        }
        return null;
    }
*/
}
