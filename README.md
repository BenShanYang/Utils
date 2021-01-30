# Android开发工具类-Utils
最新版本1.0.6  [![](https://www.jitpack.io/v/BenShanYang/Utils.svg)](https://www.jitpack.io/#BenShanYang/Utils)
[Utils Api文档](https://github.com/BenShanYang/JavaDoc)

## 依赖Utils

#### Step 1.添加依赖
Gradle 
```groovy
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
```

```groovy
dependencies {
    implementation 'com.github.BenShanYang:Utils:1.0.6'
}
```

## 帮助类方法解释

#### AppUtils - 获取App基本信息的工具类
|方法名|描述|参数描述
|---|---|---|
|isInstalled(Context context, String packageName)|判断是否安装某个App|context-上下文<br>packageName-包名|
|isInstalledQQ(Context context)|是否安装了QQ|context-上下文|
|isInstalledWeChat(Context context)|是否安装了微信|context-上下文|
|isInstalledBlog(Context context)|是否安装了新浪微博|context-上下文|
|getAppName(Context context)|获取应用程序名称|context-上下文|
|getVersionName(Context context)|获取应用程序版本名称信息|context-上下文|
|getVersionCode(Context context)|获取应用程序版本名称信息|context-上下文|
|getPackageName(Context context)|获取应用程序版本名称信息|context-上下文|
|getBitmap(Context context)|获取图标 bitmap|context-上下文|
|getDrawable(Context context)|获取图标 drawable|context-上下文|

#### CacheUtils - 清除缓存的工具类
|方法名|描述|参数描述
|---|---|---|
|getTotalCacheSize(Context context)|获取缓存大小|context-上下文|
|clearAllCache(Context context)|清除缓存|context-上下文|

#### ClipboardUtils - 剪切板工具类
|方法名|描述|参数描述
|---|---|---|
|getClipboardContent(Context context)|获取剪切板的内容|context-上下文|
|copy(Context context, CharSequence str)|复制文字|context-上下文<br>text-要复制的内容|
|copy(Context context, CharSequence label, CharSequence str)|复制文字|context-上下文<br>label-剪切板数据的用户可见标签<br>text-要复制的内容|

#### DateUtils - 日期格式化工具类
|方法名|描述|参数描述
|---|---|---|
|formatTimeStamp(Long timeStamp, String template)|通过时间戳获取格式化后的时间串|timeStamp-时间戳(毫秒)<br>template-格式化模板。例如: yyyy-MM-dd HH:mm:ss|
|stringDateToTimeStamp(String strDate, String template)|将时间的字符串转换成时毫秒时间戳|strDate-时间的字符串<br>template-格式化模板,需要和时间字符串的格式匹配。例如: yyyy-MM-dd HH:mm:ss|

#### DensityUtils - dp、px、sp相互转换工具类
|方法名|描述|参数描述
|---|---|---|
|dp2px(Context context, float dpValue)|根据手机的分辨率从 dp 的单位 转成为 px(像素)|context-上下文<br>dpValue-要转换的dp值|
|px2dp(Context context, float pxValue)|将px转换为与之相等的dp|context-上下文<br>pxValue-要转换的px值|
|px2sp(Context context, float pxValue)|将px值转换为sp值|context-上下文<br>pxValue-要转换的px值|
|sp2px(Context context, float spValue)|将sp值转换为px值|context-上下文<br>spValue-要转换的sp值|

#### DensityUtils - 设备管理工具
|方法名|描述|参数描述
|---|---|---|
|getStatusBarHeight(Context context)|获取状态栏高度|context 上下文|
|setFullScreen(Activity activity)|设置全屏|activity|





























































































