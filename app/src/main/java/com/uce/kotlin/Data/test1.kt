package com.test.kotlin.Data

data class User(val name: String, val age: Int)

fun copy(name: String = this.name, age: Int = this.age) = User(name, age)

data class User(val name: String, val age: Int)


fun main(args: Array<String>) {
    val jack = User(name = "Jack", age = 1)
    val olderJack = jack.copy(age = 2)
    println(jack)
    println(olderJack)
}

