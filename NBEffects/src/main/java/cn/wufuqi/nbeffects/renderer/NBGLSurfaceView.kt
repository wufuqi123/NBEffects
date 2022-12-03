package cn.wufuqi.nbeffects.renderer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.opengl.GLES10
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.filter.RendererFilter
import cn.wufuqi.nbeffects.utils.BitmapUtils

class NBGLSurfaceView : GLSurfaceView, GLRender {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setupSurfaceView()
    }

    private val queueEventRunnableList = mutableListOf<Runnable?>()


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
    override fun setOpenGLImageRenderType(type: OpenGLImageRenderType) {
        queueEvent {
            renderType = type
            mGLRender.setOpenGLImageRenderType(type)
            mGLRender.setFilter(mGLRender.getFilter())
        }
    }

    override fun setImageBitmap(
        bitmap: Bitmap,
        type: OpenGLImageRenderType
    ) {
        queueEvent {
            mGLRender.setImage(bitmap)
            mGLRender.setOpenGLImageRenderType(type)
            mGLRender.setFilter(mGLRender.getFilter())
        }
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


    private fun setupSurfaceView() {
        //设置版本
        setEGLContextClientVersion(3)

        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
//        setTranslucent()
        mGLRender = FilterRenderer()

        setRenderer(mGLRender)
        isAddRender = true

    }

    /**
     * 设置滤镜
     * 滤镜由于可能存在多种类型
     * 这里抽象了一个基础的滤镜类
     * queueEvent
     *
     * @param baseFilter
     */
    override fun setFilter(filter: RendererFilter?) {
        queueEvent {
            mGLRender.setFilter(filter)
            requestRender()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        requestRender()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mGLRender.destroy()
    }

    override fun requestRender() {
        try {
            queueEvent {
                super.requestRender()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun queueEvent(r: Runnable?) {
        if (isAddRender) {
            try {
                super.requestRender()
            } catch (e: Exception) {
                queueEventRunnableList.add(r)
                e.printStackTrace()
                return
            }

            queueEventRunnableList.forEach {
                super.queueEvent(it)
            }
            queueEventRunnableList.clear()
            super.queueEvent(r)
        } else {
            queueEventRunnableList.add(r)
        }
    }


    /**
     * 设置透明背景的方法
     * 设置后   会被置顶
     */
    override fun setTranslucent(translucent:Boolean){
        holder.setFormat(PixelFormat.TRANSLUCENT)
        // 将GLSurfaceView置顶
        setZOrderOnTop(translucent)
    }



}