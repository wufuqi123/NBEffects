package cn.wufuqi.nbeffects.utils

import android.content.res.Resources
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object ResReadUtils {

    /**
     * 读取资源
     *
     * @param resourceId
     * @return
     */
    fun readResource(resourceId: Int): String {
        val builder = StringBuilder()
        try {
            val inputStream: InputStream =
                AppUtils.getApplication()!!.resources.openRawResource(resourceId)
            val streamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(streamReader)
            bufferedReader.readLines().forEach {
                builder.append(it)
                builder.append("\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }
        return builder.toString()
    }
}