package cn.wufuqi.nbeffects.filter

import cn.wufuqi.nbeffects.R
import cn.wufuqi.nbeffects.bean.Vec2

/**
 * 波浪流动效果
 */
class WaveFilter : BaseFilter(R.raw.wave_filter_fragment_shader) {


    private var offsetX: Float = 0.2f
    private var offsetY: Float = 0.2f

    var time = 0.0f

    init {
        setUniform("iOffset", Vec2(offsetX, offsetY))
    }

    override fun onUpdateDrawFrame(dt:Long) {
        if (time >= Float.MAX_VALUE - 1) {
            time = 0.0f
        }
        time += dt / 800f
        setUniform("iTime", time)
    }
}