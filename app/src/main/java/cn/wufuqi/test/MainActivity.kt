package cn.wufuqi.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.wufuqi.easytimer.EasyTimer
import cn.wufuqi.nbeffects.NBEffectsImageView
import cn.wufuqi.nbeffects.filter.RainFilter
import cn.wufuqi.nbeffects.filter.WaveFilter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val img = findViewById<NBEffectsImageView>(R.id.iv_gpu)
        img.setImageRes(R.drawable.fj)
        img.setFilter(RainFilter())

    }
}