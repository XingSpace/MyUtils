# MyUtils
常用的Android工具类大集合

+ BitmapUtil
	+ getRoundedBitmapDrawable(Bitmap bitmap, float roundPx)
	    - 返回一个圆角的Drawable

	+ gaussBlur(Context context, Bitmap image, float radius)
	    - 返回一个高斯模糊处理过的Bitmap

	+ gray(Bitmap bitmap)
	    - 返回一个黑白处理过的Bitmap

	+ setAlpha(Bitmap bitmap,float alpha)
	    - 调整一个Bitmap的透明度，alpha值介于1和0之间，1表示完全透明，0表示不透明

	+ BitmapToDrawable(Bitmap bitmap)
	    - 将Bitmap转换为Drawable

	+ DrawableToBitmap(Drawable drawable)
	    - 将Drawable转换为Bitmap

	+ base64ToBitmap(String base64)
	    - 将base64字符串转换为Bitmap

	+ bitmapToBase64(Bitmap bitmap)
	    - 将bitmap转换为base64

	+ recycleBitmap(Bitmap bitmap)
	    - 回收bitmap所占用的内存

+ DrawableBuilder
//用于建造一个圆角背景Drawable,主要适用与适配多种屏幕时，防止圆角变形
	+ create()
	    - 本类提供了一个链式建造者模式，最后通过create方法创建一个GradientDrawable

	+ createStateDrawable(Drawable down,Drawable up)
	    - 创建一个 响应点击效果的 Drawable，@down 按下时的Drawable，@up 抬起时的Drawable

	+ createStateDrawable(Bitmap down, Bitmap up)
	    - 同上一种

	+ createLayer(Drawable...drawables)
	    - 创建一个叠加的效果的Drawable

+ FileUtil
	+ deleteFile(File file)
	    - 删除指定文件夹

	+ moveFile(File original, File target,boolean isCover)
	    - 移动指定文件

	+ moveFiles(File original, File target, boolean isCover)
	    - 移动指定文件夹

	+ copyFile(File from, File to, boolean isConver)
	    - 复制文件

+ HttpUtil
	+ uploadFile(String url, Map<String, String> map, File file ,String fileKey)
	    - 上传文件和指定参数，依赖OkHttp

	+ downloadFile(String sourcePath, String distPath, String fileName, boolean isForce)
		- 下载文件，依赖OkHttp

    + execPing(String ip, int tryMax)
        - 测试网络是否能够ping通，@tryMax 最多尝试次数

    + getMimeType(String fileName)
        - 网络传输时获取传输文件所属媒体类型

	+ encode(String str)
	    - 用utf-8编码

	+ decode(String str)
	    - 用utf-8解码

+ IOUtil
	+ writeToFile(String data, File file)
	    - 将字符串写入指定文件，依赖Okio
	+ readFile(File file)
	    - 读取指定文件,依赖Okio

+ PermissionUtil
	+ permissionEntry(Activity activity,Context context,boolean isSubmit,int mRequestCode)
	    - 权限确认方法

	+ getPermission(String permissions, Context context)
	    - 请求某一权限的方法

+ ThreadUtil
    + isRunMainThread()
        - 判断当前是否运行在主线程上

    + runOnMainThread(Runnable runnable)
        - 将runnable在主线程上运行

    + runOnChildThread(Runnable runnable)
        - 将runnable在普通子线程上运行

    + runOnMainThreadDelayed(Runnable runnable, long time)
        - 在主线程上延迟运行runnable

    + runOnChildThreadDelayed(Runnable runnable, long time)
        - 在普通子线程上延迟运行runnable

    + removeDelayedThread(String tag)
        - 移除掉一个延迟执行的线程任务，@tag 是runOnMainThreadDelayed、runOnChildThreadDelayed方法的返回值

    + runOnChildThreadLoop(Runnable runnable, String tag, long time)
        - 开启一个循环执行的线程，每隔time毫秒运行一次

    + stopChildThreadLoopWithTag(String tag)
        - 停止被标记为tag的循环子线程

