package org.koin.spark

import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.get
import org.koin.test.AutoCloseKoinTest
import org.koin.test.ext.junit.assertContexts
import org.koin.test.ext.junit.assertDefinitions
import org.koin.test.ext.junit.assertIsInRootPath
import org.koin.test.ext.junit.assertRemainingInstanceHolders

class SparkDSLTest : AutoCloseKoinTest() {

    val module = module {
        single { HelloService() }
        controller { HelloController(get()) }
    }

    class HelloService
    class HelloController(val service: HelloService) : SparkController

    @Test
    fun `check controller is well injected`() {
        startKoin(listOf(module))

        val controller: HelloController = get()
        val service: HelloService = get()

        assertEquals(service, controller.service)

        assertRemainingInstanceHolders(2)
        assertDefinitions(2)
        assertContexts(1)
        assertIsInRootPath(HelloController::class)
        assertIsInRootPath(HelloService::class)

        assertEquals(controller, get<SparkController>())
    }

    @Test
    fun `check controller is a bean`() {
        startKoin(listOf(module))

        val service: HelloService = get()
        val controller: HelloController = get()
        val controller2: HelloController = get()

        assertEquals(service, controller.service)
        assertEquals(controller2, controller)

        assertEquals(controller, get<SparkController>())
        assertRemainingInstanceHolders(2)
        assertDefinitions(2)
        assertContexts(1)
        assertIsInRootPath(HelloController::class)
        assertIsInRootPath(HelloService::class)
    }
}