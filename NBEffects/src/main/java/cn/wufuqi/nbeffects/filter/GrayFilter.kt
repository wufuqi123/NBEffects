package cn.wufuqi.nbeffects.filter

import cn.wufuqi.nbeffects.R
import cn.wufuqi.nbeffects.bean.Vec3

/**
 * 置灰
 */
class GrayFilter : BaseFilter(R.raw.gray_filter_fragment_shader) {
    init {
        // 设置灰色滤镜
        setUniform("color", Vec3(0.299f, 0.587f, 0.114f))
    }
}