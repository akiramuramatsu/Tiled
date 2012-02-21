package mk.tm.android.tiled.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import mk.tm.android.tiled.config.GameConstants;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.BitmapFont;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.IVertexBufferObject;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

/**
 * Created by IntelliJ IDEA.
 * User: Tome
 * Date: 20.2.12
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends SimpleBaseGameActivity implements GameConstants {

    private BitmapTextureAtlas mBackgroundTextureAtlas;
    private ITextureRegion mBackgroundTextureRegion;

    private BitmapFont mFont;

    @Override
    protected void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        this.mFont = new BitmapFont(this.getTextureManager(), this.getAssets(), "font/BitmapFont.fnt");
        this.mFont.load();

        this.mBackgroundTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT);
        this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTextureAtlas, this, "background.png", 0, 0);
        this.mBackgroundTextureAtlas.load();
    }

    @Override
    protected Scene onCreateScene() {
        final Scene scene = new Scene();
        scene.attachChild(new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, this.mBackgroundTextureRegion, this.getVertexBufferObjectManager()));

        Text playText = new Text(170, 300, this.mFont, "Play!", this.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                Intent intent = new Intent(MainActivity.this, LevelSelectActivity.class);
                intent.putExtra(WORLD_ACTION, 1);
                startActivity(intent);
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        playText.setColor(Color.YELLOW);

        scene.attachChild(playText);
        scene.registerTouchArea(playText);

        return scene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, EngineOptions.ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }
}