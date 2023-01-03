package br.com.mdr.test.rule

import androidx.test.core.app.ApplicationProvider
import io.mockk.unmockkAll
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

class KoinRule(
    private val modules: List<Module> = emptyList(),
    private val shouldStart: Boolean = true
) : TestRule {

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                try {
                    if (shouldStart) {
                        startKoin {
                            androidContext(ApplicationProvider.getApplicationContext())
                            modules(
                                modules
                            )
                        }
                    } else {
                        loadKoinModules(modules = modules)
                    }

                    base?.evaluate()
                } finally {
                    unmockkAll()
                    if (shouldStart) {
                        stopKoin()
                    } else {
                        unloadKoinModules(modules = modules)
                    }
                }
            }
        }
    }
}
