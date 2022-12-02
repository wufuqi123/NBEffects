package cn.wufuqi.nbeffects.renderer

import android.graphics.Bitmap
import android.opengl.GLSurfaceView
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.filter.BaseFilter
import cn.wufuqi.nbeffects.filter.RendererFilter
import cn.wufuqi.nbeffects.utils.BitmapUtils
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class FilterRenderer : GLSurfaceView.Renderer {


    private var mSurfaceWidth = 0
    private var mSurfaceHeight: Int = 0

    private var mTargetFilter: RendererFilter = BaseFilter()

    var bitmap: Bitmap = BitmapUtils.getTransparentBitmap()
    var renderType = OpenGLImageRenderType.FLEX

    var bitmapWidth = bitmap.width
    var bitmapHeight = bitmap.width

    fun setImage(bitmap: Bitmap) {
        this.bitmap = bitmap
        bitmapWidth = bitmap.width
        bitmapHeight = bitmap.height
        mTargetFilter.bitmap = bitmap
    }

    fun setOpenGLImageRenderType(type: OpenGLImageRenderType) {
        renderType = type
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        mTargetFilter.onSurfaceCreated()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        mSurfaceWidth = width
        mSurfaceHeight = height
        mTargetFilter.onSurfaceChanged(width, height, bitmapWidth, bitmapHeight, renderType)
    }

    override fun onDrawFrame(gl: GL10?) {
        mTargetFilter.onDrawFrame()
    }

    fun setFilter(filter: RendererFilter?) {
        mTargetFilter.onDestroy()
        mTargetFilter = filter ?: BaseFilter()
        mTargetFilter.bitmap = bitmap
        mTargetFilter.onSurfaceCreated()
        mTargetFilter.onSurfaceChanged(
            mSurfaceWidth,
            mSurfaceHeight,
            bitmapWidth,
            bitmapHeight,
            renderType
        )
    }

    fun getFilter():RendererFilter{
        return mTargetFilter
    }

    fun destroy(){
        mTargetFilter.onDestroy()
    }

}