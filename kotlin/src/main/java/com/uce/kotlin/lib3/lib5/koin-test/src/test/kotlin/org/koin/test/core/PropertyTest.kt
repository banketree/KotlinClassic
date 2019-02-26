package org.koin.test.core

import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Test
import org.koin.core.KoinProperties
import org.koin.dsl.module.module
import org.koin.error.MissingPropertyException
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.get
import org.koin.standalone.getProperty
import org.koin.standalone.setProperty
import org.koin.test.AutoCloseKoinTest
import org.koin.test.ext.junit.*

class PropertyTest : AutoCloseKoinTest() {
    val SimpleModule = module {
        single { ComponentA(getProperty(KEY)) }
        single { ComponentB(get()) }
    }

    val NoPropertyModule = module {

        single { ComponentA(getProperty(KEY)) }
        single { ComponentB(get()) }
    }

    val ComplexModule = module {
        module("A") {
            single { ComponentB(get()) }
            single { ComponentA(getProperty(KEY)) }
        }
    }

    val MoreComplexModule = module {
        module("A") {
            single { ComponentB(get()) }
            factory { ComponentA(getProperty(KEY)) }
        }
    }

    class ComponentA(val url: String)
    class ComponentB(val componentA: ComponentA)

    @Test
    fun `should load integer value`() {
        startKoin(
            listOf(SimpleModule),
            KoinProperties(useEnvironmentProperties = true, useKoinPropertiesFile = true)
        )

        val number = getProperty<Int>("number")
        Assert.assertEquals(1234, number)
    }

    @Test
    fun `should load float value`() {
        startKoin(
            listOf(SimpleModule),
            KoinProperties(useEnvironmentProperties = true, useKoinPropertiesFile = true)
        )

        val decimal = getProperty<Float>("decimal")
        Assert.assertEquals(1.42f, decimal)
    }

    @Test
    fun `should inject external property`() {
        startKoin(listOf(SimpleModule), KoinProperties(useKoinPropertiesFile = true))
        setProperty(KEY, VALUE)

        val url = getProperty<String>(KEY)
        val a = get<ComponentA>()
        val b = get<ComponentB>()

        Assert.assertEquals(a, b.componentA)
        Assert.assertEquals(VALUE, a.url)
        Assert.assertEquals(url, a.url)

        assertRemainingInstanceHolders(2)
        assertDefinitions(2)
        assertContexts(1)
        assertIsInRootPath(ComponentA::class)
        assertIsInRootPath(ComponentB::class)
        assertProperties(3)
    }


    @Test
    fun `should inject internal property`() {
        startKoin(
            listOf(SimpleModule),
            KoinProperties(extraProperties = mapOf(KEY to VALUE), useKoinPropertiesFile = true)
        )

        val url = getProperty<String>(KEY)
        val a = get<ComponentA>()
        val b = get<ComponentB>()

        Assert.assertEquals(a, b.componentA)
        Assert.assertEquals(VALUE, a.url)
        Assert.assertEquals(url, a.url)

        assertRemainingInstanceHolders(2)
        assertDefinitions(2)
        assertContexts(1)
        assertIsInRootPath(ComponentA::class)
        assertIsInRootPath(ComponentB::class)
        assertProperties(3)
    }

    @Test
    fun `should inject property - complex module`() {
        startKoin(listOf(ComplexModule), KoinProperties(useKoinPropertiesFile = true))
        setProperty(KEY, VALUE)

        val url = getProperty<String>(KEY)
        val a = get<ComponentA>()
        val b = get<ComponentB>()

        Assert.assertEquals(a, b.componentA)
        Assert.assertEquals(VALUE, a.url)
        Assert.assertEquals(url, a.url)

        assertRemainingInstanceHolders(2)
        assertDefinitions(2)
        assertContexts(2)
        assertIsInModulePath(ComponentA::class, "A")
        assertIsInModulePath(ComponentB::class, "A")
        assertProperties(3)
    }

    @Test
    fun `should not inject property but get default value as return`() {
        startKoin(listOf(NoPropertyModule), KoinProperties(useKoinPropertiesFile = true))

        try {
            getProperty<String>(KEY)
            fail()
        } catch (e: MissingPropertyException) {
            System.err.println(e)
        }
        val urlWithDefault = getProperty(KEY, "DEFAULT")

        Assert.assertEquals(urlWithDefault, "DEFAULT")
    }

    @Test
    fun `should not inject property`() {
        startKoin(listOf(NoPropertyModule), KoinProperties(useKoinPropertiesFile = true))

        try {
            getProperty<String>(KEY)
            fail()
        } catch (e: MissingPropertyException) {
            System.err.println(e)
        }
        try {
            get<ComponentA>()
            fail()
        } catch (e: Exception) {
        }
        try {
            get<ComponentB>()
            fail()
        } catch (e: Exception) {
        }

        assertRemainingInstanceHolders(2)
        assertDefinitions(2)
        assertContexts(1)
        assertIsInRootPath(ComponentA::class)
        assertIsInRootPath(ComponentB::class)
        assertProperties(2)
    }

    @Test
    fun `should overwrite property`() {
        startKoin(listOf(MoreComplexModule), KoinProperties(useKoinPropertiesFile = true))
        setProperty(KEY, VALUE)

        var url = getProperty<String>(KEY)
        var a = get<ComponentA>()
        val b = get<ComponentB>()

        Assert.assertEquals(VALUE, a.url)
        Assert.assertEquals(url, a.url)
        Assert.assertEquals(b.componentA.url, a.url)

        assertRemainingInstanceHolders(2)
        assertDefinitions(2)

        setProperty(KEY, VALUE_2)

        url = getProperty<String>(KEY)
        a = get()

        Assert.assertEquals(url, a.url)
        Assert.assertEquals(VALUE_2, a.url)

        assertRemainingInstanceHolders(2)
        assertDefinitions(2)

        assertContexts(2)
        assertProperties(3)
        assertIsInModulePath(ComponentA::class, "A")
        assertIsInModulePath(ComponentB::class, "A")
    }

    companion object {
        val KEY = "URL"
        val VALUE = "http://..."
        val VALUE_2 = "http://...2"
    }
}