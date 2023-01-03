package br.com.mdr.test.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.mdr.base.ApiError
import br.com.mdr.test.rule.CoroutinesTestRule
import br.com.mdr.test.rule.MockWebServerRule
import io.mockk.confirmVerified
import io.mockk.verifySequence
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.koin.core.component.KoinApiExtension
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

const val REQUEST_ERROR = "Error on request"
private const val IO_DISPATCHER = "IO"

@KoinApiExtension
@ExperimentalCoroutinesApi
abstract class BaseViewModelTest : KoinTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var mockWebServerRule = MockWebServerRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.ERROR)
        modules(
            module(override = true) {
                single<CoroutineDispatcher>(named(IO_DISPATCHER)) {
                    coroutinesTestRule.testDispatcher
                }
            }
        )
    }

    protected lateinit var observerLoading: Observer<Boolean>

    fun mockErrorWrapper() = ApiError(REQUEST_ERROR)

    fun verifyLoaderWasShown() {
        verifySequence {
            observerLoading.onChanged(true)
            observerLoading.onChanged(false)
        }
        confirmVerified(observerLoading)
    }

    fun verifyLoaderWasNotShown() {
        confirmVerified(observerLoading)
    }

}
