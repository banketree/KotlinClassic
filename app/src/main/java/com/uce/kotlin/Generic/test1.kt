package com.test.kotlin.Generic

//class Box<T>(t: T) {
//    var value = t
//}
//
//val box: Box<Int> = Box<Int>(1)
//// 或者
//val box = Box(1) // 编译器会进行类型推断，1 类型 Int，所以编译器知道我们说的是 Box<Int>。

class Box<T>(t : T) {
    var value = t
}

fun main(args: Array<String>) {
    var boxInt = Box<Int>(10)
    var boxString = Box<String>("Runoob")

    println(boxInt.value)
    println(boxString.value)
}