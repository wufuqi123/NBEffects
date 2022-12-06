package cn.wufuqi.nbeffects.pixijs_filter

import cn.wufuqi.nbeffects.R
import cn.wufuqi.nbeffects.filter.BaseFilter
import kotlin.math.max


class AdjustmentFilter : BaseFilter(R.raw.adjustment_filter_fragment_shader) {
    /** The amount of luminance */
    var gamma = 1f

    /** The amount of saturation */
    var saturation = 1f

    /** The amount of contrast */
    var contrast = 1f

    /** The amount of brightness */
    var brightness = 1f

    /** The amount of red channel */
    var red = 1f

    /** The amount of green channel */
    var green = 1f

    /** The amount of blue channel */
    var blue = 1f

    /** The amount of alpha channel */
    var alpha = 1f


    override fun onUpdateDrawFrame(dt: Long) {
        setUniform("gamma", max(gamma, 0.0001f))
        setUniform("saturation", saturation)
        setUniform("contrast", contrast)
        setUniform("brightness", brightness)
        setUniform("red", red)
        setUniform("green", green)
        setUniform("blue", blue)
        setUniform("alpha", alpha)
    }
}