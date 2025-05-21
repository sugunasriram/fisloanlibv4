package com.github.sugunasriram.fisloanlibv4.fis_code.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun LaunchScreen(startDestination: String) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, route = AppNavGraph.BUYERAPP,
        startDestination = AppNavGraph.GRAPH_LAUNCH,
    ) {
        mobileNavigation(navController = navController, startDestination = startDestination)
    }
}
