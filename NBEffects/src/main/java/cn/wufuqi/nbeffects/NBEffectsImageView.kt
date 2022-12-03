package cn.wufuqi.nbeffects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import cn.wufuqi.nbeffects.filter.RendererFilter
import cn.wufuqi.nbeffects.renderer.GLRender
import cn.wufuqi.nbeffects.renderer.NBGLSurfaceView
import cn.wufuqi.nbeffects.renderer.NBTextureView
import cn.wufuqi.nbeffects.utils.BitmapUtils


class NBEffectsImageView : FrameLayout, GLRender {


    var renderType = OpenGLImageRenderType.AUTO_NO_BACK

    lateinit var mGLRender: GLRender

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context) {
        mGLRender = NBGLSurfaceView(context)
        addView(mGLRender as View)
    }


    /**
     * 设置渲染类型
     *
     * OpenGLImageRenderType.FLEX   等高等宽拉伸
     * EQUAL_WIDTH                  等宽的
     * EQUAL_HEIGHT                 等高的
     * AUTO_NO_BACK                 自适应，不留黑边  （默认）
     */
    override fun setOpenGLImageRenderType(type: OpenGLImageRenderType) {
        renderType = type
        mGLRender.setOpenGLImageRenderType(type)
    }

    override fun setImageBitmap(
        bitmap: Bitmap,
        type: OpenGLImageRenderType
    ) {
        mGLRender.setImageBitmap(bitmap, type)
    }

    override fun setImageDrawable(
        drawable: Drawable,
        type: OpenGLImageRenderType
    ) {
        setImageBitmap(BitmapUtils.drawableToBitmap(drawable), type)
    }


    override fun setImageRes(
        resourceId: Int,
        type: OpenGLImageRenderType
    ) {
        setImageBitmap(BitmapUtils.getBitmap(resourceId)!!, type)
    }


    /**
     * 设置滤镜
     */
    override fun setFilter(filter: RendererFilter?) {
        mGLRender.setFilter(filter)
    }

    /**
     * 设置true后  view 将会被置顶显示
     * 设置false  view可能会有黑边
     */
    override fun setTranslucent(translucent: Boolean) {
        mGLRender.setTranslucent(translucent)
    }


}