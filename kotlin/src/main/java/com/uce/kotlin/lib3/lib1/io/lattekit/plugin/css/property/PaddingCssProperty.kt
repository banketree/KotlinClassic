package io.lattekit.plugin.css.property

import android.content.Context
import io.lattekit.plugin.css.NodeStyle
import io.lattekit.plugin.css.declaration.LengthValue
import io.lattekit.plugin.css.declaration.PaddingValue
import io.lattekit.view.NativeView

/**
 * Created by maan on 2/23/16.
 */
open class PaddingCssProperty : CssProperty("padding") {

    override val INHERITED = true
    override val INITIAL_VALUE: String? = "0px"


    var defaultPaddingLeft : Int? = null;
    var defaultPaddingTop : Int?  = null;
    var defaultPaddingRight : Int?  = null;
    var defaultPaddingBottom : Int?  = null;

    var paddingLeft : Int? = 0
    var paddingTop : Int? = 0
    var paddingRight : Int? = 0
    var paddingBottom : Int? = 0

    fun readShorthand(values : List<LengthValue>, context : Context) {
        if (values.size == 1) {
            paddingTop = values[0].inPixels(context).toInt()
            paddingRight = values[0].inPixels(context).toInt()
            paddingBottom = values[0].inPixels(context).toInt()
            paddingLeft = values[0].inPixels(context).toInt()
        } else if (values.size == 2) {
            paddingTop = values[0].inPixels(context).toInt()
            paddingRight = values[1].inPixels(context).toInt()
            paddingBottom = values[0].inPixels(context).toInt()
            paddingLeft = values[1].inPixels(context).toInt()
        } else if (values.size == 3) {
            paddingTop = values[0].inPixels(context).toInt()
            paddingRight = values[1].inPixels(context).toInt()
            paddingBottom = values[2].inPixels(context).toInt()
            paddingLeft = values[1].inPixels(context).toInt()
        } else if (values.size == 4) {
            paddingTop = values[0].inPixels(context).toInt()
            paddingRight = values[1].inPixels(context).toInt()
            paddingBottom = values[2].inPixels(context).toInt()
            paddingLeft = values[3].inPixels(context).toInt()
        }
    }

    override fun computeValue(context: Context, view: NativeView, style : NodeStyle) {
        paddingLeft = defaultPaddingLeft
        paddingTop = defaultPaddingTop
        paddingRight = defaultPaddingRight
        paddingBottom = defaultPaddingBottom

        var declarations = style.getDeclarations("padding", "padding-top", "padding-right", "padding-bottom", "padding-left")
        declarations.forEach {
            var values = (it.value as PaddingValue).paddingValues
            if (it.propertyName == "padding") {
                readShorthand(values, context)
            } else when (it.propertyName) {
                "padding-top" -> paddingTop = values[0].inPixels(context).toInt()
                "padding-right" -> paddingRight = values[0].inPixels(context).toInt()
                "padding-bottom" -> paddingBottom = values[0].inPixels(context).toInt()
                "padding-left" -> paddingLeft = values[0].inPixels(context).toInt()
            }
        }
    }
    override fun apply(view: NativeView, style: NodeStyle) {
        var pLeft = paddingLeft ?: view.androidView!!.paddingLeft
        var pTop = paddingTop ?: view.androidView!!.paddingTop
        var pRight = paddingRight ?: view.androidView!!.paddingRight
        var pBottom = paddingBottom ?: view.androidView!!.paddingBottom

        view.androidView?.setPadding(pLeft, pTop, pRight, pBottom)
    }
}
