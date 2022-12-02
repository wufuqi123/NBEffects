package cn.wufuqi.nbeffects.bean

data class Vec3(var r: Float, var g: Float, var b: Float): UniformInterface() {
    override fun toArray(): FloatArray {
        return floatArrayOf(r, g, b)
    }
}
