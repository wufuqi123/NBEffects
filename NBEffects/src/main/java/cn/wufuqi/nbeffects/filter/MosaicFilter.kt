package cn.wufuqi.nbeffects.filter

import cn.wufuqi.nbeffects.NBEffectsImageView
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.R
import cn.wufuqi.nbeffects.bean.Vec3

/**
 * 方形马赛克
 */
class MosaicFilter : BaseFilter(R.raw.mosaic_filter_fragment_shader) {


    /**
     *
     * 方形马赛克的宽高大小  px
     * 0 - 屏幕大小
     */
    var mosaicSize = 0f


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

    override fun onUpdateDrawFrame(dt: Long) {
        setUniform("mosaicSize", mosaicSize)
    }
}