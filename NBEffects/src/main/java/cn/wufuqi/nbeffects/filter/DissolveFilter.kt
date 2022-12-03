package cn.wufuqi.nbeffects.filter

import cn.wufuqi.nbeffects.R
import com.wukonganimation.tween.Tween
import com.wukonganimation.tween.TweenManager

/**
 * 溶解的效果
 */
class DissolveFilter : BaseFilter(R.raw.dissolve_filter_fragment_shader) {

    private var mTween: Tween? = null


    /**
     * 溶解的值  0-1
     */
    var dissolve: Float = 1f

    /**
     * 渐入
     */
    fun fadeIn(time: Long) {
        mTween?.remove()
        mTween = TweenManager.builderOne(this)
            .to(mutableMapOf("dissolve" to 1f))
            .time(time)
            .start()
    }

    /**
     * 渐出
     */
    fun fadeOut(time: Long) {
        mTween?.remove()
        mTween = TweenManager.builderOne(this)
            .to(mutableMapOf("dissolve" to 0f))
            .time(time)
            .start()
    }

    /**
     * 指定到某个溶解值
     */
    fun fadeTo(time: Long, dissolve: Float) {
        mTween?.remove()
        mTween = TweenManager.builderOne(this)
            .to(mutableMapOf("dissolve" to dissolve))
            .time(time)
            .start()
    }

    override fun onUpdateDrawFrame(dt: Long) {
        super.onUpdateDrawFrame(dt)
        setUniform("dissolve", 1 - dissolve)
    }

    override fun onDestroy() {
        super.onDestroy()
        mTween?.remove()
        mTween = null
    }
}