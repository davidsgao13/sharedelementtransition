package com.example.sharedelementtransition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sharedelementtransition.presentation.detail_screen.DetailScreen
import com.example.sharedelementtransition.presentation.list_screen.ListScreen
import com.example.sharedelementtransition.ui.theme.SharedElementTransitionTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedElementTransitionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    // An experimental scope used for SharedTransitions between two UI Screens. We
                    // wrap the navController and NavHost inside this scope in order to perform a
                    // shared transition during navigation. This gives us access to a special
                    // modifier that gives us access to a special transition Modifier.sharedElement.
                    // We don't have this sharedElement Modifier outside of the SharedTransition
                    // scope
                    SharedTransitionLayout {
                        // Special modifier that we have access to inside a SharedTransitionLayout,
                        // which has a 'this' value of SharedTransitionScope.
                        // We need to add these modifiers to every shared element we want in our
                        // animation and define how those animations work.
                        //
                        // Modifier.sharedElement()
                        //
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
                                ListScreen(
                                    onItemClick = { resId, text ->
                                        navController.navigate("detail/$resId/$text")
                                    },
                                    animatedVisibilityScope = this
                                )
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
                                DetailScreen(
                                    resId = resId,
                                    text = text,
                                    animatedVisibilityScope = this
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}