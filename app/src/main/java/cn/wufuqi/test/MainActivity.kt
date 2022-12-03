package cn.wufuqi.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import cn.wufuqi.easytimer.EasyTimer
import cn.wufuqi.nbeffects.NBEffectsImageView
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.filter.*
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
        EasyTimer().scheduleOneUI(2000) {
            val filter = DissolveFilter()
            img.setFilter(filter)
//            TweenManager.builderOne(filter)
//                .from(mutableMapOf("dissolve" to 0f))
//                .to(mutableMapOf("dissolve" to 1f))
//                .time(4000)
//                .pingPong(true)
//                .repeat(-1)
//                .start()
            filter.fadeOut(5000){

                        filter.fadeIn(5000)
            }
        }
        img.setTranslucent(true)

    }
}