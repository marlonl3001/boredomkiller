package br.com.mdr.boredomkiller.presentation

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.mdr.boredomkiller.data.dao.UserDatabase
import br.com.mdr.boredomkiller.data.repository.MainRepository
import br.com.mdr.boredomkiller.domain.MainUseCase
import br.com.mdr.boredomkiller.presentation.viewmodel.MainViewModel
import br.com.mdr.test.rule.KoinRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(JUnit4::class)
class ServerActivityFragmentTest: KoinTest {

    @get:Rule
    val koiRule = KoinRule(
        listOf(
            module(override = true) {
                configureLocalModuleTest(
                    ApplicationProvider.getApplicationContext())
                single { mockk<MainRepository>() }
                single { MainUseCase(get()) }
                viewModel { MainViewModel(get()) }
            }
        ),
        false
    )

    @Test
    fun givenRepositoryList_whenFetchListFromRepository_thenScrollToKoin() {
        withServerActivityFragment {
            mockUserActivity()
            launch()
        } actions {
            clickAtChangeActivity()
        } verify {
            checkLabelText()
        }
    }

    private fun configureLocalModuleTest(context: Context) = module {
        single {
            Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        }
    }

}