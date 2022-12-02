package cn.wufuqi.nbeffects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import cn.wufuqi.nbeffects.filter.RendererFilter
import cn.wufuqi.nbeffects.renderer.FilterRenderer
import cn.wufuqi.nbeffects.utils.BitmapUtils


class NBEffectsImageView : GLSurfaceView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setupSurfaceView()
    }


    private lateinit var mGLRender: FilterRenderer

    var renderType = OpenGLImageRenderType.AUTO_NO_BACK

    var isAddRender = false


    /**
     * 设置渲染类型
     *
     * OpenGLImageRenderType.FLEX   等高等宽拉伸
     * EQUAL_WIDTH                  等宽的
     * EQUAL_HEIGHT                 等高的
     * AUTO_NO_BACK                 自适应，不留黑边  （默认）
     */
    fun setOpenGLImageRenderType(type: OpenGLImageRenderType) {
        renderType = type
        mGLRender.setOpenGLImageRenderType(type)
        requestRender()
    }

    fun setImageBitmap(
        bitmap: Bitmap,
        type: OpenGLImageRenderType = renderType
    ) {
        mGLRender.setImage(bitmap)
        mGLRender.setOpenGLImageRenderType(type)
        requestRender()
    }

    fun setImageDrawable(
        drawable: Drawable,
        type: OpenGLImageRenderType = renderType
    ) {
        setImageBitmap(BitmapUtils.drawableToBitmap(drawable), type)
    }


    fun setImageRes(
        resourceId: Int,
        type: OpenGLImageRenderType = renderType
    ) {
        setImageBitmap(BitmapUtils.getBitmap(resourceId)!!, type)
    }


    private fun setupSurfaceView() {
        //设置版本
        setEGLContextClientVersion(3)
        mGLRender = FilterRenderer()
    }

    /**
     * 设置滤镜
     * 滤镜由于可能存在多种类型
     * 这里抽象了一个基础的滤镜类
     * queueEvent
     *
     * @param baseFilter
     */
    fun setFilter(filter: RendererFilter?) {
        mGLRender.setFilter(filter)
        requestRender()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setRenderer(mGLRender)
        isAddRender = true
        requestRender()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        setRenderer(null)
        isAddRender = false
        mGLRender.destroy()
    }

    override fun requestRender() {
        try {
            if (isAddRender) {
                super.requestRender()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}