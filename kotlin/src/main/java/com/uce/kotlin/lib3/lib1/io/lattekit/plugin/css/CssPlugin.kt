package io.lattekit.plugin.css

import io.lattekit.plugin.LattePlugin
import io.lattekit.plugin.css.declaration.GLOBAL_STYLESHEETS
import io.lattekit.plugin.css.declaration.Stylesheet
import io.lattekit.view.LatteView
import io.lattekit.view.NativeView

/**
 * Created by maan on 2/20/16.
 */


class CssPlugin : LattePlugin() {

    fun getStylesheetsFor(view: LatteView): List<Stylesheet> {
        var results = mutableListOf<Stylesheet>()
        findStylesheets(view, results)
        var localCss = (view.renderingView?.data("localCss") ?: mutableListOf<Any>()) as MutableList<Any>
        localCss.forEach { if (it is Stylesheet) { results.add(it) } }
        return results;
    }

    fun findStylesheets(view: LatteView, results: MutableList<Stylesheet>) {
        var stylesheetList = view.data("css:stylesheet")
        if (stylesheetList != null && stylesheetList is List<*>) {
            results.addAll(0,stylesheetList as List<Stylesheet>)
        } else {
            var cssFiles = (view.data("css") ?: mutableListOf<Any>()) as MutableList<Any>
            if (cssFiles != null) {
                cssFiles.forEach {
                    if (it is String) {
                        results.add(Stylesheet.getStylesheet(it))
                    } else if (it is Stylesheet) {
                        results.add(it)
                    }
                }
            }
            if (view.parentView != null) {
                findStylesheets(view.parentView!!, results)
            }
            view.data("css:stylesheet", results.toList())
        }

    }

    override fun onPropsUpdated(view: LatteView, oldProps: MutableMap<String, Any?>) {
        if (view is NativeView) {
            view.getStyle().applyStylesheets(GLOBAL_STYLESHEETS + getStylesheetsFor(view))
        }
    }

    override fun onViewCreated(view: LatteView) {
        if (view is NativeView) {
            view.getStyle().applyStylesheets(GLOBAL_STYLESHEETS + getStylesheetsFor(view))
        }
    }
}




