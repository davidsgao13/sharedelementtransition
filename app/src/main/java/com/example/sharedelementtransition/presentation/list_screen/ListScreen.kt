package com.example.sharedelementtransition.presentation.list_screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sharedelementtransition.R

/**
 * A list screen composable. This type of function is called an extension function with a receiver
 * type. Specifically, this is an extension function for a receiver scope.
 *
 * @SharedTransitionScope is the receiver type, meaning this function can only be called on
 * instances of SharedTransitionScope
 * @ListScreen is the name of the function
 *
 * The function extends the functionality of a SharedTransitionScope and allows you to use its
 * properties and functions inside the body of ListScreen.
 *
 * How does this work? Inside the function body of the Composable, SharedTransitionScope becomes the
 * implicit 'this', allowing us to call its properties and methods directly without qualification.
 * Qualification in this context refers to explicitly specifying the owner of scope of a property,
 * method, or variable when calling it. This is usually done to resolve ambiguity. For example:
 *
 * val myObject = SomeClass()
 * myObject.someProperty; this is a qualified property
 * myObject.someMethod(); this is a qualified method.
 *
 * Directly access without qualification improves code readability and reduces verbosity.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    // Lambda that gives us access to an Int and a String, the Int being the resourceId of the image
    // and the String being the text associated with it; this is because we're going to pass these
    // values to the ItemScreen to display them once the user has clicked on an element in the list
    onItemClick: (Int, String) -> Unit
) {
    val drawables = listOf(
        R.drawable.plant1,
        R.drawable.plant2
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(drawables) { index, resId ->
            val text = "Item$index"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // The onClick of each row (clickable) will call the transition lambda function
                    // that we define in the constructor of ListScreen
                    .clickable { onItemClick(resId, text) }
            ) {
                // By having two composables that each take up a width of 1f, they end up taking up
                // the whole screen equally
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(16 / 9f)
                        .weight(1f)
                        // Only available if the composable is inside a SharedTransitionScope
                        .sharedElement(
                            state = rememberSharedContentState(key = "image/$resId"),
                            // Pass in an animation for the AnimatedVisibilityScope
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 1000)
                            }
                        )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = text,
                    modifier = Modifier
                        .weight(1f)
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
    }
}