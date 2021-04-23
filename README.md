# Android开发工具类-Utils
最新版本1.1.4 [![](https://www.jitpack.io/v/BenShanYang/Utils.svg)](https://www.jitpack.io/#BenShanYang/Utils)

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
    implementation 'com.github.BenShanYang:Utils:1.1.4'
}
```


## 基础类

### 接口类和抽象类
```java
OnItemClickListener<T> //自定义RecyclerView的item点击回调事件
OnItemLongClickListener<T> //自定RecyclerView的item长点击回调事件
OnPageChangedListener //ViewPager.OnPageChangeListener的实现类，只在代码里实现onPageSelected(int position)方法，避免实现过多方法
TextWatchListener //TextWatcher的实现类，再在代码里实现afterTextChanged(Editable s)方法，避免实现过多方法
```
### Activity栈管理类-ActivityStackManager
```java
------------------------Application注册------------------------
public class UtilsApp extends Application {

    public static ActivityStackManager manager;
    
    @Override
    public void onCreate() {
        super.onCreate();
        manager = ActivityStackManager.getInstance();
        //manager.clearStack();//清空activity栈
        //manager.topActivity();//获取Activity栈顶activity
        manager.setActivityLifecycle(new ActivityLifecycle(){
            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
        registerActivityLifecycleCallbacks(manager);
    }
}

------------------------首页按两次返回键推出程序------------------------
// 退出时间
private long currentBackPressedTime = 0;
// 退出间隔
private static final int BACK_PRESSED_INTERVAL = 2000;

@Override
public void onBackPressed() {
    // 判断时间间隔
    if (System.currentTimeMillis()- currentBackPressedTime > BACK_PRESSED_INTERVAL) {
        currentBackPressedTime = System.currentTimeMillis();
        Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
    } else {
        // 退出
        UtilsApp.manager.clearStack();//清空activity栈
    }
}

------------------------退出登录1------------------------
Intent intent = new Intent(this,MainActivity.class);
Bundle bundle = new Bundle();
bundle.putBoolean("onlyAlive",true);
intent.putExtras(bundle);
startActivity(intent);

------------------------退出登录2------------------------
Intent intent = new Intent(this,MainActivity.class);
intent.putExtra("onlyAlive",true);
startActivity(intent);
```

### 文件下载管理类
```java
FileDownloadManager downloadManager = new FileDownloadManager(activity,"url","name"){
    @Override
    public void onPrepare() {
	//下载准备
    }

    @Override
    public void onSuccess(String path) {
	//下载完成
    }

    @Override
    public void onFailed(Throwable throwable) {
	//下载失败
    }
};
downloadManager.download();
```

### 指纹识别管理类
```java
FingerprintHelper.Builder builder = new FingerprintHelper.Builder(activity)
                .callback(new FingerprintCallback() {
                    @Override
                    public void onHmUnavailable() {
                        //硬件不支持                        
                    }

                    @Override
                    public void onNoneEnrolled() {
                        //未添加指纹                        
                    }

                    @Override
                    public void fingerprintOk() {
                        //设备支持指纹并且已经录入指纹并且设备也打开了指纹识别                        
                    }

                    @Override
                    public void onSuccee() {
                        //指纹识别成功                        
                    }

                    @Override
                    public void onFailed(int errorCode, CharSequence errString) {
                        //指纹识别失败
                        if (errorCode == FingerprintCallback.DISABLED) {
                            //处于指纹禁用期                            
                        } else if (errorCode == FingerprintCallback.VALIDATION_FAILED) {
                            //多次验证指纹失败被系统禁用指纹一段时间                            
                        } else if (errorCode == FingerprintCallback.FINGERPRINT_READER_DISABLED) {
                            //尝试次数过多，指纹传感器已停用                            
                        } else if (errorCode == FingerprintCallback.FINGERPRINT_CANCEL) {
                            //指纹操作已取消                            
                        } else if (errorCode == FingerprintCallback.FINGERPRINT_FAILED) {
                            //没有调用系统的回调函数                            
                        }
                    }

                    @Override
                    public void onCancel() {
                        //取消指纹识别                        
                    }
                });
        builder.build();
```

### BaseDialog使用
```java
public class CustomDialog extends BaseDialog {

    public CustomDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_dialog);
        //点击弹窗区域外弹窗消失
        setCanceledOnTouchOutside(true);
    }

    @Override
    public int getGravity() {
    	//设置显示内容的居中方式。例：Gravity.CENTER、Gravity.BOTTOM 、Gravity.CENTER_HORIZONTAL
        return Gravity.CENTER;
    }
}
```
### BaseParentActivity
#### 使用
```java
public class MainActivity extends BaseParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```
#### BaseParentActivity介绍
```java
public abstract class BaseParentActivity extends FragmentActivity {

    public Activity activity;
    private Toast toast = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = BaseParentActivity.this;
    }

    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    /**
     * 获取资源文件中的颜色
     *
     * @param id 颜色的资源id
     * @return 返回颜色的色值
     */
    @ColorInt
    public int getResColor(@ColorRes int id) {
        try {
            return getResources().getColor(id);
        } catch (Exception e) {
            return Color.TRANSPARENT;
        }
    }

    /**
     * 获取资源文件图片或者drawable
     *
     * @param id 资源文件的id
     * @return 返回的资源文件
     */
    public Drawable getResDrawable(@DrawableRes int id) {
        try {
            return getResources().getDrawable(id);
        } catch (Exception e) {
            return new ColorDrawable(Color.TRANSPARENT);
        }
    }

    /**
     * 获取布局文件
     *
     * @param resource 布局id
     * @return 返回的布局view
     */
    public View getLayout(@LayoutRes int resource) {
        return LayoutInflater.from(activity).inflate(resource, null);
    }

    /**
     * 获取布局文件
     *
     * @param resource 布局id
     * @param root     布局要加入的父布局ViewGroup
     * @return 返回的布局view
     */
    public View getLayout(@LayoutRes int resource, @Nullable ViewGroup root) {
        return LayoutInflater.from(activity).inflate(resource, root);
    }

    /**
     * 获取布局文件
     *
     * @param resource     布局id
     * @param root         布局要加入的父布局ViewGroup
     * @param attachToRoot
     * @return 返回的布局view
     */
    public View getLayout(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.from(activity).inflate(resource, root, attachToRoot);
    }

    /**
     * 弹出提示
     *
     * @param message 提示的信息
     */
    public void showToast(CharSequence message) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, dp2px(this, 100));
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     *
     * @param message 提示的信息
     * @param gravity 位于屏幕中的位置。例: Gravity.BOTTOM
     */
    public void showToast(CharSequence message, int gravity) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                toast.setGravity(gravity, 0, 0);
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     *
     * @param message 提示的信息
     * @param gravity 位于屏幕中的位置。例: Gravity.BOTTOM
     * @param xOffset x轴上的偏移量
     * @param yOffset y轴上的偏移量
     */
    public void showToast(CharSequence message, int gravity, int xOffset, int yOffset) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                toast.setGravity(gravity, xOffset, yOffset);
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     *
     * @param text 提示的文字
     */
    public void showSnackbar(CharSequence text) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 打开指定Activity
     *
     * @param clazz 指定Activity
     */
    public void toActivity(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, int requestCode) {
        startActivityForResult(new Intent(this, clazz), requestCode);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, long requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtra("requestCode", requestCode);
        startActivity(intent);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param bundle      数据bundle
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, Bundle bundle, long requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtra("requestCode", requestCode);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz  指定Activity
     * @param bundle 携带的数据源
     */
    public void toActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param bundle      携带的数据源
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("requestCode", requestCode);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 获取携带的数据
     *
     * @return
     */
    public Bundle getBundle() {
        Intent intent = getIntent();
        Bundle bundle = null;
        if (intent != null) {
            bundle = intent.getExtras();
        }
        return bundle;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     * @return
     */
    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取请求码
     *
     * @return
     */
    public long getRequestCode() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getLongExtra("requestCode", -1L);
        }
        return -1L;
    }
}
```

### BaseParentFragment
#### 使用
```java
public class CustomFragment extends BaseParentFragment {

