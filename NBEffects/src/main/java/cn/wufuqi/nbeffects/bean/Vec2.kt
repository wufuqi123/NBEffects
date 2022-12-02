package cn.wufuqi.nbeffects.bean

data class Vec2(var x: Float, var y: Float): UniformInterface() {
    override fun toArray(): FloatArray {
        return floatArrayOf(x, y)
    }
}
