package com.wuba.wastesorting

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.wuba.wastesorting.components.ResourcesSwitch
import com.wuba.wastesorting.ui.theme.WasteSortingTheme

@Composable
fun App(
    vm: MainViewModel = hiltViewModel()
){
    val navController = rememberNavController()
    ResourcesSwitch(locale = vm.locale) {
        WasteSortingTheme {
            Surface(color = MaterialTheme.colors.background) {
                NavGraph(navController = navController)
            }
        }
    }
}