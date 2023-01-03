package br.com.mdr.boredomkiller.presentation


import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.ViewInteraction
import br.com.mdr.boredomkiller.R
import br.com.mdr.boredomkiller.data.repository.MainRepository
import br.com.mdr.boredomkiller.presentation.fragment.ServerActivityFragment
import br.com.mdr.test.extension.click
import br.com.mdr.test.extension.hasText
import br.com.mdr.test.mock.userActivityResponse
import io.mockk.coEvery
import io.mockk.mockk
import org.koin.test.KoinTest
import org.koin.test.inject

fun ServerActivityFragmentTest.withServerActivityFragment(func: ServerActivityFragmentRobot.() -> Unit) =
    ServerActivityFragmentRobot().apply(func)

class ServerActivityFragmentRobot: KoinTest {
    private val mockNavigation = mockk<NavController>(relaxed = true)
    private val repository: MainRepository by inject()

    infix fun actions(func: ServerActivityFragmentRobot.() -> Unit) = this.apply(func)
    infix fun verify(func: ServerActivityFragmentResult.() -> Unit) =
        ServerActivityFragmentResult().apply(func)

    fun launch() {
        launchFragmentInContainer<ServerActivityFragment>(
            themeResId = R.style.Theme_BoredomKiller
        ).withFragment {
            Navigation.setViewNavController(requireView(), mockNavigation)
        }
    }

    fun mockUserActivity() {
        coEvery { repository.getActivity() } returns userActivityResponse
    }

    fun clickAtChangeActivity() =
        R.id.btnChange.click()

}

class ServerActivityFragmentResult {
    fun checkLabelText(): ViewInteraction =
        R.id.txtActivityDescription.hasText(
            "Go for a run"
        )
}