# Android开发工具类-Utils
最新版本1.0.6  [![](https://www.jitpack.io/v/BenShanYang/Utils.svg)](https://www.jitpack.io/#BenShanYang/Utils)


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

#### AppUtils
|方法名|描述|参数描述
|---|---|---|
|isInstalled(Context context, String packageName)|判断是否安装某个App|context-上下文<br>packageName-包名|
|isInstalledQQ(Context context)|是否安装了QQ|context-上下文|
|isInstalledWeiChat(Context context)|是否安装了微信|context-上下文|
|isInstalledWeiBo(Context context)|是否安装了新浪微博|context-上下文|

[Utils Api文档](https://github.com/BenShanYang/JavaDoc)
