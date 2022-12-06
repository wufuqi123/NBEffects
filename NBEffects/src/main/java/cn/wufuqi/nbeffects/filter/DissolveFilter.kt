package cn.wufuqi.nbeffects.filter

import cn.wufuqi.nbeffects.R

/**
 * 溶解的效果
 */
class DissolveFilter : BaseFilter(R.raw.dissolve_filter_fragment_shader) {



    /**
     * 溶解的值  0-1
     */
    var dissolve: Float = 1f

    /**
     * 渐入
     */
    fun fadeIn(time: Long, callback: (() -> Unit)? = null) {
        aminFilterValue(time,"dissolve",1f,callback)
    }

    /**
     * 渐出
     */
    fun fadeOut(time: Long, callback: (() -> Unit)? = null) {
        aminFilterValue(time,"dissolve",-0.1f,callback)
    }

    /**
     * 指定到某个溶解值
     */
    fun fadeTo(time: Long, dissolve: Float, callback: (() -> Unit)? = null) {
        aminFilterValue(time,"dissolve",dissolve,callback)
    }

    override fun onUpdateDrawFrame(dt: Long) {
        super.onUpdateDrawFrame(dt)
        setUniform("dissolve", 1 - dissolve)
    }
}