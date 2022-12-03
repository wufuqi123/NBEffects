package cn.wufuqi.nbeffects.filter

import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLES30
import android.opengl.Matrix
import android.os.SystemClock
import android.util.Log
import androidx.annotation.CallSuper
import cn.wufuqi.nbeffects.Constants
import cn.wufuqi.nbeffects.NBEffectsImageView
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.R
import cn.wufuqi.nbeffects.bean.*
import cn.wufuqi.nbeffects.utils.BitmapUtils
import cn.wufuqi.nbeffects.utils.BufferUtils
import cn.wufuqi.nbeffects.utils.ResReadUtils.readResource
import cn.wufuqi.nbeffects.utils.ShaderUtils.linkProgram
import cn.wufuqi.nbeffects.utils.ShaderUtils.loadFragmentShader
import cn.wufuqi.nbeffects.utils.ShaderUtils.loadVertexShader
import cn.wufuqi.nbeffects.utils.TextureUtils.loadTexture
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * 基本的Filter
 */
open class BaseFilter : RendererFilter {

    companion object {
        const val TEXTURE_LOCATION_NAME = "uTextureUnit"
        const val TAG = "RendererFilter"
    }

    private var vertexBuffer: FloatBuffer
    private var mTexVertexBuffer: FloatBuffer

    private var mVertexIndexBuffer: ShortBuffer

    var mProgram = 0

    var textureId = 0


    private var uMatrixLocation = 0

    private val mUniformMap = mutableMapOf<String, UniformInterface>()

    /**
     * 矩阵
     */
    private val mMatrix = FloatArray(16)

    /**
     * 顶点着色器
     */
    private var mVertexShader: String

    /**
     * 片段着色器
     */
    private var mFragmentShader: String

    override var bitmap: Bitmap = BitmapUtils.getTransparentBitmap()

    var lastTime = 0L

    var textureCount = 0
    /**
     * 加载默认的着色器
     */
    constructor() : this(
        R.raw.base_filter_vertex_shader,
        R.raw.base_filter_fragment_shader
    )

    constructor(fragmentRes: Int) : this(
        R.raw.base_filter_vertex_shader,
        fragmentRes
    )

    constructor(vertexRes: Int, fragmentRes: Int) : this(
        readResource(vertexRes),
        readResource(fragmentRes)
    )

    constructor(vertexShader: String, fragmentShader: String) {
        mVertexShader = vertexShader
        mFragmentShader = fragmentShader
        //初始化内存空间
        vertexBuffer = BufferUtils.floatBufferUtil(Constants.POSITION_VERTEX)
        mTexVertexBuffer = BufferUtils.floatBufferUtil(Constants.TEX_VERTEX)
        mVertexIndexBuffer = BufferUtils.shortBufferUtil(Constants.VERTEX_INDEX)
    }


    open fun setupProgram() {
        //编译着色器
        val vertexShaderId = loadVertexShader(mVertexShader)
        val fragmentShaderId = loadFragmentShader(mFragmentShader)
        //链接程序片段
        mProgram = linkProgram(vertexShaderId, fragmentShaderId)
        uMatrixLocation = GLES30.glGetUniformLocation(mProgram, "u_Matrix")
        mUniformMap.forEach { v ->
            v.value.location = GLES30.glGetUniformLocation(mProgram, v.key)
            if (v.value is Texture) {
                val texture = v.value as Texture
                texture.textureId = loadTexture(texture.bitmap)
            }
        }
        //加载纹理
        textureId = loadTexture(bitmap)

//        textureLocation =
    }


    fun setUniform(name: String, value: Float) {
        setUniform(name, UniformFloat(value))
    }

    fun setUniform(name: String, value: Int) {
        setUniform(name, UniformInt(value))
    }

    fun setUniform(name: String, u: UniformInterface) {
        mUniformMap[name] = u
        if (u.location == 0) {
            u.location = GLES30.glGetUniformLocation(mProgram, name)
        }
    }

    @CallSuper
    override fun onSurfaceCreated() {
        //设置背景颜色
//        GLES30.glClearColor(0.5f, 0.5f, 0.5f, 0.5f)
        //初始化程序对象
        setupProgram()
        lastTime = SystemClock.uptimeMillis()
    }

