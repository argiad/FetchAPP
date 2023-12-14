package com.steegler.fetchb.domain

import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import com.freeletics.flowredux.dsl.State
import com.steegler.fetchlib.FetchLib
import com.steegler.fetchlib.Result
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@ViewModelScoped
class StateMachine @Inject constructor(
    private val lib: FetchLib
) : FlowReduxStateMachine<FetchState, FetchAction>(initialState = FetchState.Content()) {
    init {
        spec {
            inState {
                onEnter { state: State<FetchState.Load> ->
                    when (val result = lib.fetchData()) {
                        is Result.Success -> {
                            state.override { FetchState.Content(result.value) }
                        }

                        is Result.Failure -> {
                            state.override { FetchState.Error(result.reason) }
                        }
                    }
                }
            }

            inState {
                on { _: FetchAction.RetryLoad, state: State<FetchState.Content> ->
                    state.override { FetchState.Load() }
                }
            }

            inState {
                on { _: FetchAction.RetryLoad, state: State<FetchState.Content> ->
                    state.override { FetchState.Load() }
                }
            }
            /*
            // Error
            inState {
                on { _: GithubAction.RetryLoadingAction, state: State<GithubState.Error> ->
                    state.override { GithubState.Load(owner = state.snapshot.owner) }
                }
            }
             */

//            inState<FetchState.Content> {
//                on { action, state ->  }
//            }
//            inState<FetchState.Error> {  }
//            inState<FetchState.Load> {  }
        }
    }
}

/*
// Load
            inState {
                onEnter { state: State<GithubState.Load> ->
                    val owner = state.snapshot.owner
                    githubRepository.repositoryByOwner(owner = owner).fold(
                        ifLeft = {
                            state.override { GithubState.Error(Throwable("Fail"), owner = owner) }
                        },
                        ifRight = {
                            state.override { GithubState.ContentState(repositories = it, owner = owner) }
                        }
                    )
                }
            }

            // Error
            inState {
                on { _: GithubAction.RetryLoadingAction, state: State<GithubState.Error> ->
                    state.override { GithubState.Load(owner = state.snapshot.owner) }
                }
            }

            // ContentState
            inState {
                on { action: GithubAction.TypeOwner, state: State<GithubState.ContentState> ->
                    state.mutate { copy(owner = action.input) }
                }

                on { _: GithubAction.Confirm, state: State<GithubState.ContentState> ->
                    val owner = state.snapshot.owner
                    if (owner.isNotBlank()) {
                        state.override { GithubState.Load(owner = owner) }
                    } else {
                        state.noChange()
                    }
                }

            }

 */
//class StateMachine
//@ViewModelScoped
//class GithubStateMachine @Inject constructor(
//    private val githubRepository: GithubRepository
//) : FlowReduxStateMachine<GithubState, GithubAction>(
//    initialState = GithubState.ContentState(owner = "", repositories = emptyList())
//) {
//    init {
//        spec {
//            // spec
//        }
//    }
//}