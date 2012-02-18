package mk.tm.android.tiled;

import mk.tm.android.tiled.entity.Board;
import mk.tm.android.tiled.entity.BoardConfiguration;
import mk.tm.android.tiled.entity.CellState;
import mk.tm.android.tiled.entity.Cell;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import java.util.List;

public class BoardActivity extends SimpleBaseGameActivity implements GameConstants {
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int CAMERA_WIDTH = 560;
    private static final int CAMERA_HEIGHT = 720;

    private Board mScene;

    private BitmapTextureAtlas mBitmapTextureAtlas;
    private ITiledTextureRegion mHeroTextureRegion;

    private BitmapTextureAtlas mBackgroundTexture;
    private ITextureRegion mBackgroundTextureRegion;

    private AnimatedSprite mPlayer;

    private static final int LAYER_COUNT = 4;

    private static final int LAYER_BACKGROUND = 0;
    private static final int LAYER_FIELDS = LAYER_BACKGROUND + 1;
    private static final int LAYER_HERO = LAYER_FIELDS + 1;
    private static final int LAYER_MESSAGE = LAYER_HERO + 1;

    private List<Cell> mFields;

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, EngineOptions.ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    protected void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        /* Load all the textures this game needs. */
        this.mBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT);
        this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "background.jpg", 0, 0);
        this.mBackgroundTexture.load();

        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 128, 64);
        this.mHeroTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "bug_tiled.png", 0, 0, 2, 1);
        this.mBitmapTextureAtlas.load();
    }

    @Override
    protected Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        this.mScene = new Board(new BoardConfiguration()) {
            @Override
            protected Cell onCreateCell(int cellX, int cellY, CellState state) {

                Cell cell = createField(cellX, cellY, state);
                mScene.attachChild(cell);
                mScene.registerTouchArea(cell);

                return cell;
            }

            @Override
            protected void onCurrentCellChanged(Cell newCell, Cell oldCell) {
                newCell.setColor(Color.BLACK);
                mPlayer.setPosition(newCell.getX(), newCell.getY());
            }
        };
        this.mScene.attachChild(new Sprite(0, 0, this.mBackgroundTextureRegion, this.getVertexBufferObjectManager()));
        this.mScene.initializeCells();

        this.mPlayer = new AnimatedSprite(0, 0, this.mHeroTextureRegion, this.getVertexBufferObjectManager());
        this.mPlayer.animate(100);
        this.mScene.attachChild(this.mPlayer);



        this.mScene.setBackground(new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, getTextureManager(), AssetBitmapTextureAtlasSource.create(this.getAssets(), "gfx/background.jpg"), this.getVertexBufferObjectManager()));
        this.mScene.setPosition(10, 10);

        return this.mScene;
    }


    private Cell currentTouched;

    private Cell createField(int cellX, int cellY, CellState state) {

        final float x = cellX * (TILE_WIDTH+ TILE_SPACING);
        final float y = cellY * (TILE_HEIGHT + TILE_SPACING);

        final Cell cell = new Cell(x, y, TILE_WIDTH, TILE_HEIGHT, this.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (currentTouched != this) {
                    mScene.tryMove(this);
                    currentTouched = this;
                }
                return true;
            }
        };

        cell.setCell(cellX, cellY);
        cell.setState(state);
        return cell;
    }
}
