package com.test.kotlin.Extend

fun Any?.toString(): String {
    if (this == null) return "null"
    // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
    // 解析为 Any 类的成员函数
    return toString()
}

fun main(arg: Array<String>) {
    var t = null
    println(t.toString())
}

//除了函数，Kotlin 也支持属性对属性进行扩展:
val <T> List<T>.lastIndex: Int
    get() = size - 1