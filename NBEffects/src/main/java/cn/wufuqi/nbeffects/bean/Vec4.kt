package cn.wufuqi.nbeffects.bean

data class Vec4(var r: Float, var g: Float, var b: Float, var a: Float) : UniformInterface() {
    override fun toArray(): FloatArray {
        return floatArrayOf(r, g, b, a)
    }
}