+ StringUtil
    + isEmpty(String s)
        - 判断字符串是否为空

    + isEmpty(String...strings)
        - 判断字符数组是否有为空的字符串

+ MathUtil
    + calcCombination(int m,int n)
        - 计算C（n,m）组合数

    + calcPermutation(int m,int n)
        - 计算P（n,m）排列数

    + calcPermutationRepetition(int m,int n)
        - 计算PR（n，m）可重排列

    + calcFactorial(int x)
        - 计算阶乘

    + calcFactorial(String s)
        - 计算阶乘

    + calcFactorial(BigInteger b)
        - 计算阶乘

    + isOddNum(int i)
        - 判断一个数是否为奇数

    + log(double value, double base)
        - 返回对数，@value 真数 @base 底数

+ MediaUtil
    + setSource(String filePath)
        - 设置视频资源路径

    + decodeFrame(long timeMs)
        - 解码视频中的某一帧，以bitmap返回，@timeMs 毫秒为单位

    + getFileLength()
        - 获取视频长度，毫秒为单位

+ CipherUtil
    + encryptDES(String data, String key)
        - DES对称加密方法

    + decryptDES(String data, String key)
        - DES对称解码方法

    + encryptMD5(String dataStr,String slat)
        - MD5加密字符串

    + getFileMD5(String path)
        - 获取指定文件的MD5码，@path 文件路径

    + byte2hex(byte[] b)
        - 数组转换为16进制字符串

    + hex2byte(byte[] b)
        - 字符串转换为byte类型数组，@b 传入一个String.getBytes()

+ ToastUtil
    + show(Context context,String text)
        - 防止吐丝现象

+ DateUtil
    + convert2Long(String date,String format)
        - 时间字符串转换为long类型时间戳，@date 时间格式字符串，@format 标准时间格式

    + convert2Date(String date,String format)
        - 时间字符串转换为Date类型时间对象，@date 时间格式字符串，@format 标准时间格式

    + convert2String(long time,String format)
        - 时间戳转换为String时间格式

    + convert2String(Date date,String format)
        - 时间对象转换为String时间格式

    + getYYYYMMDD()
        - 获取年月日时间字符串，当前时间

    + getYYYYMMDDHHMMSS()
        - 获取年月日时分秒字符串，当前时间

+ UIUtil
    + px2dp(Context context, float pxValue)
        - px单位转为dp单位

    + dp2px(Context context, float dipValue)
        - dp单位转为px单位

    + px2sp(Context context, float pxValue)
        - px单位转为sp单位

    + sp2px(Context context, float spValue)
        - sp单位转为px单位

    + getDisplaysLength(Context context)
        - 获取当前屏幕数量

    + getDisplay(Context context,int index)
        - 获取index下标的屏幕对象

    + getDisplays(Context context)
        - 获取当前设备所有的屏幕对象

    + getScreenSize(Context context)
        - 获取主屏的尺寸

    + highLightingTextColor(String text, String color)
        - 将text中所有的数字和字母进行高亮处理

    + setScale(View view, float scale)
        - 等比例拉伸view，包括它的子View

+ RoundImageView
    - 这个包是从'com.makeramen:roundedimageview:2.3.0'中集成的
    更多的使用方法可以前往[RoundedImageView](https://github.com/vinc3m1/RoundedImageView)中查看

+ RoundedDrawableBuilder
    - 本类使用链式编程方式创建一个RoundedDrawable

+ LogUtil（需要有写入和读取权限）
    + init(String appName)
        - 本方法用于初始化记录日志的文件夹 @appName 就是文件夹名称

    + d(String s)
        - 会将日志以debug标签记录下来，并写入到日志中

    + e(String s)
        - 会将日志以error标签记录下来，并写入到日志中