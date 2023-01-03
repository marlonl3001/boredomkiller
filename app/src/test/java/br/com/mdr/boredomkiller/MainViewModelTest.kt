package br.com.mdr.boredomkiller

import androidx.test.filters.SmallTest
import br.com.mdr.boredomkiller.data.repository.MainRepository
import br.com.mdr.boredomkiller.di.useCaseModule
import br.com.mdr.boredomkiller.di.viewModelModule
import br.com.mdr.boredomkiller.presentation.viewmodel.MainViewModel
import br.com.mdr.test.base.BaseViewModelTest
import br.com.mdr.test.extension.test
import br.com.mdr.test.mock.ExceptionTestData.ERROR_WRAPPER
import br.com.mdr.test.mock.ExceptionTestData.HTTP_EXCEPTION
import br.com.mdr.test.mock.userActivityResponse
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(JUnit4::class)
class MainViewModelTest: BaseViewModelTest() {

    private val viewModel: MainViewModel by inject()
    private val repository: MainRepository by inject()

    @Before
    fun setUp() {
        loadKoinModules(
        useCaseModule +
                viewModelModule +
                module(override = true) {
                    single { mockk<MainRepository>() }
                }
        )
        observerLoading = viewModel.loading.test()

    }

    @Test
    fun givenSuccess_whenFetchActivity_thenDisplayResult() {
        //given
        val observerSuccess = viewModel.userActivity.test()
        val observerError = viewModel.apiError.test()

        coEvery { repository.getActivity() } returns userActivityResponse

        //when
        viewModel.fetchActivity()

        //then
        verify { observerSuccess.onChanged(userActivityResponse) }
        confirmVerified(observerSuccess, observerError)
    }

    @Test
    fun givenException_whenFetchActivity_thenDisplayError() {
        //given
        val observerSuccess = viewModel.userActivity.test()
        val observerError = viewModel.apiError.test()

        coEvery { repository.getActivity() } throws HTTP_EXCEPTION

        //when
        viewModel.fetchActivity()

        //then
        verify { observerError.onChanged(ERROR_WRAPPER) }
        confirmVerified(observerSuccess, observerError)
    }
}