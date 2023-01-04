package com.optisol.facetouchup.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import com.optisol.facetouchup.draw.BlushDraw
import com.optisol.facetouchup.draw.LipDraw


/**
 * Created by Siva G Gurusamy on 04,January,2023
 * Organization   : OptiSol Business Solutions Pvt Ltd
 * Official Email : siva.g@optisolbusiness.com
 */
class DrawUtils {
    private val makeupList: MutableList<Region> = ArrayList()
    fun init() {
        makeupList.add(Region.FOUNDATION)
        makeupList.add(Region.LIP)
        makeupList.add(Region.BLUSH)
    }

    fun draw(
        context: Context,
        list: List<Region?>,
        originBitmap: Bitmap?,
        facePointJson: String?
    ) {
        if (originBitmap == null) return
        val canvas = Canvas(originBitmap)
        val faceJson: String = FacePoints.getFaceJson(context, facePointJson)
        for (region in list) {
            when (region) {
                Region.BLUSH -> {
                    val blush: Bitmap? =
                        BitmapUtils.getBitmapByAssetsName(context, "face_blush.png")
                    BlushDraw.drawBlush(canvas, blush, FacePoints.getBlush(faceJson), 100)
                }
                Region.LIP -> {
                    val mouthPath: Path = FacePoints.getMouthPath(faceJson)
                    LipDraw.drawLipPerfect(canvas, mouthPath, Color.parseColor("#144A47"), 120)
                }
                else -> return
            }
        }
    }

    fun getMakeupList(): List<Region> {
        return makeupList
    }
}
