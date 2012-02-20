package mk.tm.android.tiled;

import mk.tm.android.tiled.config.BoardConfiguration;
import mk.tm.android.tiled.config.GameConstants;
import mk.tm.android.tiled.entity.Board;
import mk.tm.android.tiled.entity.CellState;
import mk.tm.android.tiled.entity.Cell;
import mk.tm.android.tiled.entity.Player;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.EmptyBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.LinearGradientFillBitmapTextureAtlasSourceDecorator;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.RectangleBitmapTextureAtlasSourceDecoratorShape;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import java.util.List;

public class BoardActivity extends SimpleBaseGameActivity implements GameConstants {
    // ===========================================================
    // Constants
    // ===========================================================

    private Board mScene;

    private BitmapTextureAtlas mBitmapTextureAtlas;
    private ITiledTextureRegion mPlayerTextureRegion;
    private ITiledTextureRegion mCellTextureRegion;

    private BitmapTextureAtlas mBackgroundTextureAtlas;
    private ITextureRegion mBackgroundTextureRegion;

    private Player mPlayer;

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
        this.mBackgroundTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 2, CAMERA_HEIGHT);
        this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTextureAtlas, this, "background.png", 0, 0);
        this.mBackgroundTextureAtlas.load();

        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 128, 256);
        this.mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "bug_tiled.png", 0, 0, 2, 1);
        this.mCellTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "cell.png", 0, 128, 2, 2);
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
                newCell.setState(CellState.MARKED);
                mPlayer.animateTo(newCell.getX(), newCell.getY());
            }
        };
        this.mScene.initializeCells();

        this.mPlayer = new Player(-100, -100, this.mPlayerTextureRegion, this.getVertexBufferObjectManager());
        this.mScene.attachChild(this.mPlayer);
        this.mScene.tryMove(0, 0);

        final Sprite sprite = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, mBackgroundTextureRegion, this.getVertexBufferObjectManager()){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);

                pGLState.enableDither();
            }
        };


        //this.mScene.setBackground(new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, getTextureManager(), AssetBitmapTextureAtlasSource.create(this.getAssets(), "gfx/background.jpg"), this.getVertexBufferObjectManager()));

        Scene scene = new Scene();
        scene.attachChild(sprite);
        scene.setChildScene(this.mScene);
        this.mScene.setBackgroundEnabled(false);
        return scene;
    }


    private Cell currentTouched;

    private Cell createField(int cellX, int cellY, CellState state) {

        final float x = cellX * (TILE_WIDTH + TILE_SPACING);
        final float y = cellY * (TILE_HEIGHT + TILE_SPACING);

        final Cell cell = new Cell(x, y, this.mCellTextureRegion, this.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (currentTouched != this) {
                    mScene.tryMove(this);
                    currentTouched = this;
                }
                return true;
            }
        };
        return cell;
    }
}
