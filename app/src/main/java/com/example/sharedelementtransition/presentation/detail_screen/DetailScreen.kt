package com.example.sharedelementtransition.presentation.detail_screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * Composable view for showing the details of a specific element picked from the ListScreen.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailScreen(
    resId: Int,
    text: String = "",
    animatedVisibilityScope : AnimatedVisibilityScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // By having two composables that each take up a width of 1f,
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(16 / 9f)
                .weight(1f)
                .sharedElement(
                    // This must be the same key as the one in the ListScreen
                    state = rememberSharedContentState(key = "image/$resId"),
                    // Pass in an animation for the AnimatedVisibilityScope
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }
                )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = text,
            modifier = Modifier.weight(1f)
                .sharedElement(
                    state = rememberSharedContentState(key = "text/$text"),
                    // Pass in an animation for the AnimatedVisibilityScope
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }
                )
        )
    }
}