    @Override
    public void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        
    }

    @Override
    public void setListener(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}
```

#### BaseParentActivity介绍
```java
public abstract class BaseParentFragment extends Fragment {

    public Context context;
    public Activity activity;
    private Toast toast = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
        setListener(view, savedInstanceState);
    }

    public abstract void initView(@NonNull View view, @Nullable Bundle savedInstanceState);

    public abstract void setListener(@NonNull View view, @Nullable Bundle savedInstanceState);

    /**
     * 获取资源文件中的颜色
     *
     * @param id 颜色的资源id
     * @return 返回颜色的色值
     */
    @ColorInt
    public int getResColor(@ColorRes int id) {
        try {
            return getResources().getColor(id);
        } catch (Exception e) {
            return Color.TRANSPARENT;
        }
    }

    /**
     * 获取资源文件图片或者drawable
     *
     * @param id 资源文件的id
     * @return 返回的资源文件
     */
    public Drawable getResDrawable(@DrawableRes int id) {
        try {
            return getResources().getDrawable(id);
        } catch (Exception e) {
            return new ColorDrawable(Color.TRANSPARENT);
        }
    }

    /**
     * 获取布局文件
     *
     * @param resource 布局id
     * @return 返回的布局view
     */
    public View getLayout(@LayoutRes int resource) {
        return LayoutInflater.from(activity).inflate(resource, null);
    }

    /**
     * 获取布局文件
     *
     * @param resource 布局id
     * @param root     布局要加入的父布局ViewGroup
     * @return 返回的布局view
     */
    public View getLayout(@LayoutRes int resource, @Nullable ViewGroup root) {
        return LayoutInflater.from(activity).inflate(resource, root);
    }

    /**
     * 获取布局文件
     *
     * @param resource     布局id
     * @param root         布局要加入的父布局ViewGroup
     * @param attachToRoot
     * @return 返回的布局view
     */
    public View getLayout(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.from(activity).inflate(resource, root, attachToRoot);
    }

    /**
     * 弹出提示
     *
     * @param message 提示的信息
     */
    public void showToast(CharSequence message) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, dp2px(context, 100));
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     *
     * @param message 提示的信息
     * @param gravity 位于屏幕中的位置。例: Gravity.BOTTOM
     */
    public void showToast(CharSequence message, int gravity) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.setGravity(gravity, 0, 0);
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     *
     * @param message 提示的信息
     * @param gravity 位于屏幕中的位置。例: Gravity.BOTTOM
     * @param xOffset x轴上的偏移量
     * @param yOffset y轴上的偏移量
     */
    public void showToast(CharSequence message, int gravity, int xOffset, int yOffset) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.setGravity(gravity, xOffset, yOffset);
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     *
     * @param text 提示的文字
     */
    public void showSnackbar(CharSequence text) {
        View view = getView();
        if (view == null && activity != null) {
            view = activity.findViewById(android.R.id.content);
        }
        if (view != null) {
            Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     * @return
     */
    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz 指定Activity
     */
    public void toActivity(Class<?> clazz) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            activity.startActivity(new Intent(activity, clazz));
        }
    }

    /**
     * 打开指定Activity
     *
     * @param clazz  指定Activity
     * @param bundle 携带的数据源
     */
    public void toActivity(Class<?> clazz, Bundle bundle) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            Intent intent = new Intent(activity, clazz);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, int requestCode) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            activity.startActivityForResult(new Intent(activity, clazz), requestCode);
        }
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, long requestCode) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            Intent intent = new Intent();
            intent.setClass(activity, clazz);
            intent.putExtra("requestCode", requestCode);
            activity.startActivity(intent);
        }
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param bundle      数据bundle
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, Bundle bundle, long requestCode) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            Intent intent = new Intent();
            intent.setClass(activity, clazz);
            intent.putExtra("requestCode", requestCode);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param bundle      携带的数据源
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, Bundle bundle, int requestCode) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            Intent intent = new Intent(activity, clazz);
            intent.putExtras(bundle);
            activity.startActivityForResult(intent, requestCode);
        }
    }

}
```

## 帮助类方法解释

#### TextUtils - 字符串处理工具类
|方法名|描述|参数描述|
|---|---|---|
|isEmpty(CharSequence str)|判断字符串是否为空|str-输入的内容|
|isEmpty(CharSequence str, boolean isTrim)|判断字符串是否为空|str-字符串<br>isTrim-是否将前后空格也算做内容|
|getText(TextView textView)|获取TextView或EditText的控件内容|textView-传入TextView或EditText控件|
|setText(@NonNull TextView textView, CharSequence text, float thickness)|设置字体粗细|textView-显示文字的控件<br>text-要显示的文字<br>thickness-文字的粗细程度,建议0.8f|
|setText(@NonNull TextView textView, CharSequence text, final float thickness, @ColorInt final int color)|设置字体粗细|textView-显示文字的控件<br>text-要显示的文字<br>thickness-文字的粗细程度<br>color-文字的颜色|
|equals(CharSequence a, CharSequence b)|判断两个字符串是否相等|a-字符串a<br>b-字符串b|
|length(CharSequence str)|获取字符串的长度|str-输入的字符串|
|setDrawable(TextView textView, CharSequence charSequence, int resId, @Gravity int gravity)|为TextView设置Drawable图片|textView-文字控件TextView<br>charSequence-设置的文字<br>resId-图片的资源id<br>gravity-位置|
|setDrawable(TextView textView, CharSequence charSequence, int resId, int drawablePadding, @Gravity int gravity)|为TextView设置Drawable图片|textView-文字控件TextView<br>charSequence-设置的文字<br>resId-图片的资源id<br>drawablePadding-文字和Icon的距离<br>gravity-位置|
|setDrawable(TextView textView, CharSequence charSequence, Drawable imgDrawable, @Gravity int gravity)|为TextView设置Drawable图片|textView-文字控件TextView<br>charSequence-设置的文字<br>imgDrawable-图片<br>gravity-位置|
|setDrawable(TextView textView, CharSequence charSequence, Drawable imgDrawable, int drawablePadding, @Gravity int gravity)|为TextView设置Drawable图片|textView-文字控件TextView<br>charSequence-设置的文字<br>imgDrawable-图片<br>drawablePadding-文字和Icon的距离<br>gravity-位置|
|setDrawable(TextView textView, int resId, @Gravity int gravity)|为TextView设置Drawable图片|textView-文字控件TextView<br>resId-图片的资源id<br>gravity-位置|
|setDrawable(TextView textView, int resId, int drawablePadding, @Gravity int gravity)|为TextView设置Drawable图片|textView-文字控件TextView<br>resId-图片的资源id<br>drawablePadding-图片和文字的间距<br>gravity-位置|
|setDrawable(TextView textView, Drawable imgDrawable, @Gravity int gravity)|为TextView设置Drawable图片|textView-文字控件TextView<br>imgDrawable-图片<br>gravity-位置|
|setDrawable(TextView textView, Drawable imgDrawable, int drawablePadding, @Gravity int gravity)|为TextView设置Drawable图片|textView-文字控件TextView<br>imgDrawable-图片<br>drawablePadding-图片和文字的间距<br>gravity-位置|
|setDrawable(TextView textView, int drawablePadding, Drawable leftDrawable, Drawable topDrawable, Drawable rightDrawable, Drawable bottomDrawable)|为TextView设置Drawable图片|textView-文字控件TextView<br>drawablePadding-图片和文字的间距<br>leftDrawable-左图片<br>topDrawable-上图片<br>rightDrawable-右图片<br>bottomDrawable-下图片|
|setDrawable(TextView textView, CharSequence charSequence, int drawablePadding, Drawable leftDrawable, Drawable topDrawable, Drawable rightDrawable, Drawable bottomDrawable)|为TextView设置Drawable图片|textView-文字控件TextView<br>charSequence-文字内容<br>drawablePadding-图片和文字的间距<br>leftDrawable-左图片<br>topDrawable-上图片<br>rightDrawable-右图片<br>bottomDrawable-下图片|

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
|formatTimeCalendar(@NonNull Calendar calendar, String template)|通过Calendar获取格式化后的时间串|calendar-Calendar时间<br>template-格式化模板。例如: yyyy-MM-dd HH:mm:ss|
|formatTimeDate(@NonNull Date date, String template)|通过Date获取格式化后的时间串|date-Date时间<br>template-格式化模板。例如: yyyy-MM-dd HH:mm:ss|
|stringDateToCalendar(String strDate, String template)|将字符串时间转换为Calendar|strDate-时间字符串<br>template-时间模板|
|stringDateToDate(String strDate, String template)|将字符串时间转换为Date|strDate-时间字符串<br>template-时间模板|

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

#### NetUtils - 网络工具类
|方法名|描述|参数描述|
|---|---|---|
|getNetworkType(Context context)|获取网络类型|context-上下文|
|getNetworkTypeName(Context context)|获取网络名称|context-上下文|
|isConnected(Context context)|网络是否连接|context-上下文|
|isNetworkAvailable(Context context)|网络可用性|context-上下文|
|isWiFi(Context context)|是否是wifi|context-上下文|
|openNetSetting(Activity activity)|打开网络设置界面|activity-上下文|
|setWifiEnabled(Context context, boolean enabled)|设置wifi状态|context-上下文<br>enabled-设置wifi状态|
|getWifiScanResults(Context context)|获取wifi列表|context-上下文|
|getScanResultsByBSSID(Context context, String bssid)|过滤扫描结果|context-上下文<br>bssid-访问点的地址|
|getWifiConnectionInfo(Context context)|获取wifi连接信息|context-上下文|

#### PhoneUtils - 手机号操作工具类
|方法名|描述|参数描述|
|---|---|---|
|isMobileNO(String mobileNums)|判断字符串是否符合手机号码格式|mobileNums-待检测的手机号字符串|
|callPhone(Context context, String phoneNum, boolean isCall)|拨打电话|phoneNum-手机号<br>isCall-true:直接拨打电话, false:跳转到拨号界面，用户手动点击拨打|
|hiddenPhone(String phoneNO)|隐藏手机号码中间四位|phoneNO-手机号码|

#### ResUtils - 通过资源id获取相关内容
|方法名|描述|参数描述|
|---|---|---|
|getDrawable(@NonNull Context context, @DrawableRes int resId)|获取资源文件中的Drawable|context-上下文<br>resId-drawable的资源id|
|getString(Context context, @StringRes int resId)|获取资源文件中的字符串|context-上下文<br>resId-字符串的资源id|
|getStrings(Context context, @ArrayRes int resId)|获取资源文件中的字符串数组|context-上下文<br>resId-字符串数组的资源id|
|getColor(@NonNull Context context, @ColorRes int color)|获取资源文件中的颜色|context-上下文<br>color-颜色的资源id|
|getColorStateList(Context context, @ColorRes int resId)|获取资源文件中的颜色样式文件|context-上下文<br>resId-颜色样式文件的资源id|

#### SaveImageUtils - 保存图片工具类
|方法名|描述|参数描述|
|---|---|---|
|savePicture(Context context, Bitmap bitmap)|保存图片|context-上下文<br>bitmap-图片Bitmap|
|savePicture(Context context, Drawable drawable)|保存图片|context-上下文<br>drawable-图片Drawable|
|savePicture(Context context, Drawable drawable, CharSequence name)|保存图片|context-上下文<br>drawable-图片Drawable<br>name-图片名称|
|savePicture(Context context, Bitmap bitmap, CharSequence name)|将图片Bitmap保存到本地|context-上下文<br>bitmap-图片Bitmap<br>name-图片名称|
|drawable2Bitmap(Drawable drawable)|Drawable转换成Bitmap|drawable-源图片的Drawable|

#### ScreenshotUtils - 截图截屏工具
|方法名|描述|参数描述|
|---|---|---|
|getBitmapByView(ViewGroup viewGroup)|将ViewGroup保存成图片|viewGroup-所要截图的父布局|
|saveBitmap(Context context, Bitmap bmp, String imageName)|将Bitmap保存到本地|context-上下文<br>bmp-图片Bitmap<br>imageName-图片名称|

#### SMSUtils - 短信工具类
|方法名|描述|参数描述|
|---|---|---|
|sendSMS(Context context, String phoneNumber, String message)|调起系统发短信功能|context-上下文<br>phoneNumber-手机号码<br>message-发送的信息|

#### SPUtils - SharedPreferences工具类
|方法名|描述|参数描述|
|---|---|---|
|init(Context context, String spName)|初始化SharedPreferences|context-上下文<br>spName-SharedPreferences名称|
|putInt(String key, int value)|保存int类型的数据|key-SP的键名<br>value-值|
|getInt(String key, int defaultvalue)|获取int类型的数据|key-SP的键名<br>defaultvalue-默认值|
|getInt(String key)|获取int类型的数据|key-SP的键名|
|putString(String key, String value)|保存String类型的数据|key-SP的键名<br>value-值|
|getString(String key, String defaultvalue)|获取String类型的数据|key-SP的键名<br>defaultvalue-默认值|
|getString(String key)|获取String类型的数据|key-SP的键名|
|putBoolean(String key, boolean value)|保存boolean类型的值|key-SP的键名<br>value-值|
|getBoolean(String key, boolean defValue)|获取boolean类型的数据|key-SP的键名<br>defValue-默认值|
|getBoolean(String key)|获取boolean类型的数据|key-SP的键名|
|putFloat(String key, float value)|保存float类型的数据|key-SP的键名<br>value-值|
|getFloat(String key, float defValue)|获取float类型的数据|key-SP的键名<br>defValue-默认值|
|getFloat(String key)|获取float类型的数据|key-SP的键名|
|putLong(String key, long value)|保存long类型的数据|key-SP的键名<br>value-值|
|getLong(String key, long defValue)|获取long类型的数据|key-SP的键名<br>defValue-默认值|
|getLong(String key)|获取long类型的数据|key-SP的键名|
|clear()|清空SP里所有数据||
|remove(String key)|删除SP里指定key对应的数据项|key SP的键名|
|contains(String key)|查看sp文件里面是否存在此 key|key-SP的键名|

#### SystemRecordUtils - 调用系统的视频录制
|方法名|描述|参数描述|
|---|---|---|
|recordVideo(Activity activity)|调用系统相机录视视频|activity<br>上下文|
|recordVideo(Activity activity, String fileName)|调用系统相机录视视频|activity-上下文<br>fileName-视频名称|
|recordVideo(Activity activity, String filePath, String fileName)|调用系统相机录视视频|activity-上下文<br>filePath-视频所在文件夹路径<br>fileName-视频名称|
|recordVideo(Activity activity, String filePath, String fileName, int requestCode)|调用系统相机录视视频|activity-上下文<br>filePath-视频所在文件夹路径<br>fileName-视频名称<br>requestCode-请求码|
|recordVideo(Activity activity, String filePath, String fileName, int quality, int duration, int requestCode)|调用系统相机录视视频|activity-上下文<br>filePath-视频所在文件夹路径<br>fileName-视频名称<br>quality-录制视频的质量，从 0-1，越大表示质量越好，同时视频也越大<br>duration-设置视频录制的最长时间,单位秒<br>requestCode-请求码|
|getVideoInformation(Context context, Uri videoUri)|获取视频信息|context-上下文<br>videoUri-视频的Uri|


