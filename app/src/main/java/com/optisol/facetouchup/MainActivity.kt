package com.optisol.facetouchup

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.optisol.facetouchup.utils.BitmapUtils
import com.optisol.facetouchup.utils.DrawUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadImg()
    }
    private fun loadImg() {
        val bitmap: Bitmap? = BitmapUtils.getBitmapByAssetsName(this, "samantha.png")
        if (bitmap != null) {
            drawMakeup(bitmap)
        }
    }

    private fun drawMakeup(bitmap: Bitmap) {
        val drawUtils = DrawUtils()
        drawUtils.init()
        drawUtils.draw(this, drawUtils.getMakeupList(), bitmap, "face_points.json")
        findViewById<ImageView>(R.id.imgView).setImageBitmap(bitmap)
    }
}