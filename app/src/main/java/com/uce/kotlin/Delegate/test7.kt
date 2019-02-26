package com.test.kotlin.Delegate

import kotlin.properties.Delegates

class Foo {
    var notNullBar: String by Delegates.notNull<String>()
}


fun main7() {
    val foo = Foo()
    foo.notNullBar = "bar"
    println(foo.notNullBar)
}
