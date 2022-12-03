package cn.wufuqi.nbeffects.filter

import android.graphics.Bitmap
import cn.wufuqi.nbeffects.NBEffectsImageView
import cn.wufuqi.nbeffects.OpenGLImageRenderType

interface RendererFilter {

    /**
     *  bitmap
     */
    var bitmap: Bitmap

    /**
     * 创建回调
     */
    fun onSurfaceCreated()

    /**
     * 宽高改变回调
     *
     * @param width
     * @param height
     */
    fun onSurfaceChanged(
        width: Int,
        height: Int,
        bitmapWidth: Int,
        bitmapHeight: Int,
        renderType: OpenGLImageRenderType,
        view: NBEffectsImageView?
    )

    /**
     * 绘制回调
     */
    fun onDrawFrame()

    /**
     * 销毁回调
     */
    fun onDestroy()
}