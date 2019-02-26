package com.test.kotlin.Delegate

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


//通过定义 provideDelegate 操作符，可以扩展创建属性实现所委托对象的逻辑。 如果 by 右侧所使用的对象将 provideDelegate 定义为成员或扩展函数，那么会调用该函数来 创建属性委托实例。
//provideDelegate 的一个可能的使用场景是在创建属性时（而不仅在其 getter 或 setter 中）检查属性一致性。
//例如，如果要在绑定之前检查属性名称，可以这样写：
//class ResourceLoader<T>(id: ResourceID<T>) {
//    operator fun provideDelegate(
//            thisRef: MyUI,
//            prop: KProperty<*>
//    ): ReadOnlyProperty<MyUI, T> {
//        checkProperty(thisRef, prop.name)
//        // 创建委托
//    }
//
//    private fun checkProperty(thisRef: MyUI, name: String) {
////        ……
//    }
//}
//
//fun <T> bindResource(id: ResourceID<T>): ResourceLoader<T> {
////    ……
//}
//
//class MyUI {
//    val image by bindResource(ResourceID.image_id)
//    val text by bindResource(ResourceID.text_id)
//}

//provideDelegate 的参数与 getValue 相同：
//thisRef —— 必须与 属性所有者 类型（对于扩展属性——指被扩展的类型）相同或者是它的超类型
//property —— 必须是类型 KProperty<*> 或其超类型。
//在创建 MyUI 实例期间，为每个属性调用 provideDelegate 方法，并立即执行必要的验证。
//如果没有这种拦截属性与其委托之间的绑定的能力，为了实现相同的功能， 你必须显式传递属性名，这不是很方便：

// 检查属性名称而不使用“provideDelegate”功能
//class MyUI {
//    val image by bindResource(ResourceID.image_id, "image")
//    val text by bindResource(ResourceID.text_id, "text")
//}
//
//fun <T> MyUI.bindResource(
//        id: ResourceID<T>,
//        propertyName: String
//): ReadOnlyProperty<MyUI, T> {
//    checkProperty(this, propertyName)
//    // 创建委托
//}

//在生成的代码中，会调用 provideDelegate 方法来初始化辅助的 prop$delegate 属性。 比较对于属性声明 val prop: Type by MyDelegate() 生成的代码与 上面（当 provideDelegate 方法不存在时）生成的代码：
//
//class C {
//    var prop: Type by MyDelegate()
//}
//
//// 这段代码是当“provideDelegate”功能可用时
//// 由编译器生成的代码：
//class C {
//    // 调用“provideDelegate”来创建额外的“delegate”属性
//    private val prop$delegate = MyDelegate().provideDelegate(this, this::prop)
//    val prop: Type
//        get() = prop$delegate.getValue(this, this::prop)
//}
//请注意，provideDelegate 方法只影响辅助属性的创建，并不会影响为 getter 或 setter 生成的代码。