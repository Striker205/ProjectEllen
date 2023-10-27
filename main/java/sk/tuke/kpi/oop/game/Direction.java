package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NORTHEAST(1,1),
    NORTHWEST(-1,1),
    SOUTHEAST(1,-1),
    SOUTHWEST(-1,-1),
    NONE(0,0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public float getAngle() {
        float angle = (float) Math.toDegrees(Math.atan2(getDx()*-1,getDy()));
        if(angle < 0) angle = 360 + angle;
        return angle;
    }

    public static Direction fromAngle(float angle) {
        switch((int) angle) {
            case 0: return NORTH;
            case 180: return SOUTH;
            case 90: return WEST;
            case 270: return EAST;
            case 45: return NORTHWEST;
            case 315: return NORTHEAST;
            case 225: return SOUTHEAST;
            case 135: return SOUTHWEST;
            default: return NONE;
        }
    }

    public Direction combine(Direction other) {
        int dy = other.getDy()+this.getDy();
        int dx = other.getDx()+this.getDx();

        if(dy == 2) dy=1;
        if(dx == 2) dx=1;
        if(dy == -2) dy=-1;
        if(dx == -2) dx=-1;

        for(Direction direction : Direction.values()){
            if(direction.getDx() == dx && direction.getDy() == dy) {
                return direction;
            }
        }
        return NONE;
    }
}
