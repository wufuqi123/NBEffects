package cn.wufuqi.nbeffects.filter

import cn.wufuqi.nbeffects.R


/**
 * window 方块 切换动画
 */
class WindowTransferFilter : BaseFilter(R.raw.window_transfer_filter_fragment_shader) {

    /**
     * 0 - 1
     * 0 - 2
     */
    var transfer = 0f


    fun transferAmin(time: Long, callback: (() -> Unit)? = null) {
        aminFilterValue(time, "transfer", 2f, callback)
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
        setUniform("transfer", 2 * transfer)
    }
}