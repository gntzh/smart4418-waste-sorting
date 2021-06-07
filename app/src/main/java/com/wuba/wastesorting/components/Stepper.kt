package com.wuba.wastesorting.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class StepperData(val complete: Int, val space: Dp, val labelSize: Dp)

val LocalStepper = compositionLocalOf<StepperData> { error("No StepperData found!") }

@Composable
fun Stepper(
    complete: Int,
    space: Dp = 300.dp,
    labelSize: Dp = 48.dp,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalStepper provides StepperData(
            complete = complete,
            space = space,
            labelSize = labelSize
        )
    ) {
        content()
    }
}


@Composable
fun Step(
    step: Int,
    title: String = "",
    description: String = "",
    last: Boolean = false
) {
    val stepper = LocalStepper.current
    val color = when {
        stepper.complete + 1 > step -> Color(0xff00bea6)
        stepper.complete + 1 == step -> Color(0xff409eff)
        else -> Color(0xff9d9d9d)
    }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = CircleShape,
                color = color,
                contentColor = Color.White,
                modifier = Modifier.size(stepper.labelSize),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        step.toString(),
                        style = MaterialTheme.typography.h4
                    )
                }
            }
            if (!last) {
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(stepper.space)
                        .background(Color(0xffc0c4cc))
                )
            }
        }
        if (title.isNotEmpty()) {
            Spacer(modifier = Modifier.height(2.dp))
            Column(
                Modifier
                    .width(stepper.space)
                    .offset(x = -(stepper.space - stepper.labelSize) / 2),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(title, style = MaterialTheme.typography.h6, color = color)
                if (description.isNotEmpty()) {
                    Text(
                        description,
                        style = MaterialTheme.typography.body1,
                        color = color
                    )
                }
            }
        }
    }
}


@Composable
fun StepContent(step: Int, content: @Composable () -> Unit) {
    val stepper = LocalStepper.current
    if ((stepper.complete + 1) == step) {
        content()
    }
}
