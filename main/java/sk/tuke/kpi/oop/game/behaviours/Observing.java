package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.function.Predicate;

public class Observing<A extends Actor, T> implements Behaviour<A>{
    private Behaviour<A> delegate;
    private Predicate<T> predicate;
    private Topic<T> topic;

    public Observing(Topic<T> topic, Predicate<T> predicate, Behaviour<A> delegate) {
        this.delegate = delegate;
        this.predicate = predicate;
        this.topic = topic;
    }

    @Override
    public void setUp(A actor) {
        if(actor == null || actor.getScene() == null) return;
        actor.getScene().getMessageBus().subscribe(this.topic, topic -> {
            if(this.predicate.test(topic)) {
                this.delegate.setUp(actor);
            }
        });
    }
}
