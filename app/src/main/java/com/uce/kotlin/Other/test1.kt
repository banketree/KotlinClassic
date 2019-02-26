package com.test.kotlin.Other

object.let{
    it.todo()//在函数体内使用it替代object对象去访问其公有的属性和方法
    ...
}

//另一种用途 判断object为null的操作
object?.let{//表示object不为null的条件下，才会去执行let函数体
    it.todo()
}

@kotlin.internal.InlineOnly
public inline fun <T, R> T.let(block: (T) -> R): R = block(this)

//kotlin

fun main(args: Array<String>) {
    val result = "testLet".let {
        println(it.length)
        1000
    }
    println(result)
}

//java

public final class LetFunctionKt {
    public static final void main(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        String var2 = "testLet";
        int var4 = var2.length();
        System.out.println(var4);
        int result = 1000;
        System.out.println(result);
    }
}


mVideoPlayer?.let {
    it.setVideoView(activity.course_video_view)
    it.setControllerView(activity.course_video_controller_view)
    it.setCurtainView(activity.course_video_curtain_view)
}