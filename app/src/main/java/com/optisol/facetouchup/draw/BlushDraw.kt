package com.optisol.facetouchup.draw

import android.graphics.*


/**
 * Created by Siva G Gurusamy on 04,January,2023
 * Organization   : OptiSol Business Solutions Pvt Ltd
 * Official Email : siva.g@optisolbusiness.com
 */
object BlushDraw {
    fun drawBlush(canvas: Canvas, faceBlush: Bitmap?, path: Path, alpha: Int) {
        val paint = Paint()
        paint.alpha = alpha
        val rectF = RectF()
        path.computeBounds(rectF, true)
        canvas.drawBitmap(faceBlush!!, null, rectF, paint)
    }
}