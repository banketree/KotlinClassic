package io.lattekit.view

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import io.lattekit.util.Values
import java.util.*

/**
 * Created by maan on 2/15/16.
 */
open class NativeViewGroup : NativeView() {

    companion object {
        var SIZE_PATTERN  = Regex("""(\d+(?:\.\d+)?)([^\d ]+)""")

        fun parseSize(size : String, fallBack: Int, context : Context) : Int {
            if (size.toLowerCase() == "match_parent" || size.toLowerCase() == "fill_container") {
                return ViewGroup.LayoutParams.MATCH_PARENT
            } else if (size.toLowerCase() == "wrap_content") {
                return ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                var match = NativeViewGroup.Companion.SIZE_PATTERN.matchEntire(size)
                if (match != null) {
                    var value = match.groupValues.get(1).toFloat();
                    var unit = match.groupValues.get(2)
                    return when (unit) {
                        "dp" -> TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,context.resources.displayMetrics).toInt();
                        "dip" -> TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,context.resources.displayMetrics).toInt();
                        "sp" -> TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,value,context.resources.displayMetrics).toInt();
                        else -> value.toInt();
                    }
                }
            }
            return fallBack
        }

    }
    var managedViews = ArrayList<View>();

    open fun getLayoutParamsClass() : Class<out ViewGroup.LayoutParams> {
        if (this.androidView != null) {
            var cls = this.androidView?.javaClass?.getDeclaredClasses()?.find{ it.name == this.androidView?.javaClass?.name+"\$LayoutParams" } as Class<out ViewGroup.LayoutParams>?
            if (cls != null ) {
                return cls
            }
        }
        return ViewGroup.MarginLayoutParams::class.java
    }

    fun createLayoutParams() : ViewGroup.LayoutParams = when (this.androidView) {
        is android.widget.LinearLayout -> android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        is android.widget.RelativeLayout -> android.widget.RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        is android.widget.FrameLayout -> android.widget.FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        else -> getLayoutParamsClass().constructors.find {
            it.parameterTypes.size == 2 &&
                it.parameterTypes[0] == Integer.TYPE &&
                it.parameterTypes[1] == Integer.TYPE
        }?.newInstance(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) as ViewGroup.LayoutParams
    }

    open fun onChildrenAdded() {}


    fun mountChildren() {
        var newViews = ArrayList<View>();
        var myContainer = this.androidView as ViewGroup;

        for(i in 0..Math.max(managedViews.size, subViews.size)-1) {
            if (i < subViews.size) {
                var v = subViews[i]

                var childLP = createLayoutParams();
                var childView = v.buildAndroidViewTree(this.activity as Context, childLP);
                applyChildLayoutProps(v.rootNativeView!!, childView.layoutParams)
                childView.layoutParams = childView.layoutParams
                if (i >= managedViews.size) {
                    myContainer.addView(childView);
                } else if (managedViews[i] != childView) {
                    myContainer.removeView(managedViews[i]);
                    myContainer.addView(childView,i);
                }

                v.androidView = childView
                newViews.add(childView);
                if (!v.isMounted) {
                    v.notifyViewCreated()
                }
            } else if (i < managedViews.size) {
                myContainer.removeViewInLayout(managedViews.get(i))
            }
        }
        managedViews = newViews

        onChildrenAdded()
    }

    override fun onPropsUpdated(oldProps: Map<String, Any?>): Boolean {
        super.onPropsUpdated(oldProps);
        return true
    }


    fun applyChildLayoutProps(child: LatteView, params: ViewGroup.LayoutParams) {
        child.props.forEach {
            var keyName = it.key
            var value = child.props.get(keyName);

            when (keyName) {

                "layout_gravity" -> {
                    var field = params.javaClass.getField("gravity");
                    field.setAccessible(true);
                    if (value is String) {
                        var realVal = Gravity::class.java.getField(value.toUpperCase()).get(null)
                        field.set(params, realVal)
                    } else if (value is Integer) {
                        field.set(params, value)
                    }
                }

                "layout_width" -> {
                    params.width = if (value is String) {
                        NativeViewGroup.Companion.parseSize(value, ViewGroup.LayoutParams.MATCH_PARENT, child.activity!!)
                    } else { value as Int }
                }

                "layout_height" -> {
                    params.height = if (value is String) {
                        NativeViewGroup.Companion.parseSize(value, ViewGroup.LayoutParams.WRAP_CONTENT, child.activity!!)
                    } else { value as Int }

                }

                "layout_weight" -> {
                    if (value is String) {
                        (params as android.widget.LinearLayout.LayoutParams).weight = value.toFloat()
                    } else if (value is Float) {
                        (params as android.widget.LinearLayout.LayoutParams).weight = value
                    } else if (value is Int) {
                        (params as android.widget.LinearLayout.LayoutParams).weight = value.toFloat()
                    }
                }
                "layout_margin" -> {
                    var value = if (value is String) {
                        Values.toInt(value, activity!!)
                    } else if (value is Int) { value } else { 0 }
                    var marginLp = (params as ViewGroup.MarginLayoutParams)
                    marginLp.leftMargin = value
                    marginLp.marginStart = value
                    marginLp.topMargin = value
                    marginLp.rightMargin = value
                    marginLp.marginEnd = value
                    marginLp.bottomMargin = value
                }

                "layout_marginTop" -> {
                    if (value is String) {
                        (params as ViewGroup.MarginLayoutParams).topMargin = Values.toInt(value, activity!!)
                    } else if (value is Int) {
                        (params as ViewGroup.MarginLayoutParams).topMargin = value
                    }
                }

                "layout_marginLeft" -> {
                    if (value is String) {
                        (params as ViewGroup.MarginLayoutParams).leftMargin = Values.toInt(value, activity!!)
                    } else if (value is Int) {
                        (params as ViewGroup.MarginLayoutParams).leftMargin = value
                    }
                }

                "layout_marginStart" -> {
                    if (value is String) {
                        (params as ViewGroup.MarginLayoutParams).marginStart = Values.toInt(value, activity!!)
                    } else if (value is Int) {
                        (params as ViewGroup.MarginLayoutParams).marginStart = value
                    }
                }

                "layout_marginRight" -> {
                    if (value is String) {
                        (params as ViewGroup.MarginLayoutParams).rightMargin = Values.toInt(value, activity!!)
                    } else if (value is Int) {
                        (params as ViewGroup.MarginLayoutParams).rightMargin = value
                    }
                }
                "layout_marginEnd" -> {
                    if (value is String) {
                        (params as ViewGroup.MarginLayoutParams).marginEnd = Values.toInt(value, activity!!)
                    } else if (value is Int) {
                        (params as ViewGroup.MarginLayoutParams).marginEnd = value
                    }
                }
                "layout_marginBottom" -> {
                    if (value is String) {
                        (params as ViewGroup.MarginLayoutParams).bottomMargin = Values.toInt(value, activity!!)
                    } else if (value is Int) {
                        (params as ViewGroup.MarginLayoutParams).bottomMargin = value
                    }
                }
            }

        }
    }
}