package com.dynodemo.android.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dynodemo.android.MyGdxGame;
import com.dynodemo.android.sprites.Dino;
import com.dynodemo.android.sprites.Obstacle;

/**
 * Created by ferjuarez on 24/03/16.
 */
public class PlayState extends State {
    private static final int OBSTACLE_SPACING = 125;
    private static final int OBSTACLE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    private Dino dino;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1;
    private Vector2 groundPos2;

    private Array<Obstacle> obstacles;

    public PlayState(GameStateManager gsm){
        super(gsm);
        dino = new Dino(50,300);
        cam.setToOrtho(false, MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT /2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth /2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth /2) + ground.getWidth(), GROUND_Y_OFFSET);

        obstacles = new Array<Obstacle>();
        for (int i = 1; i <= OBSTACLE_COUNT; i++) {
            obstacles.add(new Obstacle(i * (OBSTACLE_SPACING + Obstacle.OBSTACLE_WIDTH)));
        }

    }
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            dino.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        dino.update(dt);
        cam.position.x = dino.getPosition().x + 80;

        for(Obstacle obstacle : obstacles){
            if(cam.position.x - (cam.viewportWidth / 2) > obstacle.getPosTopObstacle().x + obstacle.getTopObstacle().getWidth()){
                obstacle.reposition(obstacle.getPosTopObstacle().x + ((Obstacle.OBSTACLE_WIDTH + OBSTACLE_SPACING) * OBSTACLE_COUNT));
            }

            if(obstacle.collides(dino.getBounds())){
                gsm.set(new PlayState(gsm));

            }
        }

        if(dino.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
            gsm.set(new PlayState(gsm));
        }
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth /2), 0);
        sb.draw(dino.getTexture(), dino.getPosition().x,dino.getPosition().y);

        for (Obstacle obstacle : obstacles){
            sb.draw(obstacle.getTopObstacle(), obstacle.getPosTopObstacle().x, obstacle.getPosTopObstacle().y);
            sb.draw(obstacle.getBottomObstacle(), obstacle.getPosBottomObstacle().x, obstacle.getPosBottomObstacle().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        dino.dispose();
        for(Obstacle obstacle : obstacles){
            obstacle.dispose();
        }
    }

    private void updateGround(){
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() *2,0);

        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() *2,0);
    }
}
