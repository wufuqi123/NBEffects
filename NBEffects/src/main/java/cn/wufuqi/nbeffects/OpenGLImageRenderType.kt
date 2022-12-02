package cn.wufuqi.nbeffects

enum class OpenGLImageRenderType {

    /**
     * 等高等宽拉伸
     */
    FLEX,

    /**
     * 等宽的
     */
    EQUAL_WIDTH,

    /**
     * 等高的
     */
    EQUAL_HEIGHT,

    /**
     * 自适应  EQUAL_WIDTH  和 EQUAL_HEIGHT
     * 不留黑边
     */
    AUTO_NO_BACK,

}