package demolition;

import processing.core.*;
import java.util.*;
import java.io.*;

public class LoadMap {
    public PImage solidWall;
    public PImage brokenWall;
    public PImage emptyTile;
    public PImage goalTile;
    private File levelConfigFile;
    private int[] bombGuyStartCoords = new int[2];
    private ArrayList<int[]> redEnemyStartCoords = new ArrayList<int[]>();
    private ArrayList<int[]> yellowEnemyStartCoords= new ArrayList<int[]>();
    private ArrayList<ArrayList<String>> textMap = new ArrayList<ArrayList<String>>();

    public LoadMap(File levelConfigFile){
        this.levelConfigFile = levelConfigFile;
    }

    public int[] getBombGuyStartCoords(){
        return this.bombGuyStartCoords;
    }

    public ArrayList<ArrayList<String>> getTextMap(int index){
        return this.textMap;
    }

    public ArrayList<ArrayList<PImage>> getMapImages(PApplet app){
        ArrayList<ArrayList<String>> textMap = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<PImage>> images = new ArrayList<ArrayList<PImage>>();
        Scanner read;
        String line;

        //read images
        try {
            this.solidWall = app.loadImage("src/main/resources/wall/solid.png");
            this.brokenWall = app.loadImage("src/main/resources/broken/broken.png");
            this.emptyTile = app.loadImage("src/main/resources/empty/empty.png");
            this.goalTile = app.loadImage("src/main/resources/goal/goal.png");

        } catch (Exception e){
            System.out.println("Error: image resources not found.");
        }
        System.out.println("READ LEVEL IMAGES -- PASSED");
        //add images to 2d arraylist from level config file
        //read map file
        try {
            read = new Scanner(levelConfigFile);
            //System.out.println("Passed Scan file");

            for (int lineIndex = 0; lineIndex < 13; lineIndex++){
                line = read.nextLine();
                textMap.add(new ArrayList<String>());
                ArrayList<PImage> imageLine = new ArrayList<PImage>();
                //System.out.println("Passed read file");

                for (int charIndex = 0; charIndex < line.length(); charIndex++){
    
                    if (line.charAt(charIndex) == "W".charAt(0)) {
                        imageLine.add(this.solidWall);
                        textMap.get(lineIndex).add("W");
                        //System.out.println("Passed added w");

                    } else if (line.charAt(charIndex) == "B".charAt(0)) {
                        imageLine.add(this.brokenWall);
                        textMap.get(lineIndex).add("B");
                        //System.out.println("Passed added b");

                    } else if (line.charAt(charIndex) == " ".charAt(0)) {
                        imageLine.add(this.emptyTile);
                        textMap.get(lineIndex).add(" ");
                        //System.out.println("Passed added space");

                    } else if (line.charAt(charIndex) == "G".charAt(0)) {
                        imageLine.add(this.goalTile);
                        textMap.get(lineIndex).add("G");
                        //System.out.println("Passed added g");

                    } else if (line.charAt(charIndex) == "P".charAt(0)) {
                        this.bombGuyStartCoords[0] = charIndex*32;
                        this.bombGuyStartCoords[1] = lineIndex*32 + 64;
                        imageLine.add(this.emptyTile);
                        textMap.get(lineIndex).add(" ");
                        //System.out.println("Passed added p");

                    } else if (line.charAt(charIndex) == "R".charAt(0)) {
                        int[] coords = {charIndex,lineIndex};
                        this.redEnemyStartCoords.add(coords);
                        imageLine.add(this.emptyTile);
                        textMap.get(lineIndex).add(" ");
                        //System.out.println("Passed added r");

                    } else if (line.charAt(charIndex) == "Y".charAt(0)) {
                        int[] coords = {charIndex,lineIndex};
                        //System.out.println("Found y");
                        this.yellowEnemyStartCoords.add(coords);
                        imageLine.add(this.emptyTile);
                        textMap.get(lineIndex).add(" ");
                        //System.out.println("Passed added y");

                    }
    
                }
                images.add(imageLine);
                
            }
            this.textMap = textMap;
            read.close();

        } catch (FileNotFoundException e){
            System.out.println("No config file");
        }

        return images;
        
    }
    
    public ArrayList<int[]> getYellowEnemies(){
        return this.yellowEnemyStartCoords;
    }
    public ArrayList<int[]> getRedEnemies(){
        return this.redEnemyStartCoords;
    }
}