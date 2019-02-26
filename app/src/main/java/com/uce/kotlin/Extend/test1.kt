package com.test.kotlin.Extend

//fun receiverType.functionName(params){
//    body
//}

class User(var name:String)

/**扩展函数**/
fun User.Print(){
    print("用户名 $name")
}

fun main(arg:Array<String>){
    var user = User("Runoob")
    user.Print()
}
