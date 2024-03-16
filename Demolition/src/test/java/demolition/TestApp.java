package demolition;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.*;

public class TestApp {
    
    @Test
    public void moveRightOnce() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        //app.setup();
        app.delay(1000);
        app.draw();
        app.keyCode = 39;
        app.keyPressed = true;
        app.draw();
        assertEquals(app.bombGuy.getCoords()[0], 64);
        assertEquals(app.bombGuy.getCoords()[1], 96);
    }

    @Test
    public void moveDownOnce() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        //app.setup();
        app.delay(1000);
        app.draw();
        app.keyCode = 40;
        app.keyPressed = true;
        app.draw();
        assertEquals(app.bombGuy.getCoords()[0], 32);
        assertEquals(app.bombGuy.getCoords()[1], 128);
    }

    @Test
    public void moveUpOnce() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        //app.setup();
        app.delay(1000);
        app.draw();
        app.keyCode = 38;
        app.keyPressed = true;
        app.draw();
        assertEquals(app.bombGuy.getCoords()[0], 32);
        assertEquals(app.bombGuy.getCoords()[1], 96);
    }
    @Test
    public void moveLeftOnce() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        //app.setup();
        app.delay(1000);
        app.draw();
        app.keyCode = 37;
        app.keyPressed = true;
        app.draw();
        assertEquals(app.bombGuy.getCoords()[0], 32);
        assertEquals(app.bombGuy.getCoords()[1], 96);
    }

    @Test
    public void placeBombOnSelf() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        //app.setup();
        app.delay(1000);
        app.draw();
        app.key = Character.SPACE_SEPARATOR;
        app.keyPressed = true;
        for (int i = 0; i < 122; i++){
            app.draw();
        }
        assertEquals(app.bombGuy.getCoords()[0], 32);
        assertEquals(app.bombGuy.getCoords()[1], 96);
        assertEquals(app.lives, 2);
    }

   

    
}
