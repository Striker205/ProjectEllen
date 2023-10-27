package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor  {
    private Animation heliAnimation;
    private int x;
    private int y;

    public Helicopter() {
        this.heliAnimation = new Animation("sprites/heli.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(heliAnimation);
        this.x = 0;
        this.y = 0;
    }
    public void searchAndDestroy() {
        new Loop<>(new Invoke<>(this::search)).scheduleFor(this);
    }

    private void search() {
        Player player = getScene().getFirstActorByType(Player.class);
        if(player == null) return;

        System.out.println(player.getPosition() + "    " + player.getEnergy());

        this.x = this.getPosX();
        this.y = this.getPosY();

        //Search
        if(player.getPosX() < this.getPosX()){
            this.setPosition(this.x-=1, this.y);
            this.heliAnimation.setRotation(90);
        }
        if(player.getPosX() > this.getPosX()) {
            this.setPosition(this.x+=1, this.y);
            this.heliAnimation.setRotation(270);
        }
        if(player.getPosY() < this.getPosY()){
            this.setPosition(this.x, this.y-=1);
            this.heliAnimation.setRotation(180);
        }
        if(player.getPosY() > this.getPosY()) {
            this.setPosition(this.x, this.y+=1);
            this.heliAnimation.setRotation(0);
        }

        //Found
        if(player.intersects(this)){
            player.setEnergy((player.getEnergy()-1));
        }
    }
}
