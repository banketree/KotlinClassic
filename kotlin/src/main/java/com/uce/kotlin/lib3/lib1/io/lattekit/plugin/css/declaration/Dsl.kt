package io.lattekit.plugin.css.declaration

import android.app.Application
import android.util.Log
import io.lattekit.plugin.css.CssParser
import io.lattekit.view.LatteView

/**
 * Created by maan on 2/26/16.
 */

var GLOBAL_STYLESHEETS = mutableListOf<Stylesheet>()

inline fun css( init: Stylesheet.() -> Unit)  : Stylesheet {
    var stylesheet = Stylesheet()
    stylesheet.init()
    GLOBAL_STYLESHEETS.add(stylesheet)
    return stylesheet
}
inline fun Application.css(cssFile : String)  {
    var isFile = CSS_FILE_PATH_RE.matches(cssFile.trim())
    if (isFile) {
        var cssList = GLOBAL_STYLESHEETS
        try {
            var stylesheet = Stylesheet.getStylesheet(cssFile.trim())
            GLOBAL_STYLESHEETS.add(stylesheet)
        } catch (e :Exception) {
            Log.d("Latte", "WARNING: COULDN'T FIND STYLESHEET ${cssFile}")
        }
    } else {
        Log.d("Latte", "WARNING: INVALID CSS FILE PATH ${cssFile}")
    }
}



var CSS_FILE_PATH_RE = Regex("""([a-zA-Z0-9]+)(\.[a-zA-Z0-9]+)*\/([a-zA-Z0-9]+)\.css""")

inline fun LatteView.css(stylesheet : Stylesheet)  {
    var css = dataOrPut("css", { mutableListOf<Any>() }) as MutableList<Any>
    css.add(stylesheet)
}

inline fun LatteView.css(stylesheet : String)  {
    var isFile = CSS_FILE_PATH_RE.matches(stylesheet.trim())
    if (isFile) {
        var cssList = dataOrPut("css", { mutableListOf<Any>() }) as MutableList<Any>
        cssList.add(stylesheet)
    } else {
        var localCssList = dataOrPut("localCss", { mutableListOf<Any>() }) as MutableList<Any>
        localCssList.add(CssParser.parse(stylesheet))
    }
}

inline fun LatteView.css(initStyle: Stylesheet.() -> Unit)  {
    val re = Stylesheet()
    re.initStyle()
    var css = dataOrPut("css", { mutableListOf<Any>() }) as MutableList<Any>
    css.add(re)
}


inline fun Stylesheet.selector(selector : String, init: RuleSet.() -> Unit): RuleSet {
    var ruleSet = RuleSet(selector)
    ruleSet.init()
    addRuleSet(ruleSet)
    return ruleSet
}


inline fun LatteView.inPx(value : String)  = LengthValue(value).inPixels(this.activity!!)

inline fun RuleSet.margin( value: String) = add("margin", value)
inline fun RuleSet.marginLeft( value: String) = add("margin-left", value)
inline fun RuleSet.marginTop( value: String) = add("margin-top", value)
inline fun RuleSet.marginRight( value: String) = add("margin-right", value)
inline fun RuleSet.marginBottom( value: String) = add("margin-bottom", value)
inline fun RuleSet.padding( value: String) = add("padding", value)
inline fun RuleSet.paddingTop( value: String) = add("padding-top", value)
inline fun RuleSet.paddingRight( value: String) = add("padding-right", value)
inline fun RuleSet.paddingBottom( value: String) = add("padding-bottom", value)
inline fun RuleSet.paddingLeft( value: String) = add("padding-left", value)
inline fun RuleSet.border( value: String) = add("border", value)
inline fun RuleSet.borderLeft( value: String) = add("border-left", value)
inline fun RuleSet.borderRight( value: String) = add("border-right", value)
inline fun RuleSet.borderBottom( value: String) = add("border-bottom", value)
inline fun RuleSet.borderTop( value: String) = add("border-top", value)
inline fun RuleSet.borderWidth( value: String) = add("border-width", value)
inline fun RuleSet.borderTopWidth( value: String) = add("border-top-width", value)
inline fun RuleSet.borderRightWidth( value: String) = add("border-right-width", value)
inline fun RuleSet.borderBottomWidth( value: String) = add("border-bottom-width", value)
inline fun RuleSet.borderLeftWidth( value: String) = add("border-left-width", value)
inline fun RuleSet.borderRadius( value: String) = add("border-radius", value)
inline fun RuleSet.borderTopLeftRadius( value: String) = add("border-top-left-radius", value)
inline fun RuleSet.borderTopRightRadius( value: String) = add("border-top-right-radius", value)
inline fun RuleSet.borderBottomRightRadius( value: String) = add("border-bottom-right-radius", value)
inline fun RuleSet.borderBottomLeftRadius( value: String) = add("border-bottom-left-radius", value)
inline fun RuleSet.fontSize( value: String) = add("font-size", value)
inline fun RuleSet.fontFamily( value: String) = add("font-family", value)
inline fun RuleSet.fontWeight( value: String) = add("font-weight", value)
inline fun RuleSet.fontStyle( value: String) = add("font-style", value)
inline fun RuleSet.color( value: String) = add("color", value)
inline fun RuleSet.backgroundColor( value: String) = add("background-color", value)
inline fun RuleSet.elevation( value: String) = add("elevation", value)
inline fun RuleSet.width( value: String) = add("width", value)
inline fun RuleSet.height( value: String) = add("height", value)
inline fun RuleSet.textAlign( value: String) = add("text-align", value)