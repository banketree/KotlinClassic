package org.koin.test.core

import org.junit.Assert.assertTrue
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.error.BeanOverrideException
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.get
import org.koin.test.AutoCloseKoinTest

class GenericBindingTest : AutoCloseKoinTest() {

    val module = module {
        single("a") { ComponentA() as InterfaceComponent<String> }
        single("b") { ComponentB() as InterfaceComponent<Int> }

    }

    val badModule = module {
        single { ComponentA() as InterfaceComponent<String> }
        single { ComponentB() as InterfaceComponent<Int> }

    }

    interface InterfaceComponent<T>
    class ComponentA : InterfaceComponent<String>
    class ComponentB : InterfaceComponent<Int>

    @Test
    fun `should inject generic interface component`() {
        startKoin(listOf(module))

        val a = get<InterfaceComponent<String>>("a")
        assertTrue(a is ComponentA)
    }

    @Test
    fun `should not inject generic interface component`() {
        try {
            startKoin(listOf(badModule))
            get<InterfaceComponent<String>>()
        } catch (e: BeanOverrideException) {
        }
    }
}