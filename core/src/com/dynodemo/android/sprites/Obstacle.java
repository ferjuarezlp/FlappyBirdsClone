package com.dynodemo.android.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by ferjuarez on 24/03/16.
 */

public class Obstacle {
    private static final int FLUCTUATION = 130;
    private static final int OBSTACLE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    public static final int OBSTACLE_WIDTH = 52;

    private Texture topObstacle;
    private Texture bottomObstacle;
    private Vector2 posTopObstacle;
    private Vector2 posBottomObstacle;
    private Random rand;
    private Rectangle topBounds;
    private Rectangle bottomBounds;

    public Obstacle(float x){
        topObstacle = new Texture("toptube.png");
        bottomObstacle = new Texture("bottomtube.png");
        rand = new Random();
        posTopObstacle = new Vector2(x, rand.nextInt(FLUCTUATION) + OBSTACLE_GAP + LOWEST_OPENING);
        posBottomObstacle = new Vector2(x, posTopObstacle.y - OBSTACLE_GAP - bottomObstacle.getHeight());

        topBounds = new Rectangle(posTopObstacle.x, posTopObstacle.y, topObstacle.getWidth(), topObstacle.getHeight());
        bottomBounds = new Rectangle(posBottomObstacle.x, posBottomObstacle.y, bottomObstacle.getWidth(), bottomObstacle.getHeight());
    }

    public Vector2 getPosBottomObstacle() {
        return posBottomObstacle;
    }

    public Texture getTopObstacle() {
        return topObstacle;
    }

    public Texture getBottomObstacle() {
        return bottomObstacle;
    }

    public Vector2 getPosTopObstacle() {
        return posTopObstacle;
    }

    public void reposition(float x){
        posTopObstacle.set(x, rand.nextInt(FLUCTUATION) + OBSTACLE_GAP + LOWEST_OPENING);
        posBottomObstacle.set(x, posTopObstacle.y - OBSTACLE_GAP - bottomObstacle.getHeight());
        topBounds.setPosition(posTopObstacle.x, posTopObstacle.y);
        bottomBounds.setPosition(posBottomObstacle.x, posBottomObstacle.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(topBounds) || player.overlaps(bottomBounds);
    }

    public void dispose(){
        topObstacle.dispose();
        bottomObstacle.dispose();
    }

}
