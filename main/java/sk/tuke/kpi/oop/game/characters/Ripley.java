package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    private Backpack backpack;
    private Health health;
    private Disposable disposable;
    private Animation playerDieAnimation;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);
    private boolean repaired;
    private Firearm gun;

    public Ripley() {
        super("Ellen");
        this.playerDieAnimation = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
        Animation player = new Animation("sprites/player.png",32,32,0f,Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(player);
        this.backpack = new Backpack("Ripley's backpack", 10);
        this.repaired = false;
        this.health = new Health(100, 100);
        this.gun = new Gun( 10);
        exhaustionEffect();
    }

    public void exhaustionEffect() {
        health.onExhaustion(new Health.ExhaustionEffect() {
            @Override
            public void apply() {
                playerDie();
            }
        });
    }

    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public void startedMoving(Direction direction) {
        Movable.super.startedMoving(direction);
        getAnimation().setRotation(direction.getAngle());
        getAnimation().setFrameDuration(0.1f);
    }

    @Override
    public void stoppedMoving() {
        Movable.super.stoppedMoving();
        getAnimation().setFrameDuration(0);
    }

    public void drain(int amount) {
        getHealth().drain(amount);
        if(getHealth().getValue() == 0) {
            playerDie();
        }
    }
    public int getAmmo() {
        return this.gun.getAmmo();
    }
    public void setAmmo(int ammo){
        this.gun.reload(ammo);
    }

    public void stopEnergyDecreasing() {
        this.disposable.dispose();
        this.repaired = true;
    }

    private void playerDie() {
        setAnimation(playerDieAnimation);
        Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
    }

    public void energyDecreasing() {
        if(this.repaired) return;

        this.disposable = new Loop<>(
            new ActionSequence<>(
                new Wait<>(0.5f),
                new Invoke<>(() -> getHealth().drain(1)))
        ).scheduleFor(this);

        new When<>(
            () -> getHealth().getValue() <= 0,
            new Invoke<>(this::playerDie)
        ).scheduleFor(this);
    }

    @Override
    public Backpack getBackpack() {
        return this.backpack;
    }

    public void showRipleyState() {
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        getScene().getGame().getOverlay().drawText("| Enegry: " + getHealth().getValue(), 110, yTextPos);
        getScene().getGame().getOverlay().drawText("| Ammo: " + this.getAmmo(), 260, yTextPos);
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    @Override
    public Firearm getFirearm() {
        return this.gun;
    }

    @Override
    public void setFirearm(Firearm firearm) {
        this.gun = firearm;
    }
}