    @CallSuper
    override fun onSurfaceChanged(
        width: Int,
        height: Int,
        bitmapWidth: Int,
        bitmapHeight: Int,
        renderType: OpenGLImageRenderType,
        view: NBEffectsImageView?
    ) {

        GLES30.glViewport(0, 0, width, height)
        var left = -1f
        var right = 1f
        var top = 1f
        var bottom = -1f

        var currRenderType = renderType
        if (currRenderType == OpenGLImageRenderType.AUTO_NO_BACK) {
            val realityHeight = ((width.toFloat() / bitmapWidth) * bitmapHeight).toInt()
            val ratioHeight = realityHeight.toFloat() / height
            currRenderType =
                if (ratioHeight >= 1) OpenGLImageRenderType.EQUAL_WIDTH else OpenGLImageRenderType.EQUAL_HEIGHT
        }


        when (currRenderType) {
            OpenGLImageRenderType.EQUAL_WIDTH -> {
                // 图片真实在手机上的高度
                val realityHeight = ((width.toFloat() / bitmapWidth) * bitmapHeight).toInt()
                val ratioHeight = realityHeight.toFloat() / height

                top = 1 / ratioHeight
                bottom = -1 / ratioHeight
            }
            OpenGLImageRenderType.EQUAL_HEIGHT -> {
                val realityWidth = ((height.toFloat() / bitmapHeight) * bitmapWidth).toInt()
                val ratioWidth = realityWidth.toFloat() / width

                right = 1 / ratioWidth
                left = -1 / ratioWidth
            }
            OpenGLImageRenderType.FLEX -> {
                left = -1f
                right = 1f
                top = 1f
                bottom = -1f
            }
        }
        setMatrix(left, right, bottom, top)
    }

    open fun setMatrix(left: Float, right: Float, bottom: Float, top: Float) {
        Matrix.orthoM(mMatrix, 0, left, right, bottom, top, -1f, 1f)
    }


    @CallSuper
    override fun onDrawFrame() {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        //        GLES30.glClear(GLES30.GL_ALPHA);

        //使用程序片段
        GLES30.glUseProgram(mProgram)
        val currTime = SystemClock.uptimeMillis()
        //更新属性等信息
        onUpdateDrawFrame(currTime - lastTime)
        lastTime = currTime




        GLES30.glUniformMatrix4fv(uMatrixLocation, 1, false, mMatrix, 0)



        GLES30.glEnableVertexAttribArray(0)
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, vertexBuffer)
        GLES30.glEnableVertexAttribArray(1)
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 0, mTexVertexBuffer)
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        //绑定纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId)
//        GLES30.glUniform1i(GLES30.glGetUniformLocation(mProgram, TEXTURE_LOCATION_NAME), textureId)


        // 设置  Uniform
        mUniformMap.forEach { v ->
            when (val value = v.value) {
                is Vec4 -> {
                    GLES30.glUniform4fv(value.location, 1, value.toArray(), 0)
                }
                is Vec2 -> {
                    GLES30.glUniform2fv(value.location, 1, value.toArray(), 0)
                }
                is Vec3 -> {
                    GLES30.glUniform3fv(value.location, 1, value.toArray(), 0)
                }
                is UniformInt -> {
                    GLES30.glUniform1i(value.location, value.value)
                }
                is UniformFloat -> {
                    GLES30.glUniform1f(value.location, value.value)
                }
                is Texture -> {
                    GLES30.glActiveTexture(GLES30.GL_TEXTURE0 + value.textureId)
                    GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, value.textureId)
                    //绑定纹理
                    GLES30.glUniform1i(value.location, value.textureId)
                }
            }
        }

        // 绘制
        GLES20.glDrawElements(
            GLES20.GL_TRIANGLES,
            Constants.VERTEX_INDEX.size,
            GLES20.GL_UNSIGNED_SHORT,
            mVertexIndexBuffer
        )
    }

    open fun onUpdateDrawFrame(dt: Long) {}

    override fun onDestroy() {
        GLES30.glDeleteProgram(mProgram)
    }
}