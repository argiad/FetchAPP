package com.steegler.fetcha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.steegler.fetcha.ui.theme.FetchATheme
import com.steegler.fetchlib.FetchLib
import com.steegler.fetchlib.FetchLibBuilder
import com.steegler.fetchlib.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FetchA : ComponentActivity() {

    lateinit var libTest: FetchLib
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        libTest = FetchLibBuilder
            .withContext(baseContext)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            when (val result = libTest.fetchData()) {

                is Result.Success -> {
                    result.value.forEach { println(it) }
                }

                is Result.Failure -> {
                    println(result.reason)
                }
            }
        }
        setContent {
            FetchATheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android AAA")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FetchATheme {
        Greeting("Android")
    }
}