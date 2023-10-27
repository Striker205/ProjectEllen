package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible> {
    private List<Collectible> objects;
    private String name;
    private int capacity;

    public Backpack(String name, int capacity) {
        this.objects = new ArrayList<Collectible>();
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        return List.copyOf(this.objects);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int getSize() {
        return this.objects.size();
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(objects.size() != getCapacity()) {
            objects.add(actor);
        }else {
            throw new IllegalStateException(getName()+" is full");
        }
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        objects.remove(actor);
    }

    @Override
    public @Nullable Collectible peek() {
        if(getSize() == 0) return null;
        return objects.get(getSize()-1);
    }

    @Override
    public void shift() {
        if(getSize() != 0) {
            Collectible item = objects.get(getSize() - 1);
            objects.remove(item);
            objects.add(0, item);
        }
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return objects.iterator();
    }
}
