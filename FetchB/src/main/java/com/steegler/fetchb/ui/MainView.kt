package com.steegler.fetchb.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.steegler.fetchb.domain.FetchAction
import com.steegler.fetchb.domain.FetchState
import com.steegler.fetchlib.DataResponseItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainView(viewModel: FetchViewModel = hiltViewModel()) {

    val uiState by viewModel.rememberState()

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(false),
            onRefresh = { viewModel.dispatch(FetchAction.RetryLoad) },
            modifier = Modifier.fillMaxSize()
        ) {
            when (uiState) {
                is FetchState.Load -> {
                    Text(text = "Loading")
                }

                is FetchState.Error -> {
                    Text(text = "{$uiState.e.localizedMessage}")
                    Button(onClick = { viewModel.dispatch(FetchAction.RetryLoad) }) {
                        Text(text = "Reload")
                    }
                }

                is FetchState.Content -> {

                    ItemsList((uiState as FetchState.Content).data)
                }

                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemsList(data: List<DataResponseItem>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(Color.Transparent)
            .padding(8.dp, end = 8.dp)
    ) {
        data.let { dataList ->
            dataList.groupBy { it.listId }.forEach { (t, u) ->

                stickyHeader {
                    Column(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    ) {
                        Text(
                            text = "ListID - $t",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                items(u.sortedBy { it.id }) { item ->
                    Card(
                        shape = RoundedCornerShape(5.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 3.dp
                        ), modifier = Modifier.padding(top = 4.dp)

                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {


                            Text(
                                text = "#${item.id} - ${item.name} ", modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

            }
        }

    }
}