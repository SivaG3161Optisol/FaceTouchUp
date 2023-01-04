package com.optisol.facetouchup.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect


/**
 * Created by Siva G Gurusamy on 04,January,2023
 * Organization   : OptiSol Business Solutions Pvt Ltd
 * Official Email : siva.g@optisolbusiness.com
 */
object BitmapUtils {
    fun getBitmapByAssetsName(context: Context, name: String): Bitmap? {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        options.inMutable = true
        return BitmapFactory.decodeStream(context.assets.open(name), Rect(), options)
    }
}