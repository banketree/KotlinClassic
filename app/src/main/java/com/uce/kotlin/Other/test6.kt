package com.test.kotlin.Other

object.also{
//todo
}

@kotlin.internal.InlineOnly
@SinceKotlin("1.1")
public inline fun <T> T.also(block: (T) -> Unit): T { block(this); return this }



//kotlin

fun main(args: Array<String>) {
    val result = "testLet".also {
        println(it.length)
        1000
    }
    println(result)
}

//java

public final class AlsoFunctionKt {
    public static final void main(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        String var2 = "testLet";
        int var4 = var2.length();
        System.out.println(var4);
        System.out.println(var2);
    }
}


https://www.jb51.net/article/136517.htm