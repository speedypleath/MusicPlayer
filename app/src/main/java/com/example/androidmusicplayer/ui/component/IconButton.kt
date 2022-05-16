package com.example.androidmusicplayer.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun IconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp
        ),
        modifier = Modifier.padding(10.dp, 0.dp).fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (iconRef, textRef) = createRefs()
            Icon(
                icon,
                contentDescription = text,
                modifier = Modifier.size(ButtonDefaults.IconSize).constrainAs(iconRef) {
                    start.linkTo(parent.start)
                }
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                text,
                modifier = Modifier.constrainAs(textRef) {
                    start.linkTo(iconRef.end, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                }
            )
        }
    }
}