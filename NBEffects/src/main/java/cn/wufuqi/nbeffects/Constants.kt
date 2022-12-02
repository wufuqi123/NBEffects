package cn.wufuqi.nbeffects

object Constants {
    const val BYTES_PER_INT = 4
    const val BYTES_PER_FLOAT = 4
    const val BYTES_PER_SHORT = 2

    /**
     * 顶点坐标
     * (x,y,z)
     */
    val POSITION_VERTEX = floatArrayOf(
        0f, 0f, 0f,  //顶点坐标V0
        1f, 1f, 0f,  //顶点坐标V1
        -1f, 1f, 0f,  //顶点坐标V2
        -1f, -1f, 0f,  //顶点坐标V3
        1f, -1f, 0f //顶点坐标V4
    )

    /**
     * 纹理坐标
     * (s,t)
     */
    val TEX_VERTEX = floatArrayOf(
        0.5f, 0.5f,  //纹理坐标V0
        1f, 0f,  //纹理坐标V1
        0f, 0f,  //纹理坐标V2
        0f, 1.0f,  //纹理坐标V3
        1f, 1.0f //纹理坐标V4
    )

    /**
     * 索引
     */
    val VERTEX_INDEX = shortArrayOf(
        0, 1, 2,  //V0,V1,V2 三个顶点组成一个三角形
        0, 2, 3,  //V0,V2,V3 三个顶点组成一个三角形
        0, 3, 4,  //V0,V3,V4 三个顶点组成一个三角形
        0, 4, 1 //V0,V4,V1 三个顶点组成一个三角形
    )
}