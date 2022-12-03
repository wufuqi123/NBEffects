package cn.wufuqi.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.wufuqi.easytimer.EasyTimer
import cn.wufuqi.nbeffects.NBEffectsImageView
import cn.wufuqi.nbeffects.OpenGLImageRenderType
import cn.wufuqi.nbeffects.filter.*
import com.wukonganimation.tween.TweenManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val img = findViewById<NBEffectsImageView>(R.id.iv_gpu)
        img.setImageRes(R.drawable.main)
        EasyTimer().scheduleOneUI(2000) {
            Log.e("wufuqi---", "------------")
//            img.setImageRes(R.drawable.fj)
//            img.setFilter(GrayFilter())
//            img.setOpenGLImageRenderType(OpenGLImageRenderType.EQUAL_WIDTH)
//            img.setTranslucent(true)
//            EasyTimer().scheduleOneUI(2000) {
//                Log.e("wufuqi---", "------------")
//                img.setTranslucent(false)
//                EasyTimer().scheduleOneUI(2000) {
//                    Log.e("wufuqi---", "------------")
//                    img.setTranslucent(true)
//                }
//            }
        }

        img.setOpenGLImageRenderType(OpenGLImageRenderType.AUTO_NO_BACK)
//        val filter = DissolveFilter()
//        img.setFilter(filter)
//        img.setFilter(BesmirchFilter())
//        filter.dissolve = 0f
//        filter.fadeOut(2000)
//        filter.fadeOut(2000)
    }
}