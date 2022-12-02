package cn.wufuqi.nbeffects.utils


import android.opengl.GLES30.*
import android.util.Log


object ShaderUtils {

    /**
     * 加载shader
     */
    fun loadShader(type: Int, shaderCode: String): Int {
        val shaderObjectId = glCreateShader(type)
        if (shaderObjectId == 0) {
            Log.e("ShaderUtils", "着色器创建失败：$shaderObjectId")
            return 0
        }
        // 添加上面编写的着色器代码并编译它
        glShaderSource(shaderObjectId, shaderCode)
        glCompileShader(shaderObjectId)

        val compileStatus = intArrayOf(0)
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            //失败  删除着色器
            glDeleteShader(shaderObjectId)
            Log.e("ShaderUtils", "着色器编译失败：$shaderCode")
            return 0
        }
        return shaderObjectId
    }


    /**
     * 加载顶点着色器
     */
    fun loadVertexShader(shaderCode: String): Int {
        return loadShader(GL_VERTEX_SHADER, shaderCode)
    }

    /**
     * 加载片段着色器
     */
    fun loadFragmentShader(shaderCode: String): Int {
        return loadShader(GL_FRAGMENT_SHADER, shaderCode)
    }


    /**
     * shader和 Program 连接起来
     */
    fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programObjectId = glCreateProgram()
        if (programObjectId == 0) {
            Log.e("ShaderUtils", "OpenGL SE 2  创建Program 失败！")
            return 0
        }
        glAttachShader(programObjectId, vertexShaderId)
        glAttachShader(programObjectId, fragmentShaderId)

        glLinkProgram(programObjectId)
        val compileStatus = IntArray(1)
        glGetProgramiv(programObjectId, GL_LINK_STATUS, compileStatus, 0)
        if (compileStatus[0] == 0) {

            val logInfo = glGetProgramInfoLog(programObjectId)
            //失败  删除
            glDeleteProgram(programObjectId)
            Log.e("ShaderUtils", "OpenGL SE 2  连接Program 失败！  $programObjectId  $vertexShaderId  $fragmentShaderId  $logInfo")
            return 0
        }
        return programObjectId
    }

    /**
     * 检测当前Program 是否有效
     */
    fun validateProgram(programObjectId: Int): Boolean {
        glValidateProgram(programObjectId)
        val compileStatus = intArrayOf(0)
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, compileStatus, 0)
        val isValidate = compileStatus[0] != 0
        if (!isValidate) {
            Log.e("ShaderUtils", "OpenGL SE 2  Program 无效")
        }
        return isValidate
    }
}