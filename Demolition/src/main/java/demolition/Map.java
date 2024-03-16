package demolition;

import java.util.ArrayList;

import processing.core.PImage;

public class Map {
    private int[] startCoords = new int[2];
    private int time = 0;
    private ArrayList<ArrayList<PImage>> images = new ArrayList<ArrayList<PImage>>();
    private ArrayList<ArrayList<String>> textMap = new ArrayList<ArrayList<String>>();
    private ArrayList<int[]> yellowEnemyStartCoords = new ArrayList<int[]>();
    private ArrayList<int[]> redEnemyStartCoords = new ArrayList<int[]>();

    private ArrayList<ArrayList<PImage>> originalImages = new ArrayList<ArrayList<PImage>>();
    private ArrayList<ArrayList<String>> originalTextMap = new ArrayList<ArrayList<String>>();

    private PImage blankTile;

    public Map(int[] startCoords, int time, ArrayList<ArrayList<PImage>> images, ArrayList<ArrayList<String>> textMap, ArrayList<int[]> yellowEnemyCoords,ArrayList<int[]> redEnemyCoords){
        this.startCoords = startCoords;
        this.time = time;
        this.originalImages = images;
        this.originalTextMap = textMap;
        this.blankTile = images.get(startCoords[1]/32).get(startCoords[0]/32);
        this.yellowEnemyStartCoords = yellowEnemyCoords; 
        this.redEnemyStartCoords = redEnemyCoords; 
        copyMap(images, textMap);
        

    }

    public void copyMap(ArrayList<ArrayList<PImage>> images, ArrayList<ArrayList<String>> textMap){
        for (ArrayList<PImage> a: images) {
            ArrayList<PImage> newLine = new ArrayList<PImage>();
            for (PImage p: a){
                newLine.add(p);
            }
            this.images.add(newLine);
        }

        for (ArrayList<String> a: textMap) {
            ArrayList<String> newLine = new ArrayList<String>();
            for (String s: a){
                newLine.add(s);
            }
            this.textMap.add(newLine);
        }
    }

    public void wallDestroyed(int x, int y){
        ArrayList<String> newList = this.textMap.get(y);
        newList.set(x," ");
        this.textMap.set(y, newList);

        ArrayList<PImage> newPList = this.images.get(y);
        newPList.set(x, blankTile);
        this.images.set(y, newPList);
    }

    public ArrayList<int[]> getYellowEnemies(){
        return this.yellowEnemyStartCoords;
    }
    public ArrayList<int[]> getRedEnemies(){
        return this.redEnemyStartCoords;
    }
    public int[] getStartCoords(){
        return this.startCoords;
    }
    public int getTime(){
        return this.time;
    }
    public ArrayList<ArrayList<PImage>> getImages(){
        return this.images;
    }
    public ArrayList<ArrayList<String>> getTextMap(){
        return this.textMap;
    }

    public void reset(){
        System.out.println("MAP RESETTING");
        this.images = new ArrayList<ArrayList<PImage>>();
        this.textMap = new ArrayList<ArrayList<String>>();
        copyMap(this.originalImages, this.originalTextMap);
        
    }
}
