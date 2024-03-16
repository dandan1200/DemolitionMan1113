package demolition;

import processing.core.PApplet;
import java.util.Random;

public class RedEnemy extends Enemy {
    private int moveClock = 0;

    public RedEnemy(int[] coords){
        super(coords);
    }
    public void chooseMove(PApplet app, Map map){
        moveClock += 1;
        if (moveClock == 60) {
            moveClock = 0;
            if (this.currentDirection == "Down") {
                if(this.moveDown(app, map) == false){
                    randomMove(app, map);
                }
            }
            else if (this.currentDirection == "Left") {
                if(this.moveLeft(app, map) == false){
                    randomMove(app, map);

                }
            }
            
            else if (this.currentDirection == "Up") {
                if(this.moveUp(app, map) == false){
                    randomMove(app, map);

                }
            }
            else if (this.currentDirection == "Right") {
                if(this.moveRight(app, map) == false){
                    randomMove(app, map);

                }
            }
        }
    this.tick(app, map);
    
    }

    private void randomMove(PApplet app, Map map){
        Random rand = new Random();
        int number = rand.nextInt(4);
        if (number == 0){
            if (this.moveDown(app, map)== false){
                randomMove(app, map);
            } else {
                return;
            }
        }
        else if (number == 1){
            if (this.moveRight(app, map)== false){
                randomMove(app, map);
            } else {
                return;
            }
        
        }
        else if (number == 2){
            if (this.moveUp(app, map)== false){
                randomMove(app, map);
            } else {
                return;
            }
        }
        else if (number == 3){
            if (this.moveLeft(app, map)== false){
                randomMove(app, map);
            } else {
                return;
            }
        }


    }

    
}
