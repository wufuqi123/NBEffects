package cn.wufuqi.nbeffects.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import java.lang.reflect.InvocationTargetException

@SuppressLint("StaticFieldLeak")
object AppUtils {

    private var application: Application? = null
    private var context: Context? = null

    /** 反射获取Application  */
    @SuppressLint("BlockedPrivateApi", "StaticFieldLeak", "PrivateApi", "DiscouragedPrivateApi")
    fun getApplication(): Application? {
        if (application != null) {
            return application
        }
        try {
            @SuppressLint("PrivateApi") val activityThread =
                Class.forName("android.app.ActivityThread")
            val at = activityThread.getMethod("currentActivityThread").invoke(null)
            val app = activityThread.getMethod("getApplication").invoke(at)
                ?: throw NullPointerException("u should init first")
            application = app as Application
            context = application
            return application
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        throw NullPointerException("u should init first")
    }

    /** 获取Context  */
    @SuppressLint("StaticFieldLeak")
    fun getContext(): Context? {
        return context ?: getApplication()
    }

    fun getAppIcon(): Int {
        var icon = -1
        try {
            val packageManager = getContext()?.packageManager
            val packageInfo = packageManager?.getPackageInfo(
                getContext()?.packageName ?: "", 0
            )
            icon = packageInfo?.applicationInfo?.icon ?: -1
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return icon
    }


    /**
     * 获取app的名称
     * @param context
     * @return
     */
    fun getAppName(): String {
        var appName = ""
        try {
            val packageManager = getContext()?.packageManager
            val packageInfo = packageManager?.getPackageInfo(
                getContext()?.packageName ?: "", 0
            )
            val labelRes = packageInfo?.applicationInfo?.labelRes ?: -1
            appName = getContext()?.resources?.getString(labelRes) ?: ""
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return appName
    }

    /**
     * 获取app的名称
     * @param context
     * @return
     */
    fun getPackageName(): String {
        return getContext()?.packageName ?: ""
    }

    /**
     * 用于判断那个应用是处于前台的
     */
    private fun getForegroundApp(): String? {

        val activityManager = getContext()!!
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses
        if (runningAppProcesses.isNullOrEmpty()) {
            return null
        }
        runningAppProcesses.forEach {
            if (it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND || it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                return it.processName
            }
        }

        return null
    }

    fun isRunningForeground(): Boolean {
        return getForegroundApp() == getPackageName()
    }
}