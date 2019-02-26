package org.koin.test.module

import org.junit.Assert
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.ext.koin.beanDefinitions

class PathVisibilityTest : AutoCloseKoinTest() {

    val flat = module {
        single { A() }
        single { B() }
    }

    val oneModule = module {
        single { A() }
        module("aModule") {
            single { B() }
        }
    }

    val deepModule1 = module {
        single { A() }
        module("mod1") {
            module("mod2") {
                single { B() }
            }
        }
    }

    val deepModule2 = module {
        single { A() }
        module("mod1.mod2") {
            single { B() }
        }
    }

    val twoModules = module {
        module("aModuleA") {
            single { A() }
        }
        module("aModuleB") {
            single { B() }
        }
    }

    val sameModule = module {
        module("aModule") {
            single { A() }
            single { B() }
        }
    }

    class A
    class B


    @Test
    fun `can see each other - flat definitions`() {
        startKoin(listOf(flat))
        val definitions = getKoin().beanDefinitions()
        val a = definitions.first { it.primaryType == A::class }
        val b = definitions.first { it.primaryType == B::class }

        Assert.assertTrue(a.isVisible(b))
        Assert.assertTrue(b.isVisible(a))
    }

    @Test
    fun `can see each other - same module`() {
        startKoin(listOf(sameModule))
        val definitions = getKoin().beanDefinitions()
        val a = definitions.first { it.primaryType == A::class }
        val b = definitions.first { it.primaryType == B::class }

        Assert.assertTrue(a.isVisible(b))
        Assert.assertTrue(b.isVisible(a))
    }

    @Test
    fun `can see each other - deep module`() {
        startKoin(listOf(deepModule1))
        val definitions = getKoin().beanDefinitions()
        val a = definitions.first { it.primaryType == A::class }
        val b = definitions.first { it.primaryType == B::class }

        Assert.assertFalse(a.isVisible(b))
        Assert.assertTrue(b.isVisible(a))
    }

    @Test
    fun `can see each other - deep module 2`() {
        startKoin(listOf(deepModule2))
        val definitions = getKoin().beanDefinitions()
        val a = definitions.first { it.primaryType == A::class }
        val b = definitions.first { it.primaryType == B::class }

        Assert.assertFalse(a.isVisible(b))
        Assert.assertTrue(b.isVisible(a))
    }

    @Test
    fun `can see one - one module`() {
        startKoin(listOf(oneModule))
        val definitions = getKoin().beanDefinitions()
        val a = definitions.first { it.primaryType == A::class }
        val b = definitions.first { it.primaryType == B::class }

        Assert.assertFalse(a.isVisible(b))
        Assert.assertTrue(b.isVisible(a))
    }

    @Test
    fun `can't see each other - seperate modules`() {
        startKoin(listOf(twoModules))
        val definitions = getKoin().beanDefinitions()
        val a = definitions.first { it.primaryType == A::class }
        val b = definitions.first { it.primaryType == B::class }

        Assert.assertFalse(a.isVisible(b))
        Assert.assertFalse(b.isVisible(a))
    }
}