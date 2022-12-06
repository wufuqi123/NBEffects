package cn.wufuqi.nbeffects.filter

import cn.wufuqi.nbeffects.R

/**
 * 高斯模糊
 */
class GaussBlursFilter: BaseFilter(R.raw.gauss_blurs_filter_fragment_shader) {

    var blur = 2f
    override fun onUpdateDrawFrame(dt: Long) {
        setUniform("bluramount", blur)
    }
}