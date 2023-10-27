package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable {
    private int num;
    private Disposable disposable;

    public DefectiveLight() {
        this.num = 0;
        this.disposable = null;
    }

    public void defective() {
        this.num = (int)(Math.random() * 20);
        if(this.num == 1) turnOff();
        else turnOn();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.disposable = new Loop<>(new Invoke<>(this::defective)).scheduleFor(this);
    }

    @Override
    public boolean repair() {
        if(this.disposable == null) return false;

        this.disposable.dispose();
        new ActionSequence<>(
            new Wait<>(10),
            new Loop<>(new Invoke<>(this::defective))
        ).scheduleFor(this);
        this.disposable = null;

        return true;
    }
}
