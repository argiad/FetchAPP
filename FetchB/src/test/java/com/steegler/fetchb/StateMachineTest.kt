package com.steegler.fetchb

import app.cash.turbine.test
import com.steegler.fetchb.domain.FetchAction
import com.steegler.fetchb.domain.FetchState
import com.steegler.fetchb.domain.StateMachine
import com.steegler.fetchlib.FetchLib
import com.steegler.fetchlib.FetchLibError
import com.steegler.fetchlib.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs


class GithubStateMachineTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @MockK
    lateinit var lib: FetchLib

    private val stateMachine by lazy { StateMachine(lib) }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Test confirm the sequence upon a failure`() = coroutineTestRule.scope.runTest {

        // Given
        coEvery { lib.fetchData() } returns Result.Failure(FetchLibError.NetworkError())

        stateMachine.state.test {
            stateMachine.dispatch(FetchAction.RetryLoad)
            assertIs<FetchState.Content>(awaitItem(), "FetchState.Content")
            assertIs<FetchState.Load>(awaitItem(), "FetchState.Load")
            assertIs<FetchState.Error>(awaitItem(), "FetchState.Error")
            coVerify { lib.fetchData() }
        }
    }

}