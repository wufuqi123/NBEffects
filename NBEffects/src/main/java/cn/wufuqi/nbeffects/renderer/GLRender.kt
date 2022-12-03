package cn.wufuqi.nbeffects.renderer

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.filter.RendererFilter

interface GLRender {

    /**
     * 设置渲染类型
     *
     * OpenGLImageRenderType.FLEX   等高等宽拉伸
     * EQUAL_WIDTH                  等宽的
     * EQUAL_HEIGHT                 等高的
     * AUTO_NO_BACK                 自适应，不留黑边  （默认）
     */
    fun setOpenGLImageRenderType(type: OpenGLImageRenderType)

    fun setImageBitmap(
        bitmap: Bitmap,
        type: OpenGLImageRenderType = OpenGLImageRenderType.AUTO_NO_BACK
    )

    fun setImageDrawable(
        drawable: Drawable,
        type: OpenGLImageRenderType = OpenGLImageRenderType.AUTO_NO_BACK
    )

    fun setImageRes(
        resourceId: Int,
        type: OpenGLImageRenderType = OpenGLImageRenderType.AUTO_NO_BACK
    )

    /**
     * 设置滤镜
     */
    fun setFilter(filter: RendererFilter?)


    /**
     * 设置true后  view 将会被置顶显示
     * 设置false  view可能会有黑边
     */
    fun setTranslucent(translucent: Boolean)
}