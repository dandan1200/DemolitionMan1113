package demolition;

import processing.core.PApplet;

public class YellowEnemy extends Enemy {

    private int moveClock = 0;
    
    public YellowEnemy(int[] startCoords){
        super(startCoords);
    }


    public void chooseMove(PApplet app, Map map){
        moveClock += 1;
        if (moveClock == 60) {
            moveClock = 0;
            if (this.currentDirection == "Down") {
                if(this.moveDown(app, map) == false){
                    if(this.moveLeft(app, map) == false){
                        if (this.moveUp(app, map) == false){
                            if (this.moveRight(app, map) == false){
                                
                            }
                        }
                    }
                }
            }
            else if (this.currentDirection == "Left") {
                if(this.moveLeft(app, map) == false){
                    if(this.moveUp(app, map) == false){
                        if (this.moveRight(app, map) == false){
                            if (this.moveDown(app, map) == false){
                                
                            }
                        }
                    }
                }
            }
            
            else if (this.currentDirection == "Up") {
                if(this.moveUp(app, map) == false){
                    if(this.moveRight(app, map) == false){
                        if (this.moveDown(app, map) == false){
                            if (this.moveLeft(app, map) == false){
                                
                            }
                        }
                    }
                }
            }
            else if (this.currentDirection == "Right") {
                if(this.moveRight(app, map) == false){
                    if(this.moveDown(app, map) == false){
                        if (this.moveLeft(app, map) == false){
                            if (this.moveUp(app, map) == false){
                                
                            }
                        }
                    }
                }
            }
        }
    this.tick(app, map);
    
    }



}
