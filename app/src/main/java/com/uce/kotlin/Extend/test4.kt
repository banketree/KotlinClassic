package com.test.kotlin.Extend

class C1 {
    fun foo() { println("成员函数") }
}

fun C1.foo() { println("扩展函数") }

//若扩展函数和成员函数一致，则使用该函数时，会优先使用成员函数。
fun main(arg:Array<String>){
    var c = C1()
    c.foo()
}