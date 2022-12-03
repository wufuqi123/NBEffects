package cn.wufuqi.nbeffects.filter

import cn.wufuqi.nbeffects.NBEffectsImageView
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.R
import cn.wufuqi.nbeffects.bean.Vec3

/**
 * 水波纹
 */
class WaterFilter : BaseFilter(R.raw.water_filter_fragment_shader) {


    var time = 0.0f
    var offset = -0.01f

    override fun onSurfaceChanged(
        width: Int,
        height: Int,
        bitmapWidth: Int,
        bitmapHeight: Int,
        renderType: OpenGLImageRenderType,
        view: NBEffectsImageView?
    ) {
        super.onSurfaceChanged(width, height, bitmapWidth, bitmapHeight, renderType, view)
        setUniform("iResolution", Vec3(width.toFloat(), height.toFloat(), 0f))
    }


    override fun setMatrix(left: Float, right: Float, bottom: Float, top: Float) {
        super.setMatrix(left - offset, right + offset, bottom - offset, top + offset)
    }

    override fun onUpdateDrawFrame(dt: Long) {
        if (time >= Float.MAX_VALUE - 1) {
            time = 0.0f
        }
        time += dt / 800f
        setUniform("iTime", time)
    }
}