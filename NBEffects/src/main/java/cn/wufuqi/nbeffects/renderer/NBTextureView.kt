package cn.wufuqi.nbeffects.renderer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.TextureView
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.filter.RendererFilter
import cn.wufuqi.nbeffects.utils.BitmapUtils

class NBTextureView : TextureView, GLRender, TextureView.SurfaceTextureListener {


    companion object {
        const val RENDERMODE_WHEN_DIRTY = 0
        const val RENDERMODE_CONTINUOUSLY = 1
    }

    var mRendererMode = RENDERMODE_CONTINUOUSLY

    var renderType = OpenGLImageRenderType.AUTO_NO_BACK
    private var mGLThread: GLESThread? = null
    private val mGLRender: FilterRenderer = FilterRenderer()

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
        surfaceTextureListener = this
    }

    override fun setOpenGLImageRenderType(type: OpenGLImageRenderType) {
        queueEvent {
            renderType = type
            mGLRender.setOpenGLImageRenderType(type)
            mGLRender.setFilter(mGLRender.getFilter())
        }
    }

    override fun setImageBitmap(bitmap: Bitmap, type: OpenGLImageRenderType) {
        queueEvent {
            mGLRender.setImage(bitmap)
            mGLRender.setOpenGLImageRenderType(type)
            mGLRender.setFilter(mGLRender.getFilter())
        }
    }

    override fun setImageDrawable(drawable: Drawable, type: OpenGLImageRenderType) {
        setImageBitmap(BitmapUtils.drawableToBitmap(drawable), type)
    }

    override fun setImageRes(resourceId: Int, type: OpenGLImageRenderType) {
        setImageBitmap(BitmapUtils.getBitmap(resourceId)!!, type)
    }

    override fun setFilter(filter: RendererFilter?) {
        queueEvent {
            mGLRender.setFilter(filter)
        }
        requestRender()
    }

    override fun setTranslucent(translucent: Boolean) {
//        setZOrderOnTop(translucent)
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        mGLThread = GLESThread(
            surface,
            mGLRender
        ) // 创建一个线程，作为GL线程

        mGLThread!!.setRenderMode(mRendererMode)
        mGLThread!!.start()
        mGLThread!!.onSurfaceChanged(width, height)
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        mGLThread?.onSurfaceChanged(width, height)
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        mGLThread?.onDestroy()
        return false
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

    }

    fun queueEvent(r: Runnable?) {
        if (mGLThread != null) {
            mGLThread!!.queueEvent(r)
        } else {
            r?.run()
        }
    }


    fun setRenderMode(mode: Int) {
        mRendererMode = mode
    }

    fun requestRender() {
        mGLThread?.requestRender()
    }

    fun onResume() {
        mGLThread?.onResume()
    }

    fun onPause() {
        mGLThread?.onPause()
    }

    fun onDestroy() {
        mGLThread?.onDestroy()
    }

}