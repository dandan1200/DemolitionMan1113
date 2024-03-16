package demolition;

import processing.core.PApplet;

public abstract class Enemy extends Sprite {

    public static boolean Killed = false;

    public Enemy(int[] startCoords){
        super(startCoords);
    }
    
    public void kill(Map map){
        this.alive = false;
        this.Killed = true;
    }

    public abstract void chooseMove(PApplet app , Map map);

}