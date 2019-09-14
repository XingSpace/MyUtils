# MyUtils
常用的Android工具类大集合

+ BitmapUtil
	+ getRoundedCornerBitmap(Bitmap bitmap, float roundPx)

+ DrawableBuilder
	+ createGradient()

+ FileUtil
	+ deleteFile(File file)
	+ moveFile(File original, File target,boolean isCover)
	+ moveFiles(File original, File target, boolean isCover)

+ HttpUtil
	+ uploadFile(String url, Map<String, String> map, File file ,String fileKey)
	+ downloadFile(String sourcePath, String distPath, String fileName, boolean isForce)
	+ encode(String str)
	+ decode(String str)

+ IOUtil
	+ writeToFile(String data, File file)
	+ readFile(File file)

+ PermissionUtil
	+ permissionEntry(Activity activity,Context context,boolean isSubmit,int mRequestCode)
	+ getPermission(String permissions, Context context)

+ ThreadUtil
    + isRunMainThread()
    + runOnMainThread(Runnable runnable)
    + runOnChildThread(Runnable runnable)
    + runOnMainThreadDelayed(Runnable runnable, long time)
    + runOnChildThreadDelayed(Runnable runnable, long time)
