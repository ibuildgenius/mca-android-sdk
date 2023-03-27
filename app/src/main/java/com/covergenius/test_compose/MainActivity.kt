package com.covergenius.test_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.covergenius.mca_sdk_android.presentation.MyCoverAI
import com.covergenius.test_compose.ui.theme.Test_composeTheme

class MainActivity : ComponentActivity() {

    val mcaToken =  "MCAPUBK_TEST|48c01008-5f01-4705-b63f-e71ef5fc974f"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Test_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column() {
                        val context = LocalContext.current


                        MyCoverAI.init(context, "<YOUR_API_TOKEN>")



                        FloatingActionButton(onClick = { MyCoverAI.init(context,  mcaToken) }) {
                        Greeting("Android")

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Test_composeTheme {
        Greeting("Android")
    }
}