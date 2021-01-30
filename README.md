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
|方法名|描述|参数描述|
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
|方法名|描述|参数描述|
|---|---|---|
|getTotalCacheSize(Context context)|获取缓存大小|context-上下文|
|clearAllCache(Context context)|清除缓存|context-上下文|

#### ClipboardUtils - 剪切板工具类
|方法名|描述|参数描述|
|---|---|---|
|getClipboardContent(Context context)|获取剪切板的内容|context-上下文|
|copy(Context context, CharSequence str)|复制文字|context-上下文<br>text-要复制的内容|
|copy(Context context, CharSequence label, CharSequence str)|复制文字|context-上下文<br>label-剪切板数据的用户可见标签<br>text-要复制的内容|

#### DateUtils - 日期格式化工具类
|方法名|描述|参数描述|
|---|---|---|
|formatTimeStamp(Long timeStamp, String template)|通过时间戳获取格式化后的时间串|timeStamp-时间戳(毫秒)<br>template-格式化模板。例如: yyyy-MM-dd HH:mm:ss|
|stringDateToTimeStamp(String strDate, String template)|将时间的字符串转换成时毫秒时间戳|strDate-时间的字符串<br>template-格式化模板,需要和时间字符串的格式匹配。例如: yyyy-MM-dd HH:mm:ss|

#### DensityUtils - dp、px、sp相互转换工具类
|方法名|描述|参数描述|
|---|---|---|
|dp2px(Context context, float dpValue)|根据手机的分辨率从 dp 的单位 转成为 px(像素)|context-上下文<br>dpValue-要转换的dp值|
|px2dp(Context context, float pxValue)|将px转换为与之相等的dp|context-上下文<br>pxValue-要转换的px值|
|px2sp(Context context, float pxValue)|将px值转换为sp值|context-上下文<br>pxValue-要转换的px值|
|sp2px(Context context, float spValue)|将sp值转换为px值|context-上下文<br>spValue-要转换的sp值|

#### DensityUtils - 设备管理工具
|方法名|描述|参数描述|
|---|---|---|
|getStatusBarHeight(Context context)|获取状态栏高度|context-上下文|
|setFullScreen(Activity activity)|设置全屏|activity|
|cancelFullScreen(Activity activity)|取消全屏|activity|
|isFullScreen(Activity activity)|判断是否全屏|activity|
|getDisplayMetrics(Activity activity)|获得设备屏幕密度|activity|
|getScreenWidth(Context context)|获得屏幕宽度|context-上下文|
|getScreenHeight(Context context)|获得屏幕高度|context-上下文|

#### EmailUtils - Email工具类
|方法名|描述|参数描述|
|---|---|---|
|checkEmail(CharSequence email)|验证Email|email-email地址|

#### IDCardUtils - 身份证号码验证工具类
|方法名|描述|参数描述|
|---|---|---|
|validate(String idCardNumber)|身份证号码校验|idCardNumber-需要验证的身份证号码|
|getErrorInfo()|获取错误信息||

#### KeyBoardUtils - 软键盘操作类
|方法名|描述|参数描述|
|---|---|---|
|showKeyboard(EditText editText)|显示软键盘|editText-需要显示软键盘的输入框|
|showKeyboard(EditText editText, boolean isDelay)|延时显示软键盘|editText-需要显示软键盘的输入框<br>isDelay-是否需要延时时间|
|showKeyboard(final EditText editText, int delay)|延时显示软键盘|editText-需要显示软键盘的输入框<br>delay-延时时间，毫秒|
|hideKeyboard(final View view)|隐藏软键盘|view-当前页面上任意一个可用的view|
|isKeyboardVisible(Activity activity)|软键盘是否显示|activity|

