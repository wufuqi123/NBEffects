package cn.wufuqi.nbeffects.filter

import cn.wufuqi.nbeffects.R

/**
 * 方条  切换动画
 */
class UnfoldTransferFilter : BaseFilter(R.raw.unfold_transfer_filter_vertex_shader) {


    /**
     * 0 - 1
     * 0 - 2
     */
    var transfer = 0f


    fun transferAmin(time: Long, callback: (() -> Unit)? = null) {
        fadeIn(time / 2) {
            fadeOut(time / 2, callback)
        }
    }


    /**
     * 渐入
     */
    fun fadeIn(time: Long, callback: (() -> Unit)? = null) {
        aminFilterValue(time, "transfer", 1f, callback)
    }

    /**
     * 渐出
     */
    fun fadeOut(time: Long, callback: (() -> Unit)? = null) {
        aminFilterValue(time, "transfer", 0f, callback)
    }

    /**
     * 指定到某个溶解值
     */
    fun fadeTo(time: Long, transfer: Float, callback: (() -> Unit)? = null) {
        aminFilterValue(time, "transfer", transfer, callback)
    }


    override fun onUpdateDrawFrame(dt: Long) {
        var t = transfer
        if (t >= 1) {
            t = 0.999f
        }
        setUniform("time", t)
    }
}