package com.test.kotlin.Delegate

fun example(computeFoo: () -> Foo) {
    val memoizedFoo by lazy(computeFoo)

    if (memoizedFoo != null) {
        print("")
//        memoizedFoo.doSomething()
    }
}