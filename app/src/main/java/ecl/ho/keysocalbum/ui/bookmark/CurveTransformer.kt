package ecl.ho.keysocalbum.ui.bookmark

import android.view.View
import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager

/**
 * Created by chensuilun on 2016/12/16.
 */
class CurveTransformer : GalleryLayoutManager.ItemTransformer {
    override fun transformItem(layoutManager: GalleryLayoutManager, item: View, fraction: Float) {
        if (layoutManager.orientation == GalleryLayoutManager.VERTICAL) {
            return
        }
        val width = item.width
        val height = item.height
        item.pivotX = width / 2f
        item.pivotY = height.toFloat()
        val scale = 1 - 0.1f * Math.abs(fraction)
        item.scaleX = scale
        item.scaleY = scale
        item.rotation = ROTATE_ANGEL * fraction
        item.translationY =
            (Math.sin(2 * Math.PI * ROTATE_ANGEL * Math.abs(fraction) / 360f) * width / 2.0f).toFloat()
        item.translationX =
            ((1 - scale) * width / 2.0f / Math.cos(2 * Math.PI * ROTATE_ANGEL * fraction / 360f)).toFloat()
        if (fraction > 0) {
            item.translationX = -item.translationX
        }
        item.alpha = 1 - 0.2f * Math.abs(fraction)
    }

    companion object {
        const val ROTATE_ANGEL = 7
        private const val TAG = "CurveTransformer"
    }
}
