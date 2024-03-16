package demolition;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Bomb {
    public boolean isActive;
    public boolean hasExploded;
    private int timeToExplode;
    private int[] coordinates = new int[2];
    private int spriteIndex = 0;
    private Explosion exp;
    private ArrayList<PImage> sprites = new ArrayList<PImage>();

    public Bomb(int[] coords){
        this.coordinates[0] = coords[0];
        this.coordinates[1] = coords[1];
        this.hasExploded = false;
    }

    public void loadSprites(PApplet app){
        this.sprites.add(app.loadImage("bomb/bomb.png"));
        for (int i = 1; i < 8; i++){
            this.sprites.add(app.loadImage("bomb/bomb" + i + ".png"));
        }
    }

    public void detonate(PApplet app, Map map){
        this.timeToExplode = 120;
        this.isActive = true;
        this.draw(app,map);
    }

    public void tick(PApplet app, Map map, BombGuy bombGuy, ArrayList<YellowEnemy> yEnemies, ArrayList<RedEnemy> rEnemies){
        timeToExplode--;
        if (timeToExplode == 0) {
            this.exp = this.explode(app,map,bombGuy,yEnemies,rEnemies);
        } else if (timeToExplode % 15 == 0 && hasExploded == false){
            this.spriteIndex++;
            this.draw(app,map);
        }
        if (hasExploded == true){
            if (this.exp.isActive() == true){
                this.exp.explodeTick(app,map,bombGuy,yEnemies,rEnemies);
            } else {
                this.isActive = false;
            }
        }

        
    }
    
    public void draw(PApplet app, Map map){
        app.image(map.getImages().get((this.coordinates[1]-64)/32).get(this.coordinates[0]/32), this.coordinates[0], this.coordinates[1]);
        app.image(this.sprites.get(spriteIndex),this.coordinates[0],this.coordinates[1]);
    }

    public Explosion explode(PApplet app, Map map, BombGuy bombGuy, ArrayList<YellowEnemy> yEnemies,ArrayList<RedEnemy> rEnemies){
        app.image(map.getImages().get((this.coordinates[1]-64)/32).get(this.coordinates[0]/32), this.coordinates[0], this.coordinates[1]);
        //System.out.println("Exploded");
        this.hasExploded = true;
        Explosion exp = new Explosion(coordinates[0], coordinates[1],app);
        exp.explodeTick(app, map, bombGuy,yEnemies,rEnemies);
        return exp;

    }


}
