# MyUtils
常用的Android工具类大集合

+ BitmapUtil
	+ getRoundedCornerBitmap(Bitmap bitmap, float roundPx)
	    - 返回一个圆角的Bitmap

+ DrawableBuilder
//用于建造一个圆角背景Drawable,
  主要适用与适配多种屏幕时，防止圆角变形
	+ createGradient()

+ FileUtil
	+ deleteFile(File file)
	    - 删除指定文件夹
	+ moveFile(File original, File target,boolean isCover)
	    - 移动指定文件夹
	+ moveFiles(File original, File target, boolean isCover)

+ HttpUtil
	+ uploadFile(String url, Map<String, String> map, File file ,String fileKey)
	    - 上传文件和指定参数，依赖OkHttp
	+ downloadFile(String sourcePath, String distPath, String fileName, boolean isForce)
		- 下载文件，依赖OkHttp
	+ encode(String str)
	+ decode(String str)

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
    + runOnChildThreadLoop(Runnable runnable, String tag, long time)
        - 开启一个循环执行的线程，每隔time毫秒运行一次
    + stopChildThreadLoopWithTag(String tag)
        - 停止被标记为tag的循环子线程

+ StringUtil
    + isEmpty(String s)
        - 判断字符串是否为空
    + isEmpty(String...strings)
        - 判断字符数组是否有为空的字符串

+ ProbabilityUtil
    + CalcCombination(int m,int n)
        - 计算C（n,m）组合数
    + CalcPermutation(int m,int n)
        - 计算P（n,m）排列数
    + CalcFactorial(int x)
        - 计算阶乘
    + CalcFactorial(String s)
        - 计算阶乘
    + CalcFactorial(BigInteger b)
        - 计算阶乘