#### Logger - 日志帮助类
|方法名|描述|参数描述|
|---|---|---|
|v(String msg)|设置Verbose日志|msg-日志信息|
|v(String tag, String msg)|设置Verbose日志|tag-日志标签<br>msg-日志信息|
|d(String msg)|设置Debug日志|msg-日志信息|
|d(String tag, String msg)|设置Debug日志|tag-日志标签<br>msg-日志信息|
|i(String msg)|设置Info日志|msg-日志信息|
|i(String tag, String msg)|设置Info日志|tag-日志标签<br>msg-日志信息|
|w(String msg)|设置Warn日志|msg-日志信息|
|w(String tag, String msg)|设置Warn日志|tag-日志标签<br>msg-日志信息|
|e(String msg)|设置Error日志|msg-日志信息|
|e(String tag, String msg)|设置Error日志|tag-日志标签<br>msg-日志信息|

#### MetaDataUtils - 获取Manifest中<meta-data>元素的值的工具类
|方法名|描述|参数描述|
|---|---|---|
|getActivityMetaData(Activity activity, String name)|获取Manifest中Activity标签下的<meta-data>元素的值|activity-Activity的上下文<br>name-meta-data的name|
|getApplicationMetaData(Context context, String name)|获取Manifest中Application标签下的<meta-data>元素的值|context-Application的上下文<br>name-<meta-data>的name|
|getServiceMetaData(Context context, Class<?> clazz, String name)|获取Manifest中Service标签下的<meta-data>元素的值|context-Serviece的上下文<br>clazz-对应的Service类<br>name-<meta-data>的name|
|getReceiverMetaData(Context context, Class<?> clazz, String name)|获取Manifest中Receiver标签下的<meta-data>元素的值|context-Receiver的上下文<br>clazz-对应的Receiver类<br>name-<meta-data>的name|

#### MoneyUtils - 金钱计算工具类
|方法名|描述|参数描述|
|---|---|---|
|add(double v1, double v2)|提供精确的加法运算|v1-被加数<br>v2-加数|
|add(String v1, String v2)|提供精确的加法运算|v1-被加数<br>v2-加数|
|add(String v1, String v2, int scale)|提供精确的加法运算|v1-被加数<br>v2-加数<br>scale-保留scale位小数|
|substract(double v1, double v2)|提供精确的减法运算|v1-被减数<br>v2-减数|
|substract(String v1, String v2)|提供精确的减法运算|v1-被减数<br>v2-减数|
|substract(String v1, String v2, int scale)|提供精确的减法运算|v1-被减数<br>v2-减数<br>scale-保留scale位小数|
|multiply(double v1, double v2)|提供精确的乘法运算|v1-被乘数<br>v2-乘数|
|multiply(double v1, double v2, int scale)|提供精确的乘法运算|v1-被乘数<br>v2-乘数<br>scale-保留scale位小数|
|multiply(String v1, String v2, int scale)|提供精确的乘法运算|v1-被乘数<br>v2-乘数<br>scale-保留scale位小数|
|divide(double v1, double v2)|提供(相对)精确的除法运算,当发生除不尽的情况时,精确到小数点以后10位,以后的数字四舍五入。|v1-被除数<br>v2-除数|
|divide(double v1, double v2, int scale)|提供(相对)精确的除法运算。当发生除不尽的情况时,由scale参数指 定精度,以后的数字四舍五入。|v1-被除数<br>v2-除数<br>scale-表示需要精确到小数点以后几位|
|divide(String v1, String v2, int scale)|提供(相对)精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入|v1-被除数<br>v2-除数<br>scale-表示需要精确到小数点以后几位|
|round(double v, int scale)|提供精确的小数位四舍五入处理|v-需要四舍五入的数字<br>scale-小数点后保留几位|
|round(String v, int scale)|提供精确的小数位四舍五入处理|v-需要四舍五入的数字<br>scale-小数点后保留几位|
|round(double v, int scale, int round)|提供精确的小数位获取|v-需要处理的数字<br>scale-小数点后保留几位<br>round:BigDecimal.ROUND_DOWN - 直接删除多余的小数位,如2.35变成2.3<br>BigDecimal.ROUND_UP - 进位处理,如2.35变成2.4<br>BigDecimal.ROUND_HALF_UP - 四舍五入,如2.35变成2.4<br>BigDecimal.ROUND_HALF_DOWN - 四舍五入,如2.35变成2.3|
|compareBigDecimal(String amount, double compare)|比较大小|amount-输入的数值<br>compare-被比较的数字|

















































































