package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.items.Hammer;

public class TrainingGameplay extends Scenario {
    @Override
    public void setupPlay(@NotNull Scene scene) {
        levelOne(scene);
    }

    public void levelOne(Scene scene) {
        if(scene == null) return;

        //Reactor
        Reactor reactor = new Reactor();
        scene.addActor(reactor, 150, 99);
        reactor.turnOn();

        //Cooler
        Cooler cooler = new Cooler(reactor);
        scene.addActor(cooler, 243, 98);
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);

        //Hammer
        Hammer hammer = new Hammer(1);
        scene.addActor(hammer, 191, 198);
        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> hammer.useWith(reactor))
        ).scheduleFor(reactor);

        //Light
        Light light = new Light();
        light.turnOn();
        scene.addActor(light, 218, 171);
        reactor.addDevice(light);

        //Chainbomb
        ChainBomb chainBomb1 = new ChainBomb(3);
        scene.addActor(chainBomb1,200,95);

        ChainBomb chainBomb2 = new ChainBomb(3);
        scene.addActor(chainBomb2,125,70);

        ChainBomb chainBomb3 = new ChainBomb(3);
        scene.addActor(chainBomb3,150,150);

        ChainBomb chainBomb = new ChainBomb(3);
        scene.addActor(chainBomb,200,50);

        ChainBomb chainBomb4 = new ChainBomb(3);
        scene.addActor(chainBomb4,140,100);


        //Teleport
        Teleport C = new Teleport(null);
        scene.addActor(C,253,258);

        Teleport B = new Teleport(C);
        //B.setDestination(C);
        scene.addActor(B,67,124);
        //B.teleportPlayer(scene.getFirstActorByType(Player.class));

        Teleport A = new Teleport(B);
        //A.setDestination(B);
        scene.addActor(A,67,322);
        //System.out.println(A.getDestination().getPosition());
        //A.teleportPlayer(scene.getFirstActorByType(Player.class));

    }
}
