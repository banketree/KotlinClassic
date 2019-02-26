package com.test.kotlin.ObjectExpression

//class MyClass {
//    companion object Factory {
//        fun create(): MyClass = MyClass()
//    }
//}
//
//val instance = MyClass.create()   // 访问到对象的内部元素

class MyClass {
    companion object {
    }
}

val x = MyClass.Companion

