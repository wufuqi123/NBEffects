```
    一个很牛逼的特效库，如水波纹雨滴实时高斯模糊等
```


#### 基础功能
1. 添加依赖

    请在 build.gradle 下添加依赖。

    ``` 
        implementation 'cn.wufuqi:NBEffects:1.0.1'
    ```


2. 设置jdk8或更高版本

    因为本sdk使用了jdk8才能使用的 Lambda 表达式，所以要在 build.gradle 下面配置jdk8或以上版本。

    ``` 
    android {
        ....

        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
        
    }
    ```

3. 使用imageview

    ```
        <cn.wufuqi.nbeffects.NBEffectsImageView
            android:id="@+id/iv_gpu"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    ```

    或者

    ```
        val iv = NBEffectsImageView(context)
        addView(iv)
    ```

4. 设置图片

    ```
        img.setImageRes(R.drawable.fj)
        img.setImageDrawable(drawable)
        img.setImageBitmap(bitmap)
    ```

5. 图片展开方式

    ```
        // 默认是  OpenGLImageRenderType.AUTO_NO_BACK
        img.setOpenGLImageRenderType(OpenGLImageRenderType.AUTO_NO_BACK)
    ```

    OpenGLImageRenderType 说明：

    ```
        FLEX                    等高等宽拉伸
        EQUAL_WIDTH             等宽的,高度使用图片的比例自适应
        EQUAL_HEIGHT            等高的,宽度使用图片的比例自适应
        AUTO_NO_BACK            自适应  EQUAL_WIDTH  和 EQUAL_HEIGHT，不留黑边
    ```


4. 去掉黑边，为了适应EQUAL_WIDTH和EQUAL_HEIGHT

    ```
        //设置之后  就没有背景黑色。但是UI会被显示在最顶部，设置的UI显示顺序不起作用。
        img.setTranslucent(true)
    ```


5. 设置过滤器

    当设置过滤器后，图片将会在显卡重新计算图形。可以重复更换过滤器。

    ```
        // 如果不设置过滤器，默认为：BaseFilter这个过滤器。
        img.setFilter(BaseFilter())
    ```


## 过滤器（Filter）介绍


#### DissolveFilter


#### DynamicBlurTransferFilter


#### GrayFilter


#### MosaicFilter


#### QuarterMirrorFilter



#### RadialBlurFilter


#### RainFilter


#### UnfoldTransferFilter


#### WaterFilter


#### WaveFilter


#### WindowTransferFilter


#### ZoomBlurTransferFilter

