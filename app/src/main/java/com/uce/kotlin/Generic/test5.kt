package com.test.kotlin.Generic

// 定义一个支持逆变的类
class Runoob<in A>(a: A) {
    fun foo(a: A) {
    }
}

fun main(args: Array<String>) {
    var strDCo = Runoob("a")
    var anyDCo = Runoob<Any>("b")
    strDCo = anyDCo
}