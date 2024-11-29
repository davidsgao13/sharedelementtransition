package com.example.sharedelementtransition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sharedelementtransition.presentation.detail_screen.DetailScreen
import com.example.sharedelementtransition.presentation.list_screen.ListScreen
import com.example.sharedelementtransition.ui.theme.SharedElementTransitionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedElementTransitionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Implement a shared transition navigation from the ListScreen to the
                    // DetailScreen. Start by creating a navController, which we will assign to
                    // rememberNavController so we don't recreate it during recompositions. This
                    // is because our navController does not need to be recreated every time the
                    // screen is recomposed. We can just reuse our existing one. Note how we use
                    // = for assignment rather then by for property delegation, because
                    // the navController is not a state, it is a static object
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "list"
                    ) {
                        composable("list") {
                            ListScreen(onItemClick = { resId, text ->
                                navController.navigate("detail/$resId/$text")
                            })
                        }
                        composable(route = "detail/{resId}/{text}",
                            arguments = listOf(navArgument("resId") {
                                type = NavType.IntType
                            }, navArgument("text") {
                                type = NavType.StringType
                            }
                            )
                        ) {
                            val resId = it.arguments?.getInt("resId") ?: 0
                            val text = it.arguments?.getString("text") ?: ""
                            DetailScreen(resId = resId, text = text)
                        }
                    }

                }
            }
        }
    }
}