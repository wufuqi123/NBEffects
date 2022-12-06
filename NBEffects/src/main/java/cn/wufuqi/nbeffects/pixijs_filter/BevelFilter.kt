package cn.wufuqi.nbeffects.pixijs_filter

import android.graphics.Color
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import cn.wufuqi.nbeffects.NBEffectsImageView
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.R
import cn.wufuqi.nbeffects.bean.Vec3
import cn.wufuqi.nbeffects.bean.Vec4
import cn.wufuqi.nbeffects.filter.BaseFilter
import kotlin.math.max

class BevelFilter : BaseFilter(R.raw.bevel_filter_fragment_shader) {
    var lightColor = "#ffffff"
    var shadowColor = "#000000"
    var transformX = 0f
    var transformY = 0f
    var lightAlpha = 1f
    var shadowAlpha = 1f


    override fun onSurfaceChanged(
        width: Int,
        height: Int,
        bitmapWidth: Int,
        bitmapHeight: Int,
        renderType: OpenGLImageRenderType,
        view: NBEffectsImageView?
    ) {
        super.onSurfaceChanged(width, height, bitmapWidth, bitmapHeight, renderType, view)
        setUniform("filterArea", Vec4(width.toFloat(), height.toFloat(), 0f, 0f))
    }

    override fun onUpdateDrawFrame(dt: Long) {
        val lc = Color.parseColor(lightColor)
        val sc = Color.parseColor(shadowColor)

        setUniform(
            "lightColor",
            Vec3(lc.red / 255f, lc.green / 255f, lc.blue / 255f)
        )
        setUniform(
            "shadowColor",
            Vec3(sc.red / 255f, sc.green / 255f, sc.blue / 255f)
        )
        setUniform("transformX", transformX)
        setUniform("transformY", transformY)
        setUniform("lightAlpha", lightAlpha)
        setUniform("shadowAlpha", shadowAlpha)

    }
}