package cn.wufuqi.nbeffects.bean

import android.graphics.Bitmap
import cn.wufuqi.nbeffects.utils.BitmapUtils

class Texture : UniformInterface {
    var bitmap: Bitmap

    var textureId: Int = 0

    constructor(resId: Int) : this(BitmapUtils.getBitmap(resId)!!)
    constructor(bitmap: Bitmap) {
        this.bitmap = bitmap
    }
}
