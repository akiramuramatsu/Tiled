package mk.tm.android.tiled.activity;

import android.content.Intent;
import android.os.Bundle;
import mk.tm.android.tiled.config.GameConstants;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.BitmapFont;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Tome
 * Date: 20.2.12
 * Time: 23:05
 * To change this template use File | Settings | File Templates.
 */
public class LevelSelectActivity extends SimpleBaseGameActivity implements GameConstants {
    private BitmapTextureAtlas mBackgroundTextureAtlas;
    private ITextureRegion mBackgroundTextureRegion;

    private BitmapTextureAtlas mBitmapTextureAtlas;
    private ITextureRegion mLevelTextureRegion;

    private BitmapFont mFont;

    @Override
    protected void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        this.mFont = new BitmapFont(this.getTextureManager(), this.getAssets(), "font/BitmapFont.fnt");
        this.mFont.load();

        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 64, 64);
        this.mLevelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "level_tile.png", 0, 0);
        this.mBitmapTextureAtlas.load();

        this.mBackgroundTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT);
        this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTextureAtlas, this, "background.png", 0, 0);
        this.mBackgroundTextureAtlas.load();
    }

    @Override
    protected Scene onCreateScene() {
        final Scene mainScene = new Scene();
        final Scene scene = new Scene();
        mainScene.attachChild(new Sprite(0, 0, this.mBackgroundTextureRegion, this.getVertexBufferObjectManager()));

        Sprite tile1 = new Sprite(200, 120, this.mLevelTextureRegion, this.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                Intent intent = getIntent();
                intent.setClass(LevelSelectActivity.this, BoardActivity.class);
                intent.putExtra(LEVEL_ACTION, 1);
                startActivity(intent);
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        Sprite tile2 = new Sprite(300, 120, this.mLevelTextureRegion, this.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                Intent intent = getIntent();
                intent.setClass(LevelSelectActivity.this, BoardActivity.class);
                intent.putExtra(LEVEL_ACTION, 2);
                startActivity(intent);
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        scene.attachChild(tile1);
        scene.attachChild(tile2);

        scene.attachChild(new Text(180, 50, this.mFont, "Select level", this.getVertexBufferObjectManager()));
        scene.attachChild(new Text(225, 135, this.mFont, "1", this.getVertexBufferObjectManager()));
        scene.attachChild(new Text(325, 135, this.mFont, "2", this.getVertexBufferObjectManager()));

        scene.registerTouchArea(tile1);
        scene.registerTouchArea(tile2);

        scene.setPosition(-50, 100);
        scene.setBackgroundEnabled(false);
        mainScene.setChildScene(scene);
        return mainScene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, EngineOptions.ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }
}
