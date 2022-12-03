package cn.wufuqi.nbeffects.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLES30.*
import android.opengl.GLUtils
import android.util.Log


object TextureUtils {

    private const val TAG = "TextureUtils"

    fun loadTexture(resourceId: Int): Int {
        return loadTexture(BitmapUtils.getBitmap(resourceId))
    }


    fun loadTexture(bitmap: Bitmap?): Int {
        val textureIds = IntArray(1)
        glGenTextures(1, textureIds, 0)
        if (textureIds[0] == 0) {
            Log.e(TAG, "无法生成一个新的OpenGL textureId对象")
            return 0
        }

        if (bitmap == null) {
            Log.e(BitmapUtils.TAG, "无法绑定bitmap.  bitmap is null")
            glDeleteTextures(1, textureIds, 0)
            return 0
        }

        // 绑定纹理到OpenGL
        glBindTexture(GL_TEXTURE_2D, textureIds[0])

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

        // 加载bitmap到纹理中
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)

        // 生成MIP贴图
        glGenerateMipmap(GL_TEXTURE_2D)

//        // 数据如果已经被加载进OpenGL,则可以回收该bitmap
//        bitmap.recycle()

        // 取消绑定纹理
        glBindTexture(GL_TEXTURE_2D, 0)

        return textureIds[0]
    }


}