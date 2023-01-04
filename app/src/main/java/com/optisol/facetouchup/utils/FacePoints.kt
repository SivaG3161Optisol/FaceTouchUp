package com.optisol.facetouchup.utils

import android.content.Context
import android.graphics.Path
import android.graphics.Point
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


/**
 * Created by Siva G Gurusamy on 04,January,2023
 * Organization   : OptiSol Business Solutions Pvt Ltd
 * Official Email : siva.g@optisolbusiness.com
 */
object FacePoints {

    fun getMouthPath(faceJson: String?): Path {
        val jsonObject = JSONObject(faceJson)
        val mouthJson =
            jsonObject.getJSONObject("face").getJSONObject("landmark").getJSONObject("mouth")
        val outPath = Path()
        val inPath = Path()
        val start = getPointByJson(mouthJson.getJSONObject("upper_lip_0"))
        outPath.moveTo(start.x.toFloat(), start.y.toFloat())
        for (i in 1..45) {
            val pointByJson = getPointByJson(
                mouthJson.getJSONObject(
                    "upper_lip_$i"
                )
            )
            outPath.lineTo(pointByJson.x.toFloat(), pointByJson.y.toFloat())
        }
        for (i in 46 downTo 1) {
            val pointByJson = getPointByJson(
                mouthJson.getJSONObject(
                    "lower_lip_$i"
                )
            )
            outPath.lineTo(pointByJson.x.toFloat(), pointByJson.y.toFloat())
        }
        outPath.close()
        val inStart = getPointByJson(mouthJson.getJSONObject("upper_lip_63"))
        inPath.moveTo(inStart.x.toFloat(), inStart.y.toFloat())
        for (i in 46..63) {
            val pointByJson = getPointByJson(
                mouthJson.getJSONObject(
                    "upper_lip_$i"
                )
            )
            inPath.lineTo(pointByJson.x.toFloat(), pointByJson.y.toFloat())
        }
        for (i in 63 downTo 46) {
            val pointByJson = getPointByJson(
                mouthJson.getJSONObject(
                    "lower_lip_$i"
                )
            )
            inPath.lineTo(pointByJson.x.toFloat(), pointByJson.y.toFloat())
        }

        outPath.op(inPath, Path.Op.DIFFERENCE)
        return outPath
    }

    fun getBlush(faceJson: String?): Path {
        var jsonObject: JSONObject? = null
        jsonObject = JSONObject(faceJson)
        val eye =
            jsonObject.getJSONObject("face").getJSONObject("landmark").getJSONObject("face")
        val path = Path()
        val start = getPointByJson(eye.getJSONObject("face_contour_left_0"))
        path.moveTo(start.x.toFloat(), start.y.toFloat())
        for (i in 1..63) {
            val point = getPointByJson(eye.getJSONObject("face_contour_left_$i"))
            path.lineTo(point.x.toFloat(), point.y.toFloat())
        }
        val leftTop = getPointByJson(eye.getJSONObject("face_contour_left_63"))
        val rightTop = getPointByJson(eye.getJSONObject("face_contour_right_63"))
        path.moveTo((leftTop.x + rightTop.x) / 2.0f, leftTop.y.toFloat())
        path.close()
        return path
    }

    fun getPointByJson(jsonObject: JSONObject): Point {
        return Point(jsonObject.optInt("x"), jsonObject.optInt("y"))
    }

    fun getFaceJson(context: Context, facePointJson: String?): String {
        var input: InputStream? = null
        try {
            input = context.assets.open(facePointJson!!)
            val byteArrayOutputStream = ByteArrayOutputStream()
            val bytes = ByteArray(8 * 1024)
            var len: Int
            while (input.read(bytes).also { len = it } != -1) {
                byteArrayOutputStream.write(bytes, 0, len)
            }
            return byteArrayOutputStream.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return ""
    }
}
