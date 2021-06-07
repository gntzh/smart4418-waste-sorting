package com.wuba.wastesorting.utils

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.wuba.wastesorting.components.ResourcesSwitch
import com.wuba.wastesorting.ui.theme.WasteSortingTheme
import java.util.*


@Composable
internal fun ThemedPreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    WasteSortingTheme(darkTheme = darkTheme) {
        Surface {
            content()
        }
    }
}


@Composable
internal fun LocaleResourcesPreview(
    locale: Locale = Locale.ENGLISH,
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    ResourcesSwitch(locale){
        ThemedPreview(darkTheme, content)
    }
}