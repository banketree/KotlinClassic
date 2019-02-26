package io.lattekit.plugin.css.property

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import io.lattekit.plugin.css.NodeStyle
import io.lattekit.plugin.css.declaration.StringValue
import io.lattekit.view.NativeView

/**
 * Created by maan on 2/25/16.
 */
class TextAlignCssProperty : CssProperty("text-align") {

    val VALUES = mapOf(
        "center" to Gravity.CENTER_HORIZONTAL,
        "start" to  Gravity.START,
        "end" to  Gravity.END,
        "left" to  Gravity.LEFT,
        "right" to  Gravity.RIGHT
    )

    override val INHERITED = true
    override val INITIAL_VALUE: String? = "start"

    var textAlign : Int = Gravity.START

    override fun computeValue(context: Context, view: NativeView, style: NodeStyle) {
        if (view.androidView is TextView) {
            textAlign = (view.androidView as TextView).gravity
        }
        var declaration = style.getDeclaration("text-align")
        if (declaration != null) {
            textAlign = VALUES[(declaration.value as StringValue).valueString] ?: textAlign
        }
    }

    override fun apply(view: NativeView, style: NodeStyle) {
        if (view.androidView is TextView) {
            (view.androidView as TextView).gravity = textAlign
        }
    }
}
