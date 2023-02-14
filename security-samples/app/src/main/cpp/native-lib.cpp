#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_security_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string apiKey = "abcxyz";
    return env->NewStringUTF(apiKey.c_str());
}
