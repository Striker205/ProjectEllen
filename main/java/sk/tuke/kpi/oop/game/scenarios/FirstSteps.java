package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;


public class FirstSteps implements SceneListener {

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Energy energy = scene.getFirstActorByType(Energy.class);
        if(energy == null) return;
        scene.addActor(energy, 50,50);

        Ammo ammo1 = new Ammo();
        scene.addActor(ammo1, -50,-100);
        Ammo ammo2 = new Ammo();

        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        if(ripley == null) return;
        scene.addActor(ripley, 0, 0);

        items(ripley, scene);
        //ripley.getBackpack().shift();

        new When<>(
            () -> ripley.intersects(energy),
            new Use<>(energy)
        ).scheduleFor(ripley);

        //Ammo
        new When<>(
            () -> ripley.intersects(ammo1),
            new Use<>(ammo1)
        ).scheduleFor(ripley);

        new When<>(
            () -> ripley.intersects(ammo2),
            new Use<>(ammo2)
        ).scheduleFor(ripley);


        KeeperController keeperController = new KeeperController(ripley);
        scene.getInput().registerListener(keeperController);


        scene.getGame().pushActorContainer(ripley.getBackpack());

        playerMovement(scene);
        sceneUpdating(scene);
    }

    public void playerMovement(Scene scene) {
        if(scene == null) return;
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        if(ripley == null) return;
/*
        Move<Ripley> move = new Move<>(Direction.NORTH.combine(Direction.EAST), 2.0f);
        move.setActor(ripley);
        move.scheduleFor(ripley);*/


        MovableController movableController = new MovableController(ripley);
        scene.getInput().registerListener(movableController);
    }

    public void items(Ripley ripley, Scene scene) {
        Wrench wrench = new Wrench();
        scene.addActor(wrench, -300, -150);

        FireExtinguisher fireExtinguisher = new FireExtinguisher();
        scene.addActor(fireExtinguisher, -300, -100);

        Hammer hammer = new Hammer();
        scene.addActor(hammer, -300, -200);

        Wrench wrench1 = new Wrench();
        scene.addActor(wrench1, -300, -50);

        FireExtinguisher fireExtinguisher1 = new FireExtinguisher();
        scene.addActor(fireExtinguisher1, -300, 0);

        Hammer hammer1 = new Hammer();
        scene.addActor(hammer1, -300, 50);

        Wrench wrench2 = new Wrench();
        scene.addActor(wrench2, -300, 100);

        FireExtinguisher fireExtinguisher2 = new FireExtinguisher();
        scene.addActor(fireExtinguisher2, -300, 150);

        Hammer hammer2 = new Hammer();
        scene.addActor(hammer2, -100, 200);

        Wrench wrench3 = new Wrench();
        scene.addActor(wrench3, -100, 100);

        FireExtinguisher fireExtinguisher3 = new FireExtinguisher();
        scene.addActor(fireExtinguisher3, -100, 150);

        Hammer hammer3 = new Hammer();
        scene.addActor(hammer3, -100, 200);

        //ripley.getBackpack().add(wrench);
        //ripley.getBackpack().add(fireExtinguisher);
        //ripley.getBackpack().add(hammer);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        if(ripley == null) return;

        ripley.showRipleyState();
    }
}
