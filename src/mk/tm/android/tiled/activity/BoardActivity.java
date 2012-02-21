package mk.tm.android.tiled.activity;

import android.os.Bundle;
import mk.tm.android.tiled.config.LevelConfiguration;
import mk.tm.android.tiled.config.GameConstants;
import mk.tm.android.tiled.entity.Board;
import mk.tm.android.tiled.entity.Cell;
import mk.tm.android.tiled.entity.Player;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.BitmapFont;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

public class BoardActivity extends SimpleBaseGameActivity implements GameConstants {
    // ===========================================================
    // Constants
    // ===========================================================

    private Board mScene;
    private Player mPlayer;

    private int mWorld;
    private int mLevel;

    private BitmapFont mFont;

    private BitmapTextureAtlas mBitmapTextureAtlas;
    private ITiledTextureRegion mPlayerTextureRegion;
    private ITiledTextureRegion mCellTextureRegion;

    private BitmapTextureAtlas mBackgroundTextureAtlas;
    private ITextureRegion mBackgroundTextureRegion;
    private LevelConfiguration mConfiguration;
    private Text mLeavesText;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.mWorld = getIntent().getIntExtra(WORLD_ACTION, 1);
        this.mLevel = getIntent().getIntExtra(LEVEL_ACTION, 1);

        this.mConfiguration = LevelConfiguration.get(this.mWorld, this.mLevel, this);
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, EngineOptions.ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    protected void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        /* Load all the textures this game needs. */
        this.mBackgroundTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT);
        this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTextureAtlas, this, "background.png", 0, 0);
        this.mBackgroundTextureAtlas.load();

        this.mFont = new BitmapFont(this.getTextureManager(), this.getAssets(), "font/BitmapFont.fnt");
        this.mFont.load();

        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 128, 256);
        this.mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "bug_tiled.png", 0, 0, 2, 1);
        this.mCellTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "cell.png", 0, 128, 2, 2);
        this.mBitmapTextureAtlas.load();
    }

    @Override
    protected Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final Text leavesText = new Text(10, 10, this.mFont, "Leaves collected:", this.getVertexBufferObjectManager());
        this.mLeavesText = new Text(350, 10, this.mFont, "00", this.getVertexBufferObjectManager());
        this.mLeavesText.setColor(Color.GREEN);

        this.mScene = new Board(this.mConfiguration) {
            @Override
            protected void onCurrentCellChanged(Cell newCell, Cell oldCell) {
                mPlayer.animateTo(newCell.getX(), newCell.getY());
                mLeavesText.setText(String.valueOf(mScene.getMarkedCellCount() + 1));
            }
        };
        this.mScene.initializeCells(this.mCellTextureRegion, this.getVertexBufferObjectManager());

        this.mPlayer = new Player(-100, -100, this.mPlayerTextureRegion, this.getVertexBufferObjectManager());
        this.mScene.attachChild(this.mPlayer);
        this.mScene.tryMove(0, 0);

        final Sprite backgroundSprite = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, mBackgroundTextureRegion, this.getVertexBufferObjectManager()) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };

        Scene scene = new Scene();
        scene.attachChild(backgroundSprite);
        scene.attachChild(leavesText);
        scene.attachChild(this.mLeavesText);
        scene.setChildScene(this.mScene);
        this.mScene.setBackgroundEnabled(false);
        return scene;
    }
}
