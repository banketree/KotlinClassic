package com.test.kotlin.Delegate

import kotlin.properties.Delegates

class User {
    var name: String by Delegates.observable("初始值") { prop, old, new ->
        println("旧值：$old -> 新值：$new")
    }
}

fun main4() {
    val user = User()
    user.name = "第一次赋值"
    user.name = "第二次赋值"
}