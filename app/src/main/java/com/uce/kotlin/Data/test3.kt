package com.test.kotlin.Data

//sealed class UiOp {
//    object Show: UiOp()
//    object Hide: UiOp()
//}
//fun execute(view: View, op: UiOp) = when (op) {
//    UiOp.Show -> view.visibility = View.VISIBLE
//    UiOp.Hide -> view.visibility = View.GONE
//}

sealed class UiOp {
    object Show: UiOp()
    object Hide: UiOp()
    class TranslateX(val px: Float): UiOp()
    class TranslateY(val px: Float): UiOp()
}

fun execute(view: View, op: UiOp) = when (op) {
    UiOp.Show -> view.visibility = View.VISIBLE
    UiOp.Hide -> view.visibility = View.GONE
    is UiOp.TranslateX -> view.translationX = op.px // 这个 when 语句分支不仅告诉 view 要水平移动，还告诉 view 需要移动多少距离，这是枚举等 Java 传统思想不容易实现的
    is UiOp.TranslateY -> view.translationY = op.px
}