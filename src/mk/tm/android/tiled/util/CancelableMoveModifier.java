package mk.tm.android.tiled.util;

import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.util.modifier.ease.IEaseFunction;

/**
 * Created by IntelliJ IDEA.
 * User: Tome
 * Date: 18.2.12
 * Time: 13:09
 * To change this template use File | Settings | File Templates.
 */
public class CancelableMoveModifier extends MoveModifier {
    public CancelableMoveModifier(final float pDuration, final float pFromX, final float pToX, final float pFromY, final float pToY) {
        super(pDuration, pFromX, pToX, pFromY, pToY);
    }

    public CancelableMoveModifier(final float pDuration, final float pFromX, final float pToX, final float pFromY, final float pToY, final IEaseFunction pEaseFunction) {
        super(pDuration, pFromX, pToX, pFromY, pToY, pEaseFunction);
    }

    public CancelableMoveModifier(final float pDuration, final float pFromX, final float pToX, final float pFromY, final float pToY, final IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromX, pToX, pFromY, pToY, pEntityModifierListener);
    }

    public CancelableMoveModifier(final float pDuration, final float pFromX, final float pToX, final float pFromY, final float pToY, final IEntityModifierListener pEntityModifierListener, final IEaseFunction pEaseFunction) {
        super(pDuration, pFromX, pToX, pFromY, pToY, pEntityModifierListener, pEaseFunction);
    }

    protected CancelableMoveModifier(final MoveModifier pMoveModifier) {
        super(pMoveModifier);
    }

    public void cancel() {
        this.mFinished = true;
    }
}
