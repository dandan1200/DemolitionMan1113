package demolition;

import processing.core.*;
import processing.data.*;

import java.util.*;
import java.io.*;

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;
    public static final int FPS = 60;

    public Map currentMap;
    private int currentMapIndex = 0;
    private ArrayList<Map> levels;
    public BombGuy bombGuy; 
    private LoadMap loadMap;
    private Boolean lastPress = false;
    public int lives;
    private PImage playerIcon;
    private PImage clockIcon;
    private int clock;
    private int clockCounter = 0;
    private PFont font;
    private ArrayList<Bomb> activeBombs = new ArrayList<Bomb>();
    private ArrayList<YellowEnemy> yellowEnemies = new ArrayList<YellowEnemy>();
    private ArrayList<RedEnemy> redEnemies = new ArrayList<RedEnemy>();
    private String configFileName;
    public boolean gameOver = false;


    public App() {
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        frameRate(FPS);
        //Setup font
        this.clockIcon = this.loadImage("icons/clock.png");
        this.playerIcon = this.loadImage("icons/player.png");
        this.font = createFont("PressStart2P-Regular.ttf",20);
        this.textFont(this.font,20);

        System.out.println("***** SETUP GAME *****");

        //Get config file
        JSONObject config = getConfig();
        System.out.println("READ CONFIG FILE -- PASSED");

        //Get lives
        this.lives = config.getInt("lives");

        //Get all levels
        this.levels = this.getAllLevels(config);
        System.out.println("READ ALL LEVELS -- PASSED");

        // Load in first map into current map.
        currentMap = this.levels.get(0);
        this.clock = currentMap.getTime();

        // Load characters
        bombGuy = new BombGuy(this.currentMap.getStartCoords());
        bombGuy.loadResources("player/player", this);

        System.out.println("LOADING ENEMIES...");
        loadEnemies();
        System.out.println("DRAWING MAP...");
        drawMap();

        System.out.println("***** SETUP COMPLETE - GAME STARTING *****");
    }
    private void loadEnemies(){
        //Load yellow enemies
        this.yellowEnemies = new ArrayList<YellowEnemy>();
        for (int[] i: currentMap.getYellowEnemies()){
            int[] coordsI = {i[0]*32,i[1]*32 + 64};
            YellowEnemy y = new YellowEnemy(coordsI);
            y.loadResources("yellow_enemy/yellow", this);
            yellowEnemies.add(y);
            //System.out.println("Yellow enemy added at: " + i[0] + i[1]);
        }
        this.redEnemies = new ArrayList<RedEnemy>();

        //Load red enemies
        for (int[] i: currentMap.getRedEnemies()){
            int[] coordsI = {i[0]*32,i[1]*32 + 64};
            RedEnemy y = new RedEnemy(coordsI);
            y.loadResources("red_enemy/red", this);
            redEnemies.add(y);
            //System.out.println("Red enemy added at: " + i[0] + i[1]);
        }
    }

    public void draw() {
        if (this.gameOver == true){
            gameOver();
        }
        //Tick clock

        this.clockTick();
        
        //Call bombGuy logic tick
        this.bombGuy.tick(this,this.currentMap);

        //Call bomb logic tick
        for (Bomb b: activeBombs){
            if (b.isActive == true) {
                b.tick(this, currentMap, bombGuy,yellowEnemies,redEnemies);
            } else {
                //Check bombguy alive
                if (this.bombGuy.alive == false){
                    this.lives-=1;
                    if (this.lives == 0){
                        gameOver(); 
                    }

                    for (YellowEnemy y : yellowEnemies){
                        y.setCoords(y.startCoords[0],y.startCoords[1]);
                    }
                    for (RedEnemy r : redEnemies){
                        r.setCoords(r.startCoords[0],r.startCoords[1]);
                    }

                    drawMap();
                    this.bombGuy.alive = true;
                }
                if (Enemy.Killed == true){
                    Enemy.Killed = false;
                    drawMap();
                }
                continue;
            }
        }

        //Call yellow enemy logic tick
        for (YellowEnemy y: yellowEnemies){
            if (y.alive == true)
                y.chooseMove(this, currentMap);
                if (y.currentCoords[0] == bombGuy.getCoords()[0] && y.currentCoords[1] == bombGuy.getCoords()[1]){
                    bombGuy.kill(currentMap);
                    this.lives-=1;
                    if (this.lives == 0)
                        gameOver();
                    
                    for (YellowEnemy y2 : yellowEnemies){
                        y2.setCoords(y2.startCoords[0],y2.startCoords[1]);
                    }
                    for (RedEnemy r : redEnemies){
                        r.setCoords(r.startCoords[0],r.startCoords[1]);
                    }
                    drawMap();
                }
        }
        //Call red enemy logic tick
        for (RedEnemy r: redEnemies){
            if (r.alive == true)
                r.chooseMove(this, currentMap);
                if (r.currentCoords[0] == bombGuy.getCoords()[0] && r.currentCoords[1] == bombGuy.getCoords()[1]){
                    bombGuy.kill(currentMap);
                    this.lives-=1;
                    if (this.lives == 0)
                        gameOver();
                    
                    for (YellowEnemy y : yellowEnemies){
                        y.setCoords(y.startCoords[0],y.startCoords[1]);
                    }
                    for (RedEnemy r2 : redEnemies){
                        r2.setCoords(r2.startCoords[0],r2.startCoords[1]);
                    }
                    drawMap();
                }
        }
        
        //Check finish line
        checkFinishLine();

        //Check movement
        this.getKeyMove();
    }

    private void checkFinishLine(){
        
        if (currentMap.getTextMap().get((bombGuy.getCoords()[1]/32)-2).get(bombGuy.getCoords()[0]/32).equals("G")){
            //System.out.println("GOAL");
            if (currentMapIndex == levels.size()-1){
                youWin();

            }
            else { 
                currentMap = levels.get(++currentMapIndex);
                drawMap();
                loadEnemies();
                bombGuy.setCoords(currentMap.getStartCoords()[0], currentMap.getStartCoords()[1]);
            }
        }
    }

    private void getKeyMove(){
        //System.out.println(this.keyCode);
        //System.out.println(this.keyPressed);
        Boolean press = this.keyPressed;
        Boolean moved = false;
        if (press && this.lastPress == false){
            if (this.keyCode == UP){
                //Move up
                moved = bombGuy.moveUp(this, currentMap);
                //System.out.println("UP");
            } else if(this.keyCode == DOWN){
                //Move down
                moved = bombGuy.moveDown(this, currentMap);
                //System.out.println("DOWN");
            } else if(this.keyCode == LEFT){
                //Move left
                moved = bombGuy.moveLeft(this, currentMap);
                //System.out.println("LEFT");
            } else if(this.keyCode == RIGHT){
                //Move right
                moved = bombGuy.moveRight(this, currentMap);
                //System.out.println(this.bombGuy.getCoords()[0]);
                //System.out.println(this.bombGuy.getCoords()[1]);
                //System.out.println("RIGHT");
            } else if (Character.isWhitespace(this.key)){
                // Place bomb
                System.out.println("Bomb Placed");
                placeBomb();
                //System.out.println("Place Bomb");
            }
        }

        this.lastPress = press;
    }

    private void placeBomb(){
        Bomb bomb = new Bomb(this.bombGuy.getCoords());
        bomb.loadSprites(this);
        this.activeBombs.add(bomb);
        bomb.detonate(this, currentMap);
    }

    public void drawMap(){
        //Draws map based on 2d image array.
        this.background(255,150,0);
        //Draw player icon and lives number
        fill(0,0,0);
        noStroke();
        this.text(String.valueOf(this.lives), 180, 44);
        this.image(playerIcon,140,16);
        //Draw clock icon and time number
        this.text(String.valueOf(this.clock), 320, 44);
        this.image(clockIcon,280,16);
        
        

        int x = 0;
        int y = 64;
        for (int i = 0; i < this.currentMap.getImages().size(); i++) {
            for (int j = 0; j < this.currentMap.getImages().get(i).size(); j++){
                this.image(this.currentMap.getImages().get(i).get(j), x, y);
                x += 32;
            }
            y += 32;
            x = 0;
        }
    }

    private void clockTick(){
        if (this.clock == 0){
            while (true){
                this.gameOver();
            }
        }

        else if (this.clockCounter == 60){
            this.clockCounter = 0;
            this.clock -= 1;
            fill(255,150,0);
            rect(320,20,350,45);
            fill(0,0,0);
            this.text(String.valueOf(this.clock), 320, 44);
            this.image(clockIcon,280,16);
        } else {
            this.clockCounter += 1;
        }
    }

    public void gameOver(){
        this.gameOver = true;
        background(255,150,0);
        textAlign(CENTER);
        this.text("GAME OVER",240, 240);
        
        exit();
    }
    
    public void youWin(){

        background(255,150,0);
        textAlign(CENTER);
        this.text("YOU WIN",240, 240);
        this.noLoop();

    }

    public JSONObject getConfig(){
        String jsonParse = "";
        try {
            if (this.configFileName == null){
                this.configFileName = "config.json";
            }

            File configf = new File(this.configFileName);
            Scanner read = new Scanner(configf);

            while (read.hasNextLine()){
                jsonParse += read.nextLine();
            }

            read.close();
        } catch (FileNotFoundException e){
            System.out.println("No config file");
        }
        
        JSONObject configJSON = JSONObject.parse(jsonParse);
        
        return configJSON;
    }

    public void setConfig(String filename){
        this.configFileName = filename;
    }
    
    public ArrayList<Map> getAllLevels(JSONObject config){
        ArrayList<Map> levels = new ArrayList<Map>();

        JSONArray lvlsConfig = config.getJSONArray("levels");

        for (int i = 0; i < lvlsConfig.size(); i++) { 
            //Passes level file object with path from config to getMapImages and returns 2d map list and time in hashmap.
            this.loadMap = new LoadMap(new File(lvlsConfig.getJSONObject(i).getString("path")));
            levels.add(new Map(this.loadMap.getBombGuyStartCoords(),lvlsConfig.getJSONObject(i).getInt("time"),this.loadMap.getMapImages(this),this.loadMap.getTextMap(i),this.loadMap.getYellowEnemies(),this.loadMap.getRedEnemies()));
            //System.out.println(this.loadMap.getRedEnemies().toString());
        }

        return levels;
    }
    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
