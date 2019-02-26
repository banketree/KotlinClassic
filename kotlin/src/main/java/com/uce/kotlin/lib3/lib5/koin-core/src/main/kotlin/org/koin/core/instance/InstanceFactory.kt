/*
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.koin.core.instance

import org.koin.core.Koin
import org.koin.core.instance.holder.*
import org.koin.core.parameter.ParameterDefinition
import org.koin.core.scope.Scope
import org.koin.dsl.definition.BeanDefinition
import org.koin.dsl.definition.Kind
import org.koin.dsl.path.Path
import org.koin.error.ClosedScopeException
import org.koin.error.NoScopeException

/**
 * Instance factory - handle objects creation against BeanRegistry
 * @author - Arnaud GIULIANI
 */
open class InstanceFactory {

    val instances = ArrayList<InstanceHolder<*>>()
    val callbacks = ArrayList<ModuleCallBack>()

    /**
     * Retrieve or create instance from bean definition
     * @return Instance / has been created
     */
    fun <T> retrieveInstance(
        def: BeanDefinition<T>,
        p: ParameterDefinition,
        scope: Scope? = null
    ): Instance<T> {
        // find holder
        var holder = find(def, scope)
        if (holder == null) {
            holder = create(def, scope)
            // save it
            instances += holder
        }
        return holder.get(p)
    }

    /**
     * Find actual InstanceHolder
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> find(def: BeanDefinition<T>, scope: Scope?): InstanceHolder<T>? {
        val found =
            if (scope == null) {
                instances.firstOrNull { it.bean == def }
            } else {
                instances.firstOrNull { it is ScopeInstanceHolder && it.bean == def && it.scope == scope }
            }
        return found as InstanceHolder<T>?
    }

    /**
     * Create InstanceHolder
     */
    open fun <T> create(def: BeanDefinition<T>, scope: Scope? = null): InstanceHolder<T> {
        return when (def.kind) {
            Kind.Single -> SingleInstanceHolder(def)
            Kind.Factory -> FactoryInstanceHolder(def)
            Kind.Scope -> {
                if (scope != null) {
                    ScopeInstanceHolder(
                        def,
                        scope
                    )
                } else {
                    throw NoScopeException("Definition '$def' has to be used with a scope. Please create and specify a scope to use with your definition")
                }
            }
        }
    }

    /**
     * Release definition instance
     */
    fun release(definition: BeanDefinition<*>, scope: Scope?) {
        if (definition.kind == Kind.Scope) {
            Koin.logger.debug("release $definition")
            val holder = find(definition, scope)
            holder?.let {
                instances.remove(it)
            }
        }
    }

    /**
     * Delete Instance Holder
     */
    fun delete(definition: BeanDefinition<*>) {
        val found = instances.filter { it.bean == definition }
        instances.removeAll(found)
    }

    /**
     * Clear all resources
     */
    fun clear() {
        instances.clear()
    }

    /**
     * Release single instance from their Path
     */
    @Deprecated("Release path should not be use anymore. Use Scope API instead")
    fun releasePath(path: Path) {
        val singeInstances =
            instances.filter { it.bean.kind == Kind.Single && path.isVisible(it.bean.path) }
        instances.removeAll(singeInstances)
        callbacks.forEach { it.onRelease(path.name) }
    }

    /**
     * Register ModuleCallBack
     */
    @Deprecated("Uset he Scope API.")
    fun register(callback: ModuleCallBack) {
        callbacks += callback
    }
}