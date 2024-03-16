package demolition;

public class BombGuy extends Sprite{
    public BombGuy(int[] startCoords){
        super(startCoords);
    }

    public void kill(Map map){
        this.setCoords(map.getStartCoords()[0],map.getStartCoords()[1]);
        map.reset();
        this.alive = false;
    }
}
