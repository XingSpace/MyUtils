#include <jni.h>
#include <string>
#include <android/log.h>
#include <vector>

#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, "C_plus-C++", __VA_ARGS__));
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "C_plus-C++", __VA_ARGS__));

extern "C" JNIEXPORT void JNICALL
Java_com_xing_app_myutils_Utils_JNIUtils_gray(JNIEnv * env, jobject obj, jintArray pixArr){
    jint *arr = env->GetIntArrayElements(pixArr,NULL);//获取数组指针
    int count = env->GetArrayLength(pixArr);//获取数组长度
    for (int i = 0; i < count; ++i) {//遍历改变每一个像素的色彩值，全部改为黑白

        int R = (arr[i] >> 16) & 0xff;
        int G = (arr[i] >> 8) & 0xff;
        int B = (arr[i]) & 0xff;

        int aver = (R + G + B) / 3;

        arr[i] = (0xff<<24) | ((aver&0xff)<<16) | ((aver&0xff)<<8) | (aver&0xff);

    }
    env->ReleaseIntArrayElements(pixArr,arr,0);//释放掉数组、指针资源
}

extern "C" JNIEXPORT void JNICALL
Java_com_xing_app_myutils_Utils_JNIUtils_setAlpha(JNIEnv * env, jobject obj, jintArray pixArr,jfloat alpha){
    jint *arr = env->GetIntArrayElements(pixArr,NULL);//获取数组指针
    int count = env->GetArrayLength(pixArr);//获取数组长度
    int A = (int)(0xff * alpha) ^ 0xff;//修改透明度

    for (int i = 0; i < count; ++i) {//遍历改变每一个像素的透明度
        arr[i] = ((A&0xff)<<24) | (arr[i] & 0xffffff);
    }

    env->ReleaseIntArrayElements(pixArr,arr,0);//释放掉数组、指针资源
}
