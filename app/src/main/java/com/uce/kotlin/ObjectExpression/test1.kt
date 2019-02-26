package com.test.kotlin.ObjectExpression

//window.addMouseListener(object : MouseAdapter() {
//    override fun mouseClicked(e: MouseEvent) {
//        // ...
//    }
//    override fun mouseEntered(e: MouseEvent) {
//        // ...
//    }
//})

//open class A(x: Int) {
//    public open val y: Int = x
//}
//
//interface B {……}
//
//val ab: A = object : A(1), B {
//    override val y = 15
//}

fun main(args: Array<String>) {
    val site = object {
        var name: String = "菜鸟教程"
        var url: String = "www.runoob.com"
    }
    println(site.name)
    println(site.url)
}