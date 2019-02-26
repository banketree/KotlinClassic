package org.koin.core.scope

import org.koin.dsl.definition.BeanDefinition

fun BeanDefinition<*>.setScope(id: String) {
    attributes["scope"] = id
}

fun BeanDefinition<*>.setAddedToScope() {
    attributes["added_to_scope"] = true
}

fun BeanDefinition<*>.isAddedToScope(): Boolean {
    return attributes["added_to_scope"] as? Boolean ?: false
}

fun BeanDefinition<*>.getScope(): String {
    return attributes["scope"] as? String ?: ""
}

fun BeanDefinition<*>.isVisibleToScope(scope: Scope?): Boolean {
    val beanScope = getScope()
    return scope == null || beanScope.isEmpty() || (beanScope.isNotEmpty() && beanScope == scope.id)
}