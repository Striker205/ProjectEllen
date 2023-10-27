package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int maxAmmo;
    private int initAmmo;

    public Firearm(int initAmmo, int maxAmmo) {
        this.initAmmo = initAmmo;
        this.maxAmmo = maxAmmo;
    }

    public Firearm(int maxAmmo) {
        this.initAmmo = maxAmmo;
        this.maxAmmo = maxAmmo;
    }

    public int getAmmo() {
        return this.initAmmo;
    }

    public void reload(int newAmmo) {
        if((getAmmo()+newAmmo) < this.maxAmmo) {
            this.initAmmo += newAmmo;
        }else {
            this.initAmmo = maxAmmo;
        }
    }

    protected abstract Fireable createBullet();

    public Fireable fire() {
        if(getAmmo() > 0) {
            this.initAmmo -= 1;
            return createBullet();
        }
        return null;
    }
}
