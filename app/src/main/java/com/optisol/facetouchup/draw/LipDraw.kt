package com.optisol.facetouchup.draw

import android.graphics.*


/**
 * Created by Siva G Gurusamy on 04,January,2023
 * Organization   : OptiSol Business Solutions Pvt Ltd
 * Official Email : siva.g@optisolbusiness.com
 */
object LipDraw {
    fun alphaColor(color: Int, alpha: Int): Int {
        return color and 0x00FFFFFF or alpha
    }

    fun drawLipPerfect(canvas: Canvas, lipPath: Path?, color: Int, alpha: Int) {
        //most 70% alpha
        var color = color
        var alpha = alpha
        if (alpha > 80) {
            alpha = (alpha * 0.9f + 0.5f).toInt()
        }
        alpha = (Color.alpha(color) * (alpha.toFloat() / 255)).toInt() shl 24
        color = alphaColor(color, alpha)
        val position = PointF()
        val blur_radius = 5f
        val mask = createMask(lipPath, color, blur_radius, position)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
        canvas.drawBitmap(mask!!, position.x, position.y, paint)
    }

    /**
     * create closed path and filled with specified color.
     *
     * @param path
     * @param color       the color of filled region.
     * @param blur_radius Blur radius, use it to get smooth mask.
     * @param position    stores the mask top-left position if `position` is not null.
     * @return mask
     */
    fun createMask(path: Path?, color: Int, blur_radius: Float, position: PointF?): Bitmap? {
        if (path == null || path.isEmpty) return null
        val bounds = RectF()
        path.computeBounds(bounds, true)
        bounds.inset(-blur_radius, -blur_radius)
        val width = bounds.width().toInt()
        val height = bounds.height().toInt()
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888) // mutable
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.maskFilter = BlurMaskFilter(blur_radius, BlurMaskFilter.Blur.NORMAL)
        paint.color = color
        paint.style = Paint.Style.FILL
        path.offset(-bounds.left, -bounds.top)
        canvas.drawPath(path, paint)
        if (position != null) {
            position.x = bounds.left
            position.y = bounds.top
        }
        return bitmap
    }
}