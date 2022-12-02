package cn.wufuqi.nbeffects.utils

import cn.wufuqi.nbeffects.Constants
import java.nio.*

object BufferUtils {

    /**
     * 将int[]转成IntBuffer
     */
    fun intBufferUtil(arr: IntArray): IntBuffer {
        val mBuffer: IntBuffer
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        val qbb: ByteBuffer = ByteBuffer.allocateDirect(arr.size * Constants.BYTES_PER_INT)
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder())
        mBuffer = qbb.asIntBuffer()
        mBuffer.put(arr)
        mBuffer.position(0)
        return mBuffer
    }


    /**
     * 将float[]数组转为OpenGl 所需要的FloatBuffer
     */
    fun floatBufferUtil(arr: FloatArray): FloatBuffer {
        val mBuffer: FloatBuffer
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        val qbb = ByteBuffer.allocateDirect(arr.size * Constants.BYTES_PER_FLOAT)
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder())
        mBuffer = qbb.asFloatBuffer()
        mBuffer.put(arr)
        mBuffer.position(0)
        return mBuffer
    }


    /**
     * 将short[]转ShortBuffer
     */
    fun shortBufferUtil(arr: ShortArray): ShortBuffer {
        val mBuffer: ShortBuffer
        // 初始化ByteBuffer，长度为arr数组的长度*2，因为一个short占2个字节
        val dlb = ByteBuffer.allocateDirect( // (# of coordinate values * 2 bytes per short)
            arr.size * Constants.BYTES_PER_SHORT
        )
        dlb.order(ByteOrder.nativeOrder())
        mBuffer = dlb.asShortBuffer()
        mBuffer.put(arr)
        mBuffer.position(0)
        return mBuffer
    }

}