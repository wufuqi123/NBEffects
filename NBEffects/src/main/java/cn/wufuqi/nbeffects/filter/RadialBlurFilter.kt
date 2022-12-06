package cn.wufuqi.nbeffects.filter

import cn.wufuqi.nbeffects.NBEffectsImageView
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.R
import cn.wufuqi.nbeffects.bean.Vec3

class RadialBlurFilter : BaseFilter(R.raw.radial_blur_filter_fragment_shader){

    var center = 5f


    override fun onSurfaceChanged(
        width: Int,
        height: Int,
        bitmapWidth: Int,
        bitmapHeight: Int,
        renderType: OpenGLImageRenderType,
        view: NBEffectsImageView?
    ) {
        super.onSurfaceChanged(width, height, bitmapWidth, bitmapHeight, renderType,view)
        setUniform("iResolution", Vec3(width.toFloat(), height.toFloat(), 0f))
    }

    override fun onUpdateDrawFrame(dt: Long) {
        setUniform("iCenter", center)
    }
}