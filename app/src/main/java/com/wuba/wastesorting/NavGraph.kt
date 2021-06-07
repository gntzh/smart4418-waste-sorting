package com.wuba.wastesorting

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wuba.wastesorting.components.LocaleContextWrapper
import com.wuba.wastesorting.screens.Login
import com.wuba.wastesorting.screens.admin.Maintenance
import com.wuba.wastesorting.screens.user.Sorting

sealed class RouteRecord(val path: String) {
    object Login : RouteRecord("login")
    object Sorting : RouteRecord("sorting")
    object Maintenance : RouteRecord("maintenance")
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = RouteRecord.Login.path,
    vm: MainViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val userType = vm.userType
    SideEffect {
        if (userType <= 0 && currentRoute != RouteRecord.Login.path && currentRoute != null) {
            navController.navigate(RouteRecord.Login.path) {
                popUpTo(RouteRecord.Login.path)
                launchSingleTop = true
            }
        }
    }
    val onLogOut = {
        vm.logOut()
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(RouteRecord.Login.path) {
            Login(vm, onLogIn = {
                when (it) {
                    1 -> navController.navigate(RouteRecord.Sorting.path)
                    2 -> navController.navigate(RouteRecord.Maintenance.path)
                }
            })
        }
        composable(RouteRecord.Sorting.path) { Sorting(vm) }
        composable(RouteRecord.Maintenance.path) { Maintenance(vm) }
    }
    Row(
        Modifier
            .fillMaxSize()
            .padding(end = 20.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        IconButton(onClick = { vm.switchLocale() }, modifier = Modifier.size(36.dp)) {
            LocaleContextWrapper {
                Icon(
                    painterResource(R.drawable.lang_btn), null, tint = when (currentRoute) {
                        RouteRecord.Login.path -> Color.White.copy(.5f)
                        else -> Color.Black.copy(.5f)
                    }
                )
            }
        }
        if (navBackStackEntry?.destination?.route != RouteRecord.Login.path) {
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = onLogOut, modifier = Modifier.size(36.dp)) {
                Icon(painterResource(R.drawable.exit), null, tint = Color.Black.copy(.5f))
            }
        }
    }
    LazyColumn(Modifier.background(Color.White)) {
        items(navController.backQueue){entry ->
            Row(Modifier.padding(horizontal = 4.dp, vertical = 4.dp)) {
                Text(entry.destination.route?:"null")
            }
        }
    }
}