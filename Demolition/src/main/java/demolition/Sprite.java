package demolition;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;



public abstract class Sprite {
    public int[] startCoords = new int[2];
    protected int[] currentCoords = new int[2];
    private String[] directions = {"_down","_up","_left","_right"}; 

    // Animation resources
    protected ArrayList<PImage> northFacingImages = new ArrayList<PImage>();
    protected ArrayList<PImage> southFacingImages = new ArrayList<PImage>();
    protected ArrayList<PImage> eastFacingImages = new ArrayList<PImage>();
    protected ArrayList<PImage> westFacingImages = new ArrayList<PImage>();
    
    // Current frame information for sprite
    private PImage currentFrame;
    protected String currentDirection;
    private int currentFrameNum;
    private int frameNextIndex = 0;

    protected boolean alive;

    // Constructor
    public Sprite(int[] startCoords) {
        this.startCoords = startCoords;
        this.currentCoords[0] = startCoords[0];
        this.currentCoords[1] = startCoords[1];
        this.alive = true;
    }
    
    // Load images for animation of sprite
    public void loadResources(String nameFormat, PApplet app){
        //Load down facing images
        for (int i = 0; i < 4; i ++){
            String path = nameFormat + directions[0] + String.valueOf(i+1) + ".png";
            southFacingImages.add(app.loadImage(path));
        }
        //load up facing images
        for (int i = 0; i < 4; i ++){
            String path = nameFormat + directions[1] + String.valueOf(i+1) + ".png";
            northFacingImages.add(app.loadImage(path));
        }
        //load left facing images
        for (int i = 0; i < 4; i ++){
            String path = nameFormat + directions[2] + String.valueOf(i+1) + ".png";
            westFacingImages.add(app.loadImage(path));
        }
        //load right facing images
        for (int i = 0; i < 4; i ++){
            String path = nameFormat + directions[3] + String.valueOf(i+1) + ".png";
            eastFacingImages.add(app.loadImage(path));
        }
        //Set first frame
        currentFrame = southFacingImages.get(0);
        currentFrameNum = 0;
        currentDirection = "Down";


    }

    //Single tick logic
    public void tick(PApplet app, Map map){
        //Wait 0.2 seconds before switching to next frame
        if (frameNextIndex == 11){
            frameNextIndex = 0;
            this.draw(app, map);
        }
        frameNextIndex++;
    }

    public void draw(PApplet app, Map map){
        //Draw current frame and set next frame.
        this.drawOverMove(app, map, currentCoords[0]/32, (currentCoords[1]/32)-2);
        app.image(currentFrame,currentCoords[0],currentCoords[1]-12);
        nextImage();
    }
    
    public void nextImage(){
        if (currentFrameNum == 3){
            currentFrameNum = 0;
        }
        if (currentDirection == "Down"){
            currentFrame = southFacingImages.get(currentFrameNum+1);
            currentFrameNum += 1;
        } else if(currentDirection == "Left"){
            currentFrame = westFacingImages.get(currentFrameNum+1);
            currentFrameNum += 1;
        } else if(currentDirection == "Right"){
            currentFrame = eastFacingImages.get(currentFrameNum+1);
            currentFrameNum += 1;
        } else if(currentDirection == "Up"){
            currentFrame = northFacingImages.get(currentFrameNum+1);
            currentFrameNum += 1;
        }
    }

    public int[] getCoords(){
        return this.currentCoords;
    }

    public Boolean moveRight(PApplet app, Map map){
        int currentMapX = this.currentCoords[0]/32;
        int currentMapY = (this.currentCoords[1]-64)/32;

        if (map.getTextMap().get(currentMapY).get(currentMapX+1).equals(" ") || map.getTextMap().get(currentMapY).get(currentMapX+1).equals("G")){
            // Move right if free next to player.
            currentCoords[0] += 32;
            this.currentDirection = "Right";
            this.nextImage();
            this.drawOverMove(app, map, currentMapX, currentMapY);
            this.draw(app,map);
            //System.out.println("MOVED RIGHT 1 SPACE");
            return true;
        }
        return false;
    }

    public Boolean moveDown(PApplet app, Map map){
        int currentMapX = this.currentCoords[0]/32;
        int currentMapY = (this.currentCoords[1]-64)/32;
        
        if (map.getTextMap().get(currentMapY+1).get(currentMapX).equals(" ") || map.getTextMap().get(currentMapY+1).get(currentMapX).equals("G")){
            // Move right if free next to player.
            currentCoords[1] += 32;
            this.currentDirection = "Down";
            this.nextImage();
            this.drawOverMove(app, map, currentMapX, currentMapY);
            this.draw(app,map);
            //System.out.println("MOVED DOWN 1 SPACE");
            return true;
        }
        return false;
    }
    public Boolean moveLeft(PApplet app, Map map){
        int currentMapX = this.currentCoords[0]/32;
        int currentMapY = (this.currentCoords[1]-64)/32;
        
        if (map.getTextMap().get(currentMapY).get(currentMapX-1).equals(" ") || map.getTextMap().get(currentMapY).get(currentMapX-1).equals("G")){
            // Move right if free next to player.
            currentCoords[0] -= 32;
            this.currentDirection = "Left";
            this.nextImage();
            this.drawOverMove(app, map, currentMapX, currentMapY);
            this.draw(app,map);
            //System.out.println("MOVED LEFT 1 SPACE");
            return true;
        }
        return false;
    }
    public Boolean moveUp(PApplet app, Map map){
        int currentMapX = this.currentCoords[0]/32;
        int currentMapY = (this.currentCoords[1]-64)/32;
        
        if (map.getTextMap().get(currentMapY-1).get(currentMapX).equals(" ") || map.getTextMap().get(currentMapY-1).get(currentMapX).equals("G")){
            // Move right if free next to player.
            currentCoords[1] -= 32;
            this.currentDirection = "Up";
            this.nextImage();
            this.drawOverMove(app, map, currentMapX, currentMapY);
            this.draw(app,map);
            //System.out.println("MOVED UP 1 SPACE");
            return true;
        }
        return false;
    }

    protected void drawOverMove(PApplet app,Map map, int x, int y){
        try{
            app.image(map.getImages().get(y-1).get(x), x*32, y*32 + 32);
            app.image(map.getImages().get(y).get(x), x*32, y*32 + 64);
            app.image(map.getImages().get(y+1).get(x), x*32, y*32 + 96);
        } catch (Exception e){
            System.out.println(e);
            
        }

    }

    public void setCoords(int x, int y){
        this.currentCoords[0] = x;
        this.currentCoords[1] = y;
    }

    public abstract void kill(Map map);

}
