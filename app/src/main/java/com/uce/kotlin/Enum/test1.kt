package com.test.kotlin.Enum

enum class Color{
    RED,BLACK,BLUE,GREEN,WHITE
}

enum class Shape(value:Int){
    ovel(100),
    rectangle(200)
}

enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}

//EnumClass.valueOf(value: String): EnumClass  // 转换指定 name 为枚举值，若未匹配成功，会抛出IllegalArgumentException
//EnumClass.values(): Array<EnumClass>        // 以数组的形式，返回枚举值

