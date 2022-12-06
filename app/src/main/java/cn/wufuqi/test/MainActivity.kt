package cn.wufuqi.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import cn.wufuqi.easytimer.EasyTimer
import cn.wufuqi.nbeffects.NBEffectsImageView
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.filter.*
import cn.wufuqi.nbeffects.pixijs_filter.AdjustmentFilter
import cn.wufuqi.nbeffects.pixijs_filter.AsciiFilter
import cn.wufuqi.nbeffects.pixijs_filter.BevelFilter
import com.gyf.immersionbar.ImmersionBar
import com.wukonganimation.tween.TweenManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        ImmersionBar.with(this)
//            .fitsSystemWindows(true)
            //状态栏颜色，不写默认透明色
//            .statusBarColor("#ffffff")
//            .autoStatusBarDarkModeEnable(true, 0.2f)
            .transparentStatusBar()  //透明状态栏，不写默认透明色
            .transparentNavigationBar()
            .init()


        val img = findViewById<NBEffectsImageView>(R.id.iv_gpu)
        img.setImageRes(R.drawable.fj)
//        val t = RadialBlurFilter()
//        t.size = 50f
//        img.setFilter(t)
        img.setTranslucent(true)

    }
}