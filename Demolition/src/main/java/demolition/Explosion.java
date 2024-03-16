package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class Explosion {
    private PImage verticalSprite;
    private PImage horizontalSprite;
    private PImage centreSprite;
    private int explosionPointX;
    private int explosionPointY;
    private int time = 30;
    private boolean active;
    private ArrayList<Integer[]> explosionArea = new ArrayList<Integer[]>();

    public Explosion(int x, int y, PApplet app){
        explosionPointX = x/32;
        explosionPointY = y/32 - 2;
        active = true;

        // Load images
        verticalSprite = app.loadImage("explosion/vertical.png");
        horizontalSprite = app.loadImage("explosion/horizontal.png");
        centreSprite = app.loadImage("explosion/centre.png");
    }

    public boolean isActive(){
        return this.active;
    }

    public boolean explodeTick(PApplet app, Map map, BombGuy bombGuy, ArrayList<YellowEnemy> yEnemies, ArrayList<RedEnemy> rEnemies){
        time -= 1;
        if (time == 0){
            active = false;
            for (Integer[] c: explosionArea){
                app.image(map.getImages().get(c[1]).get(c[0]), c[0]*32, c[1]*32+64);
            }


            return active;
        } else {
            //centre
            app.image(centreSprite, explosionPointX*32, explosionPointY*32+64);
            if (time == 29){
                Integer[] coords = {explosionPointX,explosionPointY};
                explosionArea.add(coords);
            }

            try {
            //vertical
            if (map.getTextMap().get(explosionPointY-1).get(explosionPointX).equals(" ") || map.getTextMap().get(explosionPointY-1).get(explosionPointX).equals("B")) {
                app.image(verticalSprite, explosionPointX*32, (explosionPointY-1)*32 + 64);
                map.wallDestroyed(explosionPointX, explosionPointY-1);
                if (time == 29){
                    Integer[] coords9 = {explosionPointX,explosionPointY-1};
                    explosionArea.add(coords9);
                }
                if (map.getTextMap().get(explosionPointY-2).get(explosionPointX).equals(" ") || (map.getTextMap().get(explosionPointY-2).get(explosionPointX).equals("B") && map.getTextMap().get(explosionPointY-1).get(explosionPointX).equals("B")== false)) {
                    app.image(verticalSprite, explosionPointX*32, (explosionPointY-2)*32+ 64);
                    map.wallDestroyed(explosionPointX, explosionPointY-2);
                    
                    if (time == 29){
                        Integer[] coords2 = {explosionPointX,explosionPointY-2};
                        explosionArea.add(coords2);
                    }
                }
            }

            if (map.getTextMap().get(explosionPointY+1).get(explosionPointX).equals(" ") || map.getTextMap().get(explosionPointY+1).get(explosionPointX).equals("B")) {
                app.image(verticalSprite, explosionPointX*32, (explosionPointY+1)*32+ 64);
                map.wallDestroyed(explosionPointX, explosionPointY+1);
                
                if (time == 29){
                    Integer[] coords3 = {explosionPointX,explosionPointY+1};
                    explosionArea.add(coords3);
                }

                if (map.getTextMap().get(explosionPointY+2).get(explosionPointX).equals(" ") || map.getTextMap().get(explosionPointY+2).get(explosionPointX).equals("B")) {
                    app.image(verticalSprite, explosionPointX*32, (explosionPointY+2)*32+ 64);
                    map.wallDestroyed(explosionPointX, explosionPointY+2);
                    
                    if (time == 29){
                        Integer[] coords4 = {explosionPointX,explosionPointY+2};
                        explosionArea.add(coords4);
                    }
                }
            }

            //horizontal
            if (map.getTextMap().get(explosionPointY).get(explosionPointX-1).equals(" ") || map.getTextMap().get(explosionPointY).get(explosionPointX-1).equals("B")) {
                app.image(horizontalSprite, (explosionPointX-1)*32, explosionPointY*32+ 64);
                map.wallDestroyed(explosionPointX-1, explosionPointY);
                
                if (time == 29){
                    Integer[] coords5 = {explosionPointX-1,explosionPointY};
                    explosionArea.add(coords5);
                }
                
                if (map.getTextMap().get(explosionPointY).get(explosionPointX-2).equals(" ") || map.getTextMap().get(explosionPointY).get(explosionPointX-2).equals("B")) {
                    app.image(horizontalSprite, (explosionPointX-2)*32, explosionPointY*32+ 64);
                    map.wallDestroyed(explosionPointX-2, explosionPointY);
                    
                    if (time == 29){
                        Integer[] coords6 = {explosionPointX-2,explosionPointY};
                        explosionArea.add(coords6);
                    }
                }
            }
            if (map.getTextMap().get(explosionPointY).get(explosionPointX+1).equals(" ") || map.getTextMap().get(explosionPointY).get(explosionPointX+1).equals("B")) {
                app.image(horizontalSprite, (explosionPointX+1)*32, explosionPointY*32+ 64);
                map.wallDestroyed(explosionPointX+1, explosionPointY);
                
                if (time == 29){
                    Integer[] coords7 = {explosionPointX+1,explosionPointY};
                    explosionArea.add(coords7);
                }
                if (map.getTextMap().get(explosionPointY).get(explosionPointX+2).equals(" ") || map.getTextMap().get(explosionPointY).get(explosionPointX+2).equals("B")) {
                    app.image(horizontalSprite, (explosionPointX+2)*32, explosionPointY*32+ 64);
                    map.wallDestroyed(explosionPointX+2, explosionPointY);
                    
                    if (time == 29){
                        Integer[] coords8 = {explosionPointX+2,explosionPointY};
                        explosionArea.add(coords8);
                    }
                }
            }
            } catch (Exception e){
                System.out.println(e);
            }
            //KILL BOMBGUY
            for (Integer[] i: explosionArea){
                if (i[0] == bombGuy.getCoords()[0]/32 && i[1] == bombGuy.getCoords()[1]/32 - 2 ){
                    System.out.println("Dead");
                    bombGuy.kill(map);
                    this.active = false;
                    return active;
    
                }
                //Kill enemies
                for (YellowEnemy y: yEnemies){
                    if (i[0] == y.getCoords()[0]/32 && i[1] == y.getCoords()[1]/32 - 2){
                        //System.out.println("Dead Yellow enemy");
                        y.kill(map);
                        
                        return active;
                    }
                }

                for (RedEnemy r: rEnemies){
                    if (i[0] == r.getCoords()[0]/32 && i[1] == r.getCoords()[1]/32 - 2){
                        //System.out.println("Dead Yellow enemy");
                        r.kill(map);
                        
                        return active;
                    }
                }
            }
            
           
            

            

            

            return active;
        }
    }
}
