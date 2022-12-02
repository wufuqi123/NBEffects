package cn.wufuqi.nbeffects.filter

import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.R
import cn.wufuqi.nbeffects.bean.Vec2
import cn.wufuqi.nbeffects.bean.Vec3
import cn.wufuqi.nbeffects.utils.BitmapUtils

/**
 * 雨滴效果
 */
class RainFilter : BaseFilter(R.raw.rain_filter_fragment_shader) {


    var time = 0f

    override var bitmap: Bitmap
        get() = super.bitmap
        set(value) {
            super.bitmap = BitmapUtils.getRotateAndFlipBitmap(value, 180f)
        }


    override fun onSurfaceChanged(
        width: Int,
        height: Int,
        bitmapWidth: Int,
        bitmapHeight: Int,
        renderType: OpenGLImageRenderType
    ) {
        super.onSurfaceChanged(width, height, bitmapWidth, bitmapHeight, renderType)
        setUniform("iResolution", Vec3(width.toFloat(), height.toFloat(), 0f))
        setUniform("texSize", Vec2(width.toFloat(), height.toFloat()))
    }


    override fun onUpdateDrawFrame(dt: Long) {
        if (time >= 20f) {
            time = 0.0f
        }
        time += dt / 800f
        setUniform("iTime", time)
    }

}