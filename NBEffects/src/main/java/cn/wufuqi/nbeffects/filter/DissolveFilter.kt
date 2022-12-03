package cn.wufuqi.nbeffects.filter

import android.util.Log
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
    fun fadeIn(time: Long, callback: (() -> Unit)? = null) {
        mTween?.remove()
        mTween = TweenManager.builderOne(this)
            .to(mutableMapOf("dissolve" to 1f))
            .time(time)
            .on(TweenManager.EVENT_END) {
                callback?.invoke()
            }
            .start()
    }

    /**
     * 渐出
     */
    fun fadeOut(time: Long, callback: (() -> Unit)? = null) {
        mTween?.remove()
        mTween = TweenManager.builderOne(this)
            .to(mutableMapOf("dissolve" to -0.1f))
            .time(time)
            .on(TweenManager.EVENT_END) {
                callback?.invoke()
            }
            .start()
    }

    /**
     * 指定到某个溶解值
     */
    fun fadeTo(time: Long, dissolve: Float, callback: (() -> Unit)? = null) {
        mTween?.remove()
        mTween = TweenManager.builderOne(this)
            .to(mutableMapOf("dissolve" to dissolve))
            .time(time)
            .on(TweenManager.EVENT_END) {
                callback?.invoke()
            }
            .start()
    }

    override fun onUpdateDrawFrame(dt: Long) {
        super.onUpdateDrawFrame(dt)
        Log.e("wufuqi---"," +++++  $dissolve")
        setUniform("dissolve", 1 - dissolve)
    }

    override fun onDestroy() {
        super.onDestroy()
        mTween?.remove()
        mTween = null
    }
}