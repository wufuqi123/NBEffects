package cn.wufuqi.nbeffects.utils

import android.graphics.*
import android.graphics.drawable.Drawable

object BitmapUtils {
    const val TAG = "BitmapUtils"
    fun getBitmap(resourceId: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inScaled = false
        return BitmapFactory.decodeResource(AppUtils.getContext()!!.resources, resourceId, options)
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        //获取图片宽度
        val width = drawable.intrinsicWidth
        //获取图片高度
        val height = drawable.intrinsicHeight
        //图片位深，PixelFormat.OPAQUE代表没有透明度，RGB_565就是没有透明度的位深，否则就用ARGB_8888。详细见下面图片编码知识。
        val config =
            if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        //创建一个空的Bitmap
        val bitmap = Bitmap.createBitmap(width, height, config)
        //在bitmap上创建一个画布
        val canvas = Canvas(bitmap)
        //设置画布的范围
        drawable.setBounds(0, 0, width, height)
        //将drawable绘制在canvas上
        drawable.draw(canvas)
        return bitmap
    }

    fun getTransparentBitmap(): Bitmap {
        val argb = IntArray(1 * 1)
        for (i in argb.indices) {
            argb[i] = 0x00FFFFFF.toInt()
        }
        return Bitmap.createBitmap(
            argb, 1, 1, Bitmap.Config.ARGB_8888
        )
    }

    // 获得旋转角度之后的位图对象
    fun getRotateAndFlipBitmap(bitmap: Bitmap, rotateDegree: Float): Bitmap {
        val matrix = Matrix() // 创建操作图片用的矩阵对象
        matrix.postRotate(rotateDegree) // 执行图片的旋转动作
        matrix.postScale(-1f, 1f)
        // 创建并返回旋转后的位图对象
        return Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width,
            bitmap.height, matrix, false
        )
    }
}