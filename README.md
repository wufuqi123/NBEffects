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
演示：
![DissolveFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/DissolveFilter.gif)

使用方式

```
    val filter = DissolveFilter()

    //溶解的值  0-1
    filter.dissolve = 0 - 1
    //渐入
    filter.fadeIn(500)
    //渐出
    filter.fadeOut(500)
    //指定到某个值
    filter.fadeTo(500)

    img.setFilter(filter)
```



#### DynamicBlurTransferFilter
演示：
![DynamicBlurTransferFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/DynamicBlurTransferFilter.gif)

使用方式

```
    val filter = DynamicBlurTransferFilter()

    //值  0-1
    filter.transfer = 0 - 1
    //渐入
    filter.fadeIn(500)
    //渐出
    filter.fadeOut(500)
    //指定到某个值
    filter.fadeTo(500)

    img.setFilter(filter)
```



#### GaussBlursFilter
演示：
![GaussBlursFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/GaussBlursFilter.png)

使用方式

```
    val filter = GaussBlursFilter()

    filter.blur = 2f  //....

    img.setFilter(filter)
```



#### GrayFilter
演示：
![GrayFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/GrayFilter.png)

使用方式

```
    val filter = GrayFilter()

    img.setFilter(filter)
```


#### MosaicFilter
演示：
![MosaicFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/MosaicFilter.png)

使用方式

```
    val filter = MosaicFilter()

    // 方形马赛克的宽高大小  px 
    filter.mosaicSize = 2f  //0 - 屏幕大小

    img.setFilter(filter)
```

#### QuarterMirrorFilter
演示：
![QuarterMirrorFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/QuarterMirrorFilter.png)


使用方式

```
    val filter = QuarterMirrorFilter()

    img.setFilter(filter)
```

#### RadialBlurFilter
演示：
![RadialBlurFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/RadialBlurFilter.png)

使用方式

```
    val filter = RadialBlurFilter()

    filter.center = 2f  //....

    img.setFilter(filter)
```


#### RainFilter
演示：
![RainFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/RainFilter.gif)

使用方式

```
    val filter = RainFilter()

    img.setFilter(filter)
```



#### UnfoldTransferFilter
演示：
![UnfoldTransferFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/UnfoldTransferFilter.gif)

使用方式

```
    val filter = UnfoldTransferFilter()

    //值  0-1
    filter.transfer = 0 - 1  0 - 2
    
    filter.transferAmin(500)
    //渐入
    filter.fadeIn(500)
    //渐出
    filter.fadeOut(500)
    //指定到某个值
    filter.fadeTo(500)

    img.setFilter(filter)
```




#### WaterFilter
演示：
![WaterFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/WaterFilter.gif)

使用方式

```
    val filter = WaterFilter()

    img.setFilter(filter)
```

#### WaveFilter
演示：
![WaveFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/WaveFilter.gif)

使用方式

```
    val filter = WaveFilter()

    img.setFilter(filter)
```



#### WindowTransferFilter
演示：
![WindowTransferFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/WindowTransferFilter.gif)


使用方式

```
    val filter = WindowTransferFilter()

    //值  0-1
    filter.transfer = 0 - 1
    
    filter.transferAmin(500)
    //渐入
    filter.fadeIn(500)
    //渐出
    filter.fadeOut(500)
    //指定到某个值
    filter.fadeTo(500)

    img.setFilter(filter)
```

#### ZoomBlurTransferFilter
演示：
![ZoomBlurTransferFilter](https://raw.githubusercontent.com/wufuqi123/NBEffects/master/img/ZoomBlurTransferFilter.gif)


使用方式

```
    val filter = ZoomBlurTransferFilter()

    //值  0-1
    filter.transfer = 0 - 1
    
    filter.transferAmin(500)
    //渐入
    filter.fadeIn(500)
    //渐出
    filter.fadeOut(500)
    //指定到某个值
    filter.fadeTo(500)

    img.setFilter(filter)
```
