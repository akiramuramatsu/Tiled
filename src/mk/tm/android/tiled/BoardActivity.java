package mk.tm.android.tiled;

import mk.tm.android.tiled.board.Position;
import mk.tm.android.tiled.ui.BoardScene;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class BoardActivity extends SimpleBaseGameActivity {
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int CAMERA_WIDTH = 560;
    private static final int CAMERA_HEIGHT = 720;

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, EngineOptions.ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    protected void onCreateResources() {

    }

    @Override
    protected Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        BoardScene scene = null;
        try {
            scene = new BoardScene(5, 7, new Position(0, 0), null);
            scene.init(this.getVertexBufferObjectManager());
        } catch (Exception e) {
            e.printStackTrace();
        }

        scene.setBackground(new Background(0, 0, 0));

        /* Create the rectangles. */

        return scene;
    }
}
