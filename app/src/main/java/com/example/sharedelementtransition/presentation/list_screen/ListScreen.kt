package com.example.sharedelementtransition.presentation.list_screen

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
 * A list screen composable.
 */

@Composable
fun ListScreen(
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
                // By having two composables that each take up a width of 1f,
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(16 / 9f)
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = text,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}