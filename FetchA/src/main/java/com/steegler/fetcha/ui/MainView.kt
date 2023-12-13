package com.steegler.fetcha.ui

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainView(viewModel: ListViewModel = hiltViewModel()) {
    val data by viewModel.items.observeAsState()
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(false),
            onRefresh = { viewModel.fetch() },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(8.dp, end = 8.dp)
            ) {
                data?.let { dataList ->
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
    }
}
