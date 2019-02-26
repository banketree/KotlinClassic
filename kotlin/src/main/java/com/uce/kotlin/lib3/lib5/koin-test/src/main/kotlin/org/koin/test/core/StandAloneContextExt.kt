package org.koin.test.core

import org.koin.core.Koin
import org.koin.core.KoinContext
import org.koin.core.parameter.emptyParameterDefinition
import org.koin.dsl.module.Module
import org.koin.log.Logger
import org.koin.standalone.StandAloneContext
import org.koin.test.core.instance.SandboxInstanceFactory
import org.koin.test.ext.koin.beanDefinitions

/**
 * Check all definition's dependencies
 */
fun StandAloneContext.checkModules(list: List<Module>, logger: Logger) {
    Koin.logger = logger //PrintLogger(showDebug = true)
    Koin.logger.info("[Koin] create sandbox")

    val koin = createKoinSandbox()
    StandAloneContext.setup(koin)

    // Build list
    koin.loadModules(list)

    // Run checks
    runAllInstances(koin)
}

private fun runAllInstances(koin: Koin) {
    val koinContext = koin.koinContext
    koinContext.instanceRegistry.createInstances(
        koinContext.beanDefinitions(),
        emptyParameterDefinition()
    )
}

private fun createKoinSandbox() = Koin.create(KoinContext.create(SandboxInstanceFactory()))