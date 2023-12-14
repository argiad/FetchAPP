package com.steegler.fetchb.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freeletics.flowredux.compose.rememberState
import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import com.steegler.fetchb.domain.FetchAction
import com.steegler.fetchb.domain.FetchState
import com.steegler.fetchb.domain.StateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FetchViewModel @Inject constructor(
    fetchStateMachine: StateMachine
) : AbsStateViewModel<FetchState, FetchAction>(fetchStateMachine)




@OptIn(ExperimentalCoroutinesApi::class)
abstract class AbsStateViewModel<State : Any, Action : Any>(
    private val stateMachine: FlowReduxStateMachine<State, Action>
) : ViewModel() {

    @Composable
    fun rememberState() = stateMachine.rememberState()

    fun dispatch(action: Action) = viewModelScope.launch {
        stateMachine.dispatch(action = action)
    }

}