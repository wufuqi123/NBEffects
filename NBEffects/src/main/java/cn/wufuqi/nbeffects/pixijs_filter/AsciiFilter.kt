package cn.wufuqi.nbeffects.pixijs_filter

import cn.wufuqi.nbeffects.NBEffectsImageView
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.R
import cn.wufuqi.nbeffects.bean.Vec4
import cn.wufuqi.nbeffects.filter.BaseFilter

class AsciiFilter:BaseFilter(R.raw.ascii_filter_fragment_shader) {

    var size = 8f


    override fun onSurfaceChanged(
        width: Int,
        height: Int,
        bitmapWidth: Int,
        bitmapHeight: Int,
        renderType: OpenGLImageRenderType,
        view: NBEffectsImageView?
    ) {
        super.onSurfaceChanged(width, height, bitmapWidth, bitmapHeight, renderType,view)
        setUniform("filterArea", Vec4(width.toFloat(), height.toFloat(), 0f,0f))
    }

    override fun onUpdateDrawFrame(dt: Long) {
        setUniform("pixelSize", size)
    }
}