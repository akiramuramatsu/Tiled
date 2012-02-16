package mk.tm.android.tiled;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.entity.util.ScreenCapture;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.FileUtils;

public class MyActivity extends SimpleBaseGameActivity {
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int CAMERA_WIDTH = 480;
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

        final Scene scene = new Scene();
        final ScreenCapture screenCapture = new ScreenCapture();
        scene.attachChild(screenCapture);
        scene.setOnSceneTouchListener(new Scene.IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
                if (pSceneTouchEvent.isActionDown()) {
                    screenCapture.capture(180, 60, 360, 360, FileUtils.getAbsolutePathOnExternalStorage(MyActivity.this, "Screen_" + System.currentTimeMillis() + ".png"), new ScreenCapture.IScreenCaptureCallback() {

                        public void onScreenCaptured(final String pFilePath) {
                            MyActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyActivity.this, "Screenshot: " + pFilePath + " taken!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        public void onScreenCaptureFailed(final String pFilePath, final Exception pException) {
                            MyActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyActivity.this, "FAILED capturing Screenshot: " + pFilePath + " !", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                return true;
            }
        });
        scene.setBackground(new Background(0, 0, 0));

        /* Create the rectangles. */
        final Rectangle rect1 = this.makeColoredRectangle(-180, -180, 1, 0, 0);
        final Rectangle rect2 = this.makeColoredRectangle(0, -180, 0, 1, 0);
        final Rectangle rect3 = this.makeColoredRectangle(0, 0, 0, 0, 1);
        final Rectangle rect4 = this.makeColoredRectangle(-180, 0, 1, 1, 0);

        final Entity rectangleGroup = new Entity(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2);

        rectangleGroup.attachChild(rect1);
        rectangleGroup.attachChild(rect2);
        rectangleGroup.attachChild(rect3);
        rectangleGroup.attachChild(rect4);

        scene.attachChild(rectangleGroup);

        return scene;
    }

    private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
        final Rectangle coloredRect = new Rectangle(pX, pY, 180, 180, this.getVertexBufferObjectManager());
        coloredRect.setColor(pRed, pGreen, pBlue);
        return coloredRect;
    }
}
