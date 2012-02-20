package mk.tm.android.tiled.entity;

import mk.tm.android.tiled.config.GameConstants;
import mk.tm.android.tiled.util.CancelableMoveModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.ease.EaseCircularInOut;
import org.andengine.util.modifier.ease.EaseCircularOut;

/**
 * Created by IntelliJ IDEA.
 * User: Tome
 * Date: 18.2.12
 * Time: 12:22
 * To change this template use File | Settings | File Templates.
 */
public class Player extends AnimatedSprite implements GameConstants, AnimatedSprite.IAnimationListener {
    private CancelableMoveModifier mModifier;

    public Player(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }

    @Override
    public AnimatedSprite animate(long pFrameDurationEach, int pLoopCount) {
        return super.animate(pFrameDurationEach, pLoopCount, this);
    }

    public void animateTo(float pToX, float pToY) {
        this.animate(50, 4);

        if (this.mModifier != null)
            this.mModifier.cancel();
        float translateX = pToX + (TILE_WIDTH - getWidth()) / 2;
        float translateY = pToY + (TILE_WIDTH - getWidth()) / 2;
        this.mModifier = new CancelableMoveModifier(0.4f, getX(), translateX, getY(), translateY, EaseCircularOut.getInstance());
        this.registerEntityModifier(this.mModifier);
    }

    /**
     * @param pAnimatedSprite
     * @param pInitialLoopCount is {@link org.andengine.entity.sprite.AnimatedSprite#LOOP_CONTINUOUS} when {@link org.andengine.entity.sprite.AnimatedSprite} loops infinitely.
     */
    @Override
    public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @param pAnimatedSprite
     * @param pOldFrameIndex  equals {@link org.andengine.entity.sprite.AnimatedSprite#FRAMEINDEX_INVALID}, the first time {@link org.andengine.entity.sprite.AnimatedSprite.IAnimationListener#onAnimationFrameChanged(org.andengine.entity.sprite.AnimatedSprite, int, int)} is called.
     * @param pNewFrameIndex  the new frame index of the currently active animation.
     */
    @Override
    public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @param pAnimatedSprite
     * @param pRemainingLoopCount is {@link org.andengine.entity.sprite.AnimatedSprite#LOOP_CONTINUOUS} when {@link org.andengine.entity.sprite.AnimatedSprite} loops infinitely.
     * @param pInitialLoopCount   is {@link org.andengine.entity.sprite.AnimatedSprite#LOOP_CONTINUOUS} when {@link org.andengine.entity.sprite.AnimatedSprite} loops infinitely.
     */
    @Override
    public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
             this.setCurrentTileIndex(0);
    }
